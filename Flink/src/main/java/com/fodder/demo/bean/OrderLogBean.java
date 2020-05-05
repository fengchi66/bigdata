package com.fodder.demo.bean;

/**
 * @author wufc
 * @create 2020-04-19 1:52 上午
 */
public class OrderLogBean {
    public Long userId;
    public String itemType;
    public int amount;
    public Long timeStamp;

    public OrderLogBean(Long userId, String itemType, int amount, Long timeStamp) {
        this.userId = userId;
        this.itemType = itemType;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }


    @Override
    public String toString() {
        return "OrderLogBean{" +
                "userId=" + userId +
                ", itemType='" + itemType + '\'' +
                ", amount=" + amount +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
