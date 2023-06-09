package com.slj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.slj.common.Result;
import com.slj.domain.User;
import com.slj.service.UserService;
import com.slj.utils.ValidateCodeUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户登录 | 登出接口")
public class UserController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserService userService;

    /**
     * 发送手机短信验证码
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            String object = redisTemplate.opsForValue().get(phone);

            if (object != null) {
                log.info("code={}", object);
                return Result.success("手机验证码短信已发送");
            }

            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);

            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("在水一方外卖","",phone,code);

            // 将生成的验证码缓存到redis，设置有效期2分钟
            redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);

            return Result.success("手机验证码短信发送成功");
        }

        return Result.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     *
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

        Object codeInSession = redisTemplate.opsForValue().get(phone);


        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if (codeInSession != null && codeInSession.equals(code)) {
            //如果能够比对成功，说明登录成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);
            if (user == null) {
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());

            // 用户登录成功 删除redis中的缓存验证码
            redisTemplate.delete(phone);
            return Result.success(user);
        }
        return Result.error("登录失败，验证码不正确");
    }

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    public Result<String> loginout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return Result.success("退出成功");
    }

}
