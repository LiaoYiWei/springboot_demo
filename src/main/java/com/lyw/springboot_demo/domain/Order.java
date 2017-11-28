package com.lyw.springboot_demo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Data
public class Order implements Serializable {
    Long id;
    String status;
    String price;
}
