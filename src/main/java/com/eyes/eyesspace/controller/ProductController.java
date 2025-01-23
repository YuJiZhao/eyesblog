package com.eyes.eyesspace.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.model.vo.ProductInfoVO;
import com.eyes.eyesspace.model.vo.ProductListInfoVO;
import com.eyes.eyesspace.result.Result;
import com.eyes.eyesspace.service.IProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private IProductService productService;

    @Permission
    @GetMapping("/getProductPage")
    public Result<Page<ProductInfoVO>> getProductPage(@NotNull(message = "页数不能为空") Integer pageNo) {
        return Result.success(productService.getProductPage(pageNo));
    }

    @Permission
    @GetMapping("/getProductListInfo")
    public Result<ProductListInfoVO> getProductListInfo() {
        return Result.success(productService.getProductListInfo());
    }
}
