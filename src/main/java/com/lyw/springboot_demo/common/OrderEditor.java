package com.lyw.springboot_demo.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyw.springboot_demo.domain.Order;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
public class OrderEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        Order value = null;
        try {
            value = mapper.readValue(text,Order.class);
        } catch (IOException e) {
            // handle error
        }
        setValue(value);
    }
}
