package com.fehead.testproject.service.impl.impl;

import com.fehead.testproject.dataobject.ImagesDO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageServiceImpl {

    public Map<String, String> choseImage(String filename,String filepath) throws UnsupportedEncodingException {
        ImagesDO imagesDO=new ImagesDO();
        imagesDO.setIMAGE_NAME(filename);
        imagesDO.setPATH(filepath);
        imagesDO.setAPI_KEY("");//你自己账号里面的信息
        imagesDO.setAPPID("");
        imagesDO.setURL("");
        Map<String, String> b=this.buildHttpHeader(imagesDO);
        return b;
    }


    private  Map<String, String> buildHttpHeader(ImagesDO imagesDO) throws UnsupportedEncodingException {
        String curTime = System.currentTimeMillis() / 1000L + "";

        String param = "{\"image_name\":\"" + imagesDO.getIMAGE_NAME() + "\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
        String checkSum = DigestUtils.md5Hex(imagesDO.getAPI_KEY() + curTime + paramBase64);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", imagesDO.getAPPID());
        return header;
    }
}
