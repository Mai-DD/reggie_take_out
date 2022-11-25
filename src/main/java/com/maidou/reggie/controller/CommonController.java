package com.maidou.reggie.controller;

import com.maidou.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/8 16:03
 **/
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //获取的file临时文件,进行转存
        //获取文件原名,截取小数点后的后缀
        String originalFilename = file.getOriginalFilename();
        //UUID生成不重复的文件名
        String s = UUID.randomUUID().toString();
        String fileName = s + originalFilename.substring(originalFilename.lastIndexOf("."));


        //判断路径是否存在,不存在则创建
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            file.transferTo(new File(basePath + fileName));
            log.info("转存图片到:{}", basePath + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            //从文件读取
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //响应
            ServletOutputStream outputStream = response.getOutputStream();

            byte bytes[] = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            //关闭资源
            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
