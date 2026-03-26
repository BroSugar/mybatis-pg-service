package cn.swu.service;

import cn.swu.entity.User;

import java.util.List;

public interface UserService {

    boolean save(User user);

    boolean update(User user);

    boolean deleteById(Long id);

    User getById(Long id);

    List<User> listAll();

    List<User> listByUsername(String username);
}
