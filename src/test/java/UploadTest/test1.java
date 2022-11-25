package UploadTest;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/8 16:26
 **/
public class test1 {
    @Test
    public void uploadTest(){
        String s = UUID.randomUUID().toString().toString();
        System.out.println(s);
        String picName = "h.sa,.jh.glo.jpg";
        String lastFileName = picName.substring(picName.lastIndexOf("."));
        System.out.println(lastFileName);
    }
}
