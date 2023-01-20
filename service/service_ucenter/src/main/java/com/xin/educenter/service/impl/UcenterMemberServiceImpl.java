package com.xin.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xin.commonutils.JwtUtils;
import com.xin.educenter.entity.UcenterMember;
import com.xin.educenter.entity.vo.RegisterVo;
import com.xin.educenter.mapper.UcenterMemberMapper;
import com.xin.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xin.educenter.utils.MD5;
import com.xin.servicebase.exceptionHandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xin
 * @since 2023-01-13
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        // 获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        // 手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001, "登录失败，用户名或密码为空！");
        }

        // 判断手机号是否正确
        LambdaQueryWrapper<UcenterMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UcenterMember::getMobile, mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        // 判断查询对象是否为空
        if(mobileMember == null) {
            throw new GuliException(20001, "登录失败，手机号不存在。");
        }
        // 判断密码
        // 因为存储到数据库的密码是加密的
        // 把输入的密码先进行加密，再和数据库的密码进行比较
        // 加密方式 MD5

        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "登录失败，密码错误。");
        }

        // 判断用户是否被禁用
        if(mobileMember.getIsDeleted()){
            throw new GuliException(20001, "登录失败，用户被禁用");
        }

        // 登录成功
        // 生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;

    }
    // 注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        // 非空判断
        //非空判断
        if(org.springframework.util.StringUtils.isEmpty(mobile) || org.springframework.util.StringUtils.isEmpty(password)
                || org.springframework.util.StringUtils.isEmpty(code) || org.springframework.util.StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001,"注册失败");
        }

        // 判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"注册失败，验证码错误！");
        }

        // 判断手机号是否重复，表里面存在相同手机号不进行添加
        LambdaQueryWrapper<UcenterMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UcenterMember::getMobile, mobile);
        Integer count = baseMapper.selectCount(wrapper);

        if(count > 0){
            throw new GuliException(20001, "注册失败，手机号已被注册！");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setIsDisabled(false);
        member.setAvatar("https://xin-guli-1010.oss-cn-guangzhou.aliyuncs.com/111.jpg");
        baseMapper.insert(member);


    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        LambdaQueryWrapper<UcenterMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UcenterMember::getOpenid, openid);
        UcenterMember member = baseMapper.selectOne(wrapper);

        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {

        return baseMapper.countRegisterDay(day);
    }
}
