package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName DishService
 * @createDate 2025/2/20 February
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Service
public interface DishService {

    /**
     * 新增菜品及其对应口味数据
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 分页查询菜品及其对应口味数据
     */
    PageResult pageQuery(DishPageQueryDTO queryDTO);

    /**
     * 根据ids批量删除菜品及其对应口味数据
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
