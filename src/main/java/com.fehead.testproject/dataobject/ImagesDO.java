package com.fehead.testproject.dataobject;

import lombok.Data;

@Data
public class ImagesDO {

    private   String URL = "";//填写你自己账号里的信息
    // 应用ID
    private   String APPID = "";
    // 接口密钥
    private   String API_KEY = "";
    // 图片名称
    public  String IMAGE_NAME ;
    // 图片路径
    private String PATH ;
}
