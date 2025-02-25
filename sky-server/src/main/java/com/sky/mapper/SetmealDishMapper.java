package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName SetmealDishMapper
 * @createDate 2025/2/25 February
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */
@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id获取套餐id
     * @return 套餐ids
     */
//    @Select("SELECT setmeal_id FROM setmeal_dish WHERE dish_id IN (#{ids})")
    List<Long> getSetmealIdsByDishIds(List<Long> ids);
}
