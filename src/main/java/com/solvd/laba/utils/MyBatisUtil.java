package com.solvd.laba.utils;

import com.zebrunner.carina.utils.R;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.Properties;

public class MyBatisUtil {
    private static SqlSessionFactory sessionFactory;

    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");

            Properties properties = new Properties();
            properties.setProperty("db.driver", R.DATABASE.get("db.driver"));
            properties.setProperty("db.url", R.DATABASE.get("db.url"));
            properties.setProperty("db.user", R.DATABASE.get("db.user"));
            properties.setProperty("db.password", R.DATABASE.get("db.password"));

            sessionFactory = new SqlSessionFactoryBuilder().build(reader, properties);

        } catch (Exception e) {
            throw new RuntimeException("Error initializing MyBatis: ", e);
        }
    }

    public static SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
