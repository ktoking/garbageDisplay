package com.fehead.testproject.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fehead.testproject.controller.utils.FileUtil;
import com.fehead.testproject.controller.utils.HttpUtil;
import com.fehead.testproject.dataobject.LabelDO;
import com.fehead.testproject.response.CommonReturnType;
import com.fehead.testproject.service.impl.impl.ImageServiceImpl;
import com.fehead.testproject.service.impl.impl.LabelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RequestMapping("/images")
@RestController
public class picturesController {


    @Autowired
    private ImageServiceImpl imageService;
    @Autowired
    private LabelServiceImpl labelService;



    @PostMapping("/upload")
    public List<LabelDO> identityAudit(HttpServletResponse response, HttpServletRequest request) throws IOException {
        System.out.println("进入get方法！");

        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = req.getFile("file"); //对应前端页面的name值

//        String path = request.getSession().getServletContext().getRealPath("/image");

        String realPath = "E:/image";

        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        //生成一个新的文件名fileName
        String n = UUID.randomUUID().toString().substring(0, 11);
        String picName = n + "." + "jpg";
        String d = realPath+"/"+ picName;
        File file = new File(realPath, picName);
        multipartFile.transferTo(file);
        //返回图片名

        Map<String, String> bulider=imageService.choseImage(picName,d);
        byte[] imageByteArray = FileUtil.read(d);
        String result = HttpUtil.doPost1("http://tupapi.xfyun.cn/v1/currency", bulider, imageByteArray);
        System.out.println("接口调用结果：" + result);


        JSONObject jsonObject = JSON.parseObject(result);

        JSONArray jsonArray=jsonObject.getJSONObject("data").getJSONArray("fileList").getJSONObject(0).getJSONArray("labels");


        List<Integer> list = new ArrayList<>();//获取五个最接近的物体的label
        for(Object jstr:jsonArray){
            list.add(Integer.parseInt(String.valueOf(jstr)));
        }

        List<LabelDO> labelDOS=labelService.selectLabel(list);

        return labelDOS;

//        PrintWriter printWriter = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        HashMap<String, Object> res = new HashMap<String, Object>();
//        res.put("success", true);
//        printWriter.write(JSON.toJSONString(d));
//        printWriter.flush();
    }
}
