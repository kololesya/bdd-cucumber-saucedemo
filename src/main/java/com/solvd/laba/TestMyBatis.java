package com.solvd.laba;

import com.solvd.laba.mappers.UserMapper;
import com.solvd.laba.models.User;
import com.solvd.laba.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class TestMyBatis {
    public static void main(String[] args) {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.getUserByUsername("standard_user");
            System.out.println(user);
        }
    }
}
