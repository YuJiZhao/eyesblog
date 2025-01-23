package com.eyes.eyesspace.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.mapper.ProductMapper;
import com.eyes.eyesspace.model.entity.Product;
import com.eyes.eyesspace.model.vo.ProductInfoVO;
import com.eyes.eyesspace.model.vo.ProductListInfoVO;
import com.eyes.eyesspace.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author eyesYeager
 * @since 2025-01-23
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    private static final int PRODUCT_PAGE_SIZE = 6;

    @Resource
    private ProductMapper productMapper;

    @Override
    public Page<ProductInfoVO> getProductPage(Integer pageNo) {
        String role = UserInfoHolder.getRole();
        Page<Product> page = new Page<>(pageNo, PRODUCT_PAGE_SIZE);
        Page<Product> productPage = productMapper.selectPage(page, Wrappers.<Product>lambdaQuery()
                .ne(AuthConfigConstant.ROLE_ADMIN.equals(role), Product::getStatus, StatusEnum.DELETE.getStatus())
                .eq(!AuthConfigConstant.ROLE_ADMIN.equals(role), Product::getStatus, StatusEnum.PUBLIC.getStatus())
                .orderByDesc(Product::getCreateTime));
        Page<ProductInfoVO> result = new Page<>();
        result.setTotal(productPage.getTotal());
        if (CollectionUtils.isEmpty(productPage.getRecords())) {
            return result;
        }
        List<ProductInfoVO> productInfoVOList = productPage.getRecords().stream().map(v -> {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(v, productInfoVO);
            return productInfoVO;
        }).toList();
        result.setRecords(productInfoVOList);
        return result;
    }

    @Override
    public ProductListInfoVO getProductListInfo() {
        String role = UserInfoHolder.getRole();
        List<Product> products = productMapper.selectList(Wrappers.<Product>lambdaQuery()
                .select(Product::getType)
                .ne(AuthConfigConstant.ROLE_ADMIN.equals(role), Product::getStatus, StatusEnum.DELETE.getStatus())
                .eq(!AuthConfigConstant.ROLE_ADMIN.equals(role), Product::getStatus, StatusEnum.PUBLIC.getStatus())
        );
        Set<String> typeSet = new HashSet<>();
        for (Product product : products) {
            String type = product.getType();
            if (StringUtils.isEmpty(type)) {
                continue;
            }
            String[] split = type.split(" ");
            typeSet.addAll(Arrays.asList(split));
        }
        return new ProductListInfoVO(products.size(), typeSet.size());
    }
}
