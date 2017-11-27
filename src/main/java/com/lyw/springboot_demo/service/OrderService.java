package com.lyw.springboot_demo.service;

import com.lyw.springboot_demo.domain.Order;
import com.lyw.springboot_demo.rpt.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Service
public class OrderService {

    OrderMapper orderMapper;

    @Transactional
    public void placeOrder(Order order) {
        addOrder(order);
        Pay();
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
