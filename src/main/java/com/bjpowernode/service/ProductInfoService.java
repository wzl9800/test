package com.bjpowernode.service;

import com.bjpowernode.entity.ProductInfo;
import com.bjpowernode.entity.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author wangzhilong
 * @dcreate 2021-11-03 20:09
 */
public interface ProductInfoService {
     //查询所有的商品信息(不分页)
     List<ProductInfo> getAll();

     //分页功能的实现
    PageInfo splitPage(int pageNun,int pageSize);

    //添加商品
    int addProduct(ProductInfo info);

    //单个删除商品
    int deleteProduct(int pid);

    //按主键id查询商品
    ProductInfo selectProduct(int pid);

    //编辑商品(更新商品)
    int updateProduct(ProductInfo productInfo);

    //批量删除商品
    int deleteBatch(String [] ids);

    //多条件查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);
}
