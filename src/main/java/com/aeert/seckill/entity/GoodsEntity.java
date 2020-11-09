package com.aeert.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author l'amour solitaire
 * @Description 抢购商品表
 * @Date 2020/11/9 上午9:19
 **/
@Data
@TableName("tb_goods")
@Accessors(chain = true)
public class GoodsEntity implements Serializable {
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
     * 抢购开始时间
     */
    private Date startDate;
    /**
     * 抢购结束时间
     */
    private Date endDate;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 库存数量
     */
    private Integer amount;
    /**
     * 售价
     */
    private BigDecimal price;

}
