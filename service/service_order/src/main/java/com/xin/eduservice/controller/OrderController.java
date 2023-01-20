package com.xin.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xin.commonutils.JwtUtils;
import com.xin.commonutils.R;
import com.xin.commonutils.ordervo.CourseWebOrder;
import com.xin.eduservice.entity.Order;
import com.xin.eduservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author xin
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Resource
    private OrderService orderService;

    // 生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        // 创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId,  JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("orderId", orderNo);
    }

    //2 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    // 根据课程id和用户id查询订单表中订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getCourseId, courseId);
        wrapper.eq(Order::getMemberId, memberId);
        wrapper.eq(Order::getStatus,1);
        int count = orderService.count(wrapper);

        return count > 0;

    }


}

