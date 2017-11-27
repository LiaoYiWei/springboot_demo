package com.lyw.springboot_demo.service;

import com.lyw.springboot_demo.domain.Order;
import com.lyw.springboot_demo.rpt.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Transactional
    public Order placeOrder(Order order) {
        addOrder(order);
        Pay();
        return order;
    }

    private void Pay() {
        //抛出异常事务回滚
        //TODO
        //throw new RuntimeException();
    }

    private void addOrder(Order order) {
        orderMapper.insertOrder(order);
    }

    public Order query(Long id) {
        return orderMapper.select(id);
    }
}
