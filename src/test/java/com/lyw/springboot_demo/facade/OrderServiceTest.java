package com.lyw.springboot_demo.facade;

import com.lyw.springboot_demo.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:dubbo-customer.xml")
public class OrderServiceTest {

    @Resource
    IOrderFacade orderFacadeClient;

    @Test
    public void dubboClientTest() {
        Order query = orderFacadeClient.query(1L);
        System.out.println("-------" + query + "-------");
    }
}