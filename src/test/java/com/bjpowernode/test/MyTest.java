package com.bjpowernode.test;

import com.bjpowernode.entity.ProductInfo;
import com.bjpowernode.entity.vo.ProductInfoVo;
import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author wangzhilong
 * @dcreate 2021-11-03 18:58
 */
//测试加密工具
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest {
    @Test
    public void testMD5(){
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);
    }

    @Test
    public void testPath(){
       String str = "1,2,3,4,";
       String [] as = str.split(",");
       for(String a:as){
           System.out.println(a);
       }
    }

    @Autowired
    ProductInfoMapper mapper;
    @Test
    public void testProductInfoMapper(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setPname("4");
        vo.setTypeid(3);
        vo.setLprice(3000);
        vo.setHprice(3999);
        List<ProductInfo> productInfos =  mapper.selectProduct(vo);
        productInfos.forEach(productInfo -> System.out.println(productInfo));
    }

    @Test
    public void Int(){
        String str = String.valueOf(3);
        Integer tt = Integer.valueOf(100);
        String t = String.valueOf('8');
        Integer i = Integer.valueOf("144");
    }
}
