package com.solvd.laba;

import com.solvd.laba.mappers.UserMapper;
import com.solvd.laba.models.User;
import com.solvd.laba.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserMapperTest {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.getUserByUsername("standard_user");

            if (user != null) {
                System.out.println("✅ Loaded user from DB: " + user);
            } else {
                System.out.println("⚠️ User not found in DB");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
