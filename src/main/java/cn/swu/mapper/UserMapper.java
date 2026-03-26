package cn.swu.mapper;

import cn.swu.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    int insert(User user);

    int update(User user);

    int deleteById(@Param("id") Long id);

    User selectById(@Param("id") Long id);

    List<User> selectAll();

    List<User> selectByUsername(@Param("username") String username);
}
