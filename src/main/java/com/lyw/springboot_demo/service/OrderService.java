package com.lyw.springboot_demo.service;

import com.lyw.springboot_demo.common.OrderEditor;
import com.lyw.springboot_demo.domain.Order;
import com.lyw.springboot_demo.facade.IOrderFacade;
import com.lyw.springboot_demo.rpt.OrderMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Api(value = "订单相关api")
@Controller
@RequestMapping("/order")
public class OrderService implements IOrderFacade {

    @Autowired
    OrderMapper orderMapper;

    @Override
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

    @Override
    public Order query(Long id) {
        return orderMapper.select(id);
    }


}


