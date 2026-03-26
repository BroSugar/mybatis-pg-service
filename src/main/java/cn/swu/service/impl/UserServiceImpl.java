package cn.swu.service.impl;

import cn.swu.entity.User;
import cn.swu.mapper.UserMapper;
import cn.swu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean save(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.update(user) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> listAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> listByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
