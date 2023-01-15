package com.xin.educenter.controller;


import com.xin.commonutils.JwtUtils;
import com.xin.commonutils.R;
import com.xin.educenter.entity.UcenterMember;
import com.xin.educenter.entity.vo.RegisterVo;
import com.xin.educenter.service.UcenterMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xin
 * @since 2023-01-13
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Resource
    UcenterMemberService memberService;

    // 登录
    @PostMapping("login")
    public R login(@RequestBody UcenterMember member){
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(member);

        return R.ok().data("token",token);
    }

    // 注册
    @PostMapping("register")
    public R regiesterUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

}

