package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.model.entity.Product;
import com.eyes.eyesspace.model.vo.ProductInfoVO;
import com.eyes.eyesspace.model.vo.ProductListInfoVO;

/**
 * @author eyesYeager
 * @since 2025-01-23
 */
public interface IProductService extends IService<Product> {

    Page<ProductInfoVO> getProductPage(Integer pageNo);

    ProductListInfoVO getProductListInfo();
}
