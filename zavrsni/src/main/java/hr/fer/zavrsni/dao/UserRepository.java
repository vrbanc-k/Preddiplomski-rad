package hr.fer.zavrsni.dao;

import hr.fer.zavrsni.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    User findByBcrypt(String bcrypt);

    User findById(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE User user SET user.validated=true WHERE user.id=:userId")
    void updateUserValidatedTrue(@Param("userId")Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE User user SET user.password=:pass WHERE user.id=:userId")
    void updateUserPassword(@Param("userId")Long id,@Param("pass")String password);

    List<User> findByRestaurantId(Long id);
}
