package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName DishController
 * @createDate 2025/2/20 February
 * @school 无锡学院
 * @studentID 22344131
 * @description 菜品管理控制器
 */

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 保存菜品或新增菜品
     * @param dishDTO 菜品信息
     * @return Result
     */
    @PostMapping()
    @ApiOperation(value = "保存菜品", notes = "保存菜品信息")  // value：接口名称，notes：接口描述
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询", notes = "分页查询菜品信息")
    public Result<PageResult> page(DishPageQueryDTO queryDTO) {
        log.info("分页查询菜品: {}", queryDTO);
        PageResult pageResult = dishService.pageQuery(queryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping()
    @ApiOperation("菜品的批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除菜品: {}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }
}
