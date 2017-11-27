package com.lyw.springboot_demo.service;

import com.lyw.springboot_demo.domain.Order;
import com.lyw.springboot_demo.rpt.OrderMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void placeOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus("等待付款");
        order.setPrice("100");
        int i = orderMapper.insertOrder(order);
        assertTrue(i==1);
    }

    @Test
    public void query() {
        Order order = orderMapper.select(1L);
        assertTrue(order != null);
    }
}