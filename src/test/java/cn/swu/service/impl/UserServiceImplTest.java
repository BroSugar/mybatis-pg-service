package cn.swu.service.impl;

import cn.swu.entity.User;
import cn.swu.mapper.UserMapper;
import cn.swu.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UserService 数据库集成测试")
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    private User testUser;
    private Long savedUserId;



    @Test
    @Order(1)
    @DisplayName("测试保存用户")
    void testSave() {
        testUser = new User();
        testUser.setUsername("测试用户");
        testUser.setEmail("test@example.com");
        testUser.setAge(25);
        testUser.setContent();
        boolean result = userService.save(testUser);
        
        assertTrue(result, "保存用户应该成功");
        assertNotNull(testUser.getId(), "保存后应该生成 ID");
        assertEquals("测试用户", testUser.getUsername());
        assertEquals(25, testUser.getAge());
        assertNotNull(testUser.getCreateTime());
        assertNotNull(testUser.getUpdateTime());
        
        savedUserId = testUser.getId();
        
        User savedUser = userService.getById(savedUserId);
        assertNotNull(savedUser);
        assertEquals("测试用户", savedUser.getUsername());
    }

    @Test
    @Order(2)
    @DisplayName("测试更新用户")
    void testUpdate() {
        userService.save(testUser);
        savedUserId = testUser.getId();
        
        testUser.setUsername("更新后的用户");
        testUser.setAge(30);
        boolean result = userService.update(testUser);
        
        assertTrue(result, "更新用户应该成功");
        
        User updatedUser = userService.getById(savedUserId);
        assertNotNull(updatedUser);
        assertEquals("更新后的用户", updatedUser.getUsername());
        assertEquals(30, updatedUser.getAge());
        assertNotNull(updatedUser.getUpdateTime());
    }

    @Test
    @Order(3)
    @DisplayName("测试删除用户")
    void testDeleteById() {
        userService.save(testUser);
        Long userId = testUser.getId();
        
        boolean result = userService.deleteById(userId);
        
        assertTrue(result, "删除用户应该成功");
        
        User deletedUser = userService.getById(userId);
        assertNull(deletedUser, "删除后查询应该返回 null");
    }

    @Test
    @Order(4)
    @DisplayName("测试根据 ID 查询用户")
    void testGetById() {
//        userService.save(testUser);
//        Long userId = testUser.getId();
        Long userId = 1L;
        User result = userService.getById(userId);
        
        assertNotNull(result);
//        assertEquals(userId, result.getId());
//        assertEquals("测试用户", result.getUsername());
//        assertEquals("test@example.com", result.getEmail());
//        assertEquals(25, result.getAge());
        
//        savedUserId = userId;
    }


    @Test
    @Order(7)
    @DisplayName("测试保存不存在的用户后查询")
    void testGetByIdNotFound() {
        User result = userService.getById(99999L);
        assertNull(result, "查询不存在的用户应该返回 null");
    }

    @Test
    @Order(9)
    @DisplayName("测试用户数据完整性")
    void testDataIntegrity() {
        userService.save(testUser);
        savedUserId = testUser.getId();
        
        User savedUser = userService.getById(savedUserId);
        
        assertNotNull(savedUser.getCreateTime(), "创建时间不能为空");
        assertNotNull(savedUser.getUpdateTime(), "更新时间不能为空");
        assertTrue(savedUser.getCreateTime().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertTrue(savedUser.getUpdateTime().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    @Order(12)
    @DisplayName("测试删除不存在的用户")
    void testDeleteNonExistentUser() {
        boolean result = userService.deleteById(99999L);
        
        assertFalse(result, "删除不存在的用户应该失败");
    }

}