package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class CommonController {

        @Autowired
        private CommonService commonService;
        @PostMapping("/upload")
        @ApiOperation("上传文件")
        public Result<String> upload(@RequestBody MultipartFile file) throws IOException {
         String url= commonService.upload(file);
         if (url.isEmpty())
             return Result.error(MessageConstant.UPLOAD_FAILED);
         return Result.success(url);
        }
}
