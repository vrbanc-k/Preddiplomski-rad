package hr.fer.zavrsni.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder)throws  Exception{
        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select email,password,validated from user where email=?")
                .authoritiesByUsernameQuery("select email,authority from user where email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home","/index","/register","/h2-console","/h2-console/**","/h2-console**",
                        "/validation","/validation**","/restaurant-list","/restaurant-list*/**","/setRestaurantMenu",
                        "/setRestaurantMenu*/**","/js/**","/css/**","/img/**","/webjars/**").permitAll()
                .antMatchers("/my-restaurants*","/my-restaurants*/**").access("hasRole('ROLE_OWNER')")
                .antMatchers("/shopping-cart*","/shopping-cart*/**").access("hasRole('ROLE_USER')")
                .antMatchers("/order-list*","/order-list*/**","/take-order*","/take-order*/**",
                        "/active-order*","/active-order*/**","/complete-order*",
                        "/complete-order*/**").access("hasRole('ROLE_WORKER')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
