package hr.fer.zavrsni.service;

import hr.fer.zavrsni.dao.UserRepository;
import hr.fer.zavrsni.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceJpa implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateUserValidatedTrue(Long id) {
        userRepository.updateUserValidatedTrue(id);
    }

    @Override
    public User findUserByEmailBcrypt(String bcrypt) {
        return userRepository.findByBcrypt(bcrypt);
    }

    @Override
    public List<User> findUserByRestaurantId(Long id) {
        return userRepository.findByRestaurantId(id);
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        userRepository.updateUserPassword(id,password);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id);
    }
}
