package library.repository;

import library.model.User;

import java.util.List;

public interface IUserRepository {
    User saveUser(User user);

    User findUserById(int id);

    List<User> findAllUsers();
}
