package com.xin.eduservice.service.impl;

import com.xin.commonutils.ordervo.CourseWebOrder;
import com.xin.commonutils.ordervo.UcenterMemberOrder;
import com.xin.eduservice.client.EduClient;
import com.xin.eduservice.client.UcenterClient;
import com.xin.eduservice.entity.Order;
import com.xin.eduservice.mapper.OrderMapper;
import com.xin.eduservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xin.eduservice.utils.OrderNoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author xin
 * @since 2023-01-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private EduClient eduClient;
    @Resource
    private UcenterClient ucenterClient;

    @Override
    public String createOrders(String courseId, String memberId) {
        // 远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        // 远程调用根据课程id获取课程信息
        CourseWebOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);

        //返回订单号
        return order.getOrderNo();
    }
}
