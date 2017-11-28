package com.lyw.springboot_demo.controller;

import com.lyw.springboot_demo.domain.Order;
import com.lyw.springboot_demo.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Api(value = "订单相关api")
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService orderService;


    @ApiOperation(value = "创建订单")
    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    Order addOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @ApiOperation(value = "查询订单")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Order queryOrder(@PathVariable("id") Long id) {
        return orderService.query(id);
    }


    @ApiOperation(value = "创建订单")
    @RequestMapping(value = "/acceptMultiBean", method = RequestMethod.POST)
    public @ResponseBody
    void acceptMultiBean(@RequestAttribute("order") Order order, @RequestAttribute("order1") Order order1) {
        System.out.println(order);
        System.out.println(order1);
    }

}
