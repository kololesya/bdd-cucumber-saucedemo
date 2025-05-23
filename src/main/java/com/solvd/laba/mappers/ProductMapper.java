package com.solvd.laba.mappers;

import com.solvd.laba.models.Product;
import java.util.List;


import org.apache.ibatis.annotations.Param;

public interface ProductMapper {

    Product getProductById(@Param("id") Long id);

    List<Product> getAllProducts();
}
