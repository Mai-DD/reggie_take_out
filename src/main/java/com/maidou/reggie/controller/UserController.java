package com.maidou.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maidou.reggie.common.R;
import com.maidou.reggie.entity.User;
import com.maidou.reggie.service.UserService;
import com.maidou.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/12 17:05
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 手机端登录
     * 没有做验证码登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        //1.获取手机号码
        String phone = map.get("phone").toString();

        //生成验证码
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
        log.info("生成验证码: {}",code);

        if(!phone.isEmpty()){
            log.info("手机号: {}",phone);
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            //2.初次登录新增user
            if(user == null){
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }

            //登录成功,删除验证码
            redisTemplate.delete(phone);
            session.setAttribute("user",user.getId());

            //3.取出
            return R.success(user);
        }

        return R.error("登录失败");
    }
}
