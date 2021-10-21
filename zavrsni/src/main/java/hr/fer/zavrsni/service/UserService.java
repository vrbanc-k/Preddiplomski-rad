package hr.fer.zavrsni.service;

import hr.fer.zavrsni.model.User;

import java.util.List;

public interface UserService {
    void registerUser(User user);
    User findUserByEmail(String email);
    void updateUserValidatedTrue(Long id);
    User findUserByEmailBcrypt(String bcrypt);
    List<User> findUserByRestaurantId(Long id);
    void updateUserPassword(Long id,String password);
    void deleteUser(User user);
    User findUserById(Long id);
}
