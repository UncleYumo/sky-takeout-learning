package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品数据
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询
     * @param queryDTO
     * @return PageResult
     */
    Page<DishVO> pageQuery(DishPageQueryDTO queryDTO);

    /**
     * 根据id查询菜品
     *
     * @param id
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * 根据ids批量查询菜品
     */
//    @Select("select * from dish where id in (#{ids})")
    List<Dish> getByIds(List<Long> ids);

    /**
     * 根据ids删除Dish
     * @param ids
     */
//    @Delete("DELETE FROM dish WHERE id IN (#{ids})")
    void deleteByIds(List<Long> ids);

    /**
     * 根据传入的信息动态更新dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
}
