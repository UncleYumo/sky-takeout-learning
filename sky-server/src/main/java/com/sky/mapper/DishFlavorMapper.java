package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName DishFlavorMapper
 * @createDate 2025/2/23 February
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Mapper
public interface DishFlavorMapper {
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品ids删除其所有配料
     * @param ids 菜品ids
     */
//    @Delete("DELETE FROM dish_flavor WHERE dish_id IN (#{idsStr})")
    void deleteByDishIds(List<Long> ids);

    /**
     * 根据菜品id删除其所有配料
     */
    @Delete("DELETE FROM dish_flavor WHERE dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    @Select("SELECT * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
