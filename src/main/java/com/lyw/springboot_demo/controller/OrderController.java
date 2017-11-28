package com.lyw.springboot_demo.controller;

import com.lyw.springboot_demo.common.OrderEditor;
import com.lyw.springboot_demo.domain.Order;
import com.lyw.springboot_demo.facade.IOrderFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
//@Api(value = "订单相关api")
//@Controller
//@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderFacade orderService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Order.class, new OrderEditor());
    }

    @ApiOperation(value = "创建订单")
    @PostMapping
    public @ResponseBody
    Order addOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @ApiOperation(value = "查询订单")
    @GetMapping("/{id}")
    public @ResponseBody
    Order queryOrder(@PathVariable("id") Long id) {
        return orderService.query(id);
    }

    @ApiOperation(value = "创建订单")
    @PostMapping("/acceptMultiBean")
    public @ResponseBody
    void acceptMultiBean(@RequestParam("order") Order order, @RequestParam("order1") Order order1) {
        System.out.println(order);
        System.out.println(order1);
    }

}
