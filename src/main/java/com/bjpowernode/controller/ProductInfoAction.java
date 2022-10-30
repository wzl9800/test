package com.bjpowernode.controller;

import com.bjpowernode.entity.ProductInfo;
import com.bjpowernode.entity.vo.ProductInfoVo;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author wangzhilong
 * @dcreate 2021-11-03 20:49
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    //每页显示的记录数
    public static final int PAGE_SIZE = 5;

    //异步上传的图片的名称
    String saveFileName = "";
    //切记：在界面层中，一定会有业务逻辑层的对象
    @Autowired
    ProductInfoService productInfoService;

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String product(HttpServletRequest request) {

        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list", list);
        return "product";
    }

    //显示第一页的5条数据
    @RequestMapping("/split")
    public String split(HttpServletRequest request) {
        PageInfo info = productInfoService.splitPage(1, PAGE_SIZE);
        request.setAttribute("info", info);
        return "product";
    }

    //分页显示数据
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public String ajaxsplit(int page, HttpSession session) {
        PageInfo info = productInfoService.splitPage(page, PAGE_SIZE);
        session.setAttribute("info", info);
        return "product";
    }

    //图片回显的处理
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request) {
        //提取生成文件名UUID+上传图片的后缀 .jpg .png
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());

        //得到项目中图片存储的路径  http://localhost:8080/mimissm/
        String path = request.getServletContext().getRealPath("/image_big");
        //转存 http://localhost:8080/mimissm/img_big/+saveFileName
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端JSON对象，封装图片的路径，为了在页面实现立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();
    }
    //添加商品
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        //还有表单提交上来的5个数据
        int num = -1;
        try {
            num = productInfoService.addProduct(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num == -1){
            request.setAttribute("msg","添加商品失败");
        }else {
            request.setAttribute("msg","添加商品成功");
        }
        //清空saveFileName变量中的内容，为了下次增加或者修改的异步ajax的上传处理
        saveFileName = "";
        //增加成功后应该重新访问数据库，所以跳转到分页显示的action上
        return "forward:/prod/split.action";
    }

    //删除单个商品
    @RequestMapping("/delete")
    public String delete(int pid,HttpServletRequest request){
        int sum = -1;
        try {
            sum = productInfoService.deleteProduct(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(sum > 0){
            request.setAttribute("msg","删除商品成功");
        }else {
            request.setAttribute("msg","删除商品失败");
        }
        return "forward:/prod/split.action";
    }

    //回显选中id的商品
    @RequestMapping("/one")
    public String updateOne(int pid,HttpServletRequest request){
        ProductInfo productInfo = productInfoService.selectProduct(pid);
        request.setAttribute("prod",productInfo);
        return "update";
    }

    //完成更新操作
    @RequestMapping("/update")
    public String update(ProductInfo productInfo,HttpServletRequest request){
        if(!saveFileName.equals("")){
            productInfo.setpImage(saveFileName);
        }

        int num = -1;
        try {
            num = productInfoService.updateProduct(productInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num > 0 ){
            request.setAttribute("msg","更新成功");
        }else {
            request.setAttribute("msg","更新失败");
        }

        saveFileName = "";
        return "forward:/prod/split.action";
    }

    //批量删除商品
    @RequestMapping("/deletebatch")
    public String deletebatch(String str,HttpServletRequest request){

        String []ps = str.split(",");
        int sum = productInfoService.deleteBatch(ps);
        try {
            if(sum > 0){
                request.setAttribute("msg","批量删除商品成功");
            }else {
                request.setAttribute("msg","批量删除商品失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品不可删除");
        }
        return "forward:/prod/split.action";
    }

    //多条件查询
    @ResponseBody
    @RequestMapping("/condition")
    public void selectCondition(ProductInfoVo vo,HttpServletRequest request){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        request.setAttribute("list",list);
    }
}
