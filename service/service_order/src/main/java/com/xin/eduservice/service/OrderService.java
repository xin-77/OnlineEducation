package com.xin.eduservice.service;

import com.xin.eduservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author xin
 * @since 2023-01-16
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberId);
}
