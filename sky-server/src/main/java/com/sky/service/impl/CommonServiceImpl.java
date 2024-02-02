package com.sky.service.impl;

import com.sky.mapper.CommonMapper;
import com.sky.service.CommonService;
import com.sky.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonMapper commonMapper;
    private AliOssUtil aliOssUtil=new AliOssUtil("oss-cn-hangzhou.aliyuncs.com",
            "LTAI5tM7oc7yo7eBKYmSKy8M",
            "5twVILeGKWUCzPMIrYRAMzPcxgGaEk",
            "web-mytalis9669");
    @Override
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(i);
        String name = UUID.randomUUID().toString()+substring;
        return aliOssUtil.upload(file.getBytes(),name);
    }
}
