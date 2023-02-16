package com.xin.eduservice.controller;

import com.xin.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author xin
 * @since 2023/1/2 10:34
 */
@Api(description="登录模块")
@RestController
@RequestMapping("eduservice/user")
//@CrossOrigin
public class EduLoginController {

    //login
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
