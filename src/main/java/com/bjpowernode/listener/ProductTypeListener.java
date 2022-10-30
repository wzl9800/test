package com.bjpowernode.listener;

import com.bjpowernode.entity.ProductType;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.service.ProductTypeService;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @author wangzhilong
 * @dcreate 2021-11-04 11:31
 */
//全局的监听器（将获取到的产品类型放入到全局作用域中）
@WebListener
public class ProductTypeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手工从Spring容器中取出ProductTypeServiceImpl的对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) ac.getBean("ProductTypeServiceImpl");
        List<ProductType> list = productTypeService.getAll();
        //放入全局应用的作用域中，供新增页面，修改页面，前台的查询功能提供全部商品商品类别集合
        servletContextEvent.getServletContext().setAttribute("typelist",list);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
