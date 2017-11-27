package com.lyw.springboot_demo.rpt;

import com.lyw.springboot_demo.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Mapper
public interface OrderMapper {

    @Insert("insert INTO `Order` VALUES (#{id},#{status},#{price})")
    int insertOrder(Order order);

    @Select("select * from `Order` where id = #{value}")
    Order select(Long id);
}
