package com.solvd.laba.mappers;

import com.solvd.laba.models.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User getUserByUsername(@Param("username") String username);
}
