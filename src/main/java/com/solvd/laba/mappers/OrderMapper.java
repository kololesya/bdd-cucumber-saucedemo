package com.solvd.laba.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.solvd.laba.models.Order;

public interface OrderMapper {

    List<Order> getOrdersByUserId(@Param("userId") Long userId);
}
