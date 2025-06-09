package com.solvd.laba.mappers;

import com.solvd.laba.models.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    List<Order> getOrdersByUserId(@Param("userId") int userId);
}
