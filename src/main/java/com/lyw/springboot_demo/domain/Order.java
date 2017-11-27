package com.lyw.springboot_demo.domain;

import lombok.Data;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Data
public class Order {
    Long id;
    String status;
    String price;
}
