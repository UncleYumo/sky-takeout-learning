package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.utils.StringUtil;
import com.sky.vo.DishVO;
import org.aspectj.apache.bcel.ExceptionConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author uncle_yumo
 * @fileName DishServiceImpl
 * @createDate 2025/2/20 February
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增菜品及其对应口味数据
     */
    @Transactional  // 事务注解，用于事务控制，保证数据的一致性，要么全成功要么全失败
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);  // 将dto数据复制到dish实体类中

        // 向菜品表插入一条数据
        dishMapper.insert(dish);

        // 获取insert语句生成的主键值
        Long dishId = dish.getId();

        // 向菜品口味表插入多条数据（如果有的话）
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if ( flavors != null && !flavors.isEmpty()) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);  // 设置菜品id
            }
            // 向口味表中插入数据
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<DishVO> page = (Page<DishVO>) dishMapper.pageQuery(queryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional  // 事务注解，用于事务控制，保证数据的一致性，要么全成功要么全失败
    public void deleteBatch(List<Long> ids) {
        // 一次可以删除一个或多个数据
        // 起手中的菜品不能删除
        // 被套餐关联的菜品不能删除
        // 删除菜品后，对应关联的口味数据也要删除

        List<Dish> dishes = dishMapper.getByIds(ids);
        List<Long> idsOnSale = new ArrayList<>();
        // 1. 判断当前菜品是否能够删除
        dishes.forEach(dish -> {
            if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE)) {
                // 如果存在起售中的菜品状态为启用，则不能删除
                idsOnSale.add(dish.getId());
            }
        });

        if (!idsOnSale.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE + ": " + idsOnSale.toString());
        }

        // 2. 判断当前菜品是否被套餐关联
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            // 有套餐关联的菜品不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 3. 删除菜品表中的菜品数据
        dishMapper.deleteByIds(ids);

        // 4. 删除菜品关联的口味数据
        dishFlavorMapper.deleteByDishIds(ids);

    }
}
