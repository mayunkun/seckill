package com.aeert.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author l'amour solitaire
 * @Description 抢购订单表
 * @Date 2020/11/9 上午9:19
 **/
@Data
@TableName("tb_order")
@Accessors(chain = true)
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date modifyDate;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 订单状态(1:待支付 2:支付成功 3:过期 4:取消支付)
     */
    private Integer status;

}
