package com.lyw.springboot_demo.service;

import com.lyw.springboot_demo.domain.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
public interface IOrderService {

    Order placeOrder(Order order);

    Order query(Long id);
}
