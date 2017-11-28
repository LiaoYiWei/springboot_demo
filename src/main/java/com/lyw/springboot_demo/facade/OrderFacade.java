package com.lyw.springboot_demo.facade;

import com.lyw.springboot_demo.common.OrderEditor;
import com.lyw.springboot_demo.domain.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Api(value = "订单相关api")
@Controller
@RequestMapping("/order")
public class OrderFacade implements IOrderFacade {

    @Autowired
    IOrderFacade orderFacade;

    @ApiOperation(value = "创建订单")
    @PostMapping
    @Override
    public @ResponseBody
    Order placeOrder(@RequestBody Order order) {
        return orderFacade.placeOrder(order);
    }


    @Override
    @ApiOperation(value = "查询订单")
    @GetMapping("/{id}")
    public @ResponseBody
    Order query(@PathVariable("id") Long id) {
        return orderFacade.query(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Order.class, new OrderEditor());
    }
}


