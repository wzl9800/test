package com.bjpowernode.service.impl;

import com.bjpowernode.entity.ProductType;
import com.bjpowernode.entity.ProductTypeExample;
import com.bjpowernode.mapper.ProductTypeMapper;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangzhilong
 * @dcreate 2021-11-04 11:26
 */
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    //业务逻辑层一定会有数据访问层的对象
    @Autowired
    ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> getAll() {

        List<ProductType> list = productTypeMapper.selectByExample(new ProductTypeExample());
        return list;
    }
}
