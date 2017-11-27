package com.lyw.springboot_demo.controller;

import com.lyw.springboot_demo.domain.Order;
import com.lyw.springboot_demo.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    OrderService orderService;


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void addOrder(@RequestBody Order order) {
        orderService.placeOrder(order);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Order queryOrder(@PathVariable("id") Long id) {
        return orderService.query(id);
    }

}
