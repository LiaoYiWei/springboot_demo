package com.lyw.springboot_demo.facade;

import com.lyw.springboot_demo.domain.Order;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
public interface IOrderFacade {

    Order placeOrder(Order order);

    Order query(Long id);
}
