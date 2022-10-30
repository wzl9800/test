package com.bjpowernode.service.impl;

import com.bjpowernode.entity.ProductInfo;
import com.bjpowernode.entity.ProductInfoExample;
import com.bjpowernode.entity.vo.ProductInfoVo;
import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangzhilong
 * @dcreate 2021-11-03 20:13
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;
    @Override
    public List<ProductInfo> getAll() {

        List<ProductInfo> list = productInfoMapper.selectByExample(new ProductInfoExample());
        return list;
    }

    //select * from product_info limit 起始记录数=（（当前页-1）*每页的条数），每页取几条
    //selrct * from product_info limit 10,5
    @Override
    public PageInfo splitPage(int pageNun, int pageSize) {
        //分页插件使用PageHelper工具类完成分页设置
        PageHelper.startPage(pageNun,pageSize);

        //进行PageInfo的数据封装
        //进行有条件的查询操作，必须要创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
        //设置排序，按主键降序排序
        //select * from product_info order by p_id desc
        example.setOrderByClause("p_id desc");
        //设置完排序后，取集合，切记切记：一定在取集合之前，设置PageHelper.startPage(pageNun,pageSize);
        List<ProductInfo> list  = productInfoMapper.selectByExample(example);
        //将查询到的集合封装进PageInfo对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int addProduct(ProductInfo info) {
        return productInfoMapper.insertSelective(info);
    }

    @Override
    public int deleteProduct(int pid) {
        int sum = productInfoMapper.deleteByPrimaryKey(pid);
        return sum;
    }

    @Override
    public ProductInfo selectProduct(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int updateProduct(ProductInfo productInfo) {
        return productInfoMapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public int deleteBatch(String []ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectProduct(vo);
    }


}
