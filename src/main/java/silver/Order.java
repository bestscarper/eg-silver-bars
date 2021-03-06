package silver;

import com.google.common.primitives.UnsignedLong;

public class Order implements Comparable {

    public Order(String userId, UnsignedLong quantityGrammes, UnsignedLong price, OrderType orderType) {
        this.userId = userId;
        this.quantityGrammes = quantityGrammes;
        this.price = price;
        this.orderType = orderType;
    }

    private String userId;
    private UnsignedLong quantityGrammes;
    private UnsignedLong price;
    private OrderType orderType;

    public String getUserId() {
        return userId;
    }

    public UnsignedLong getQuantityGrammes() {
        return quantityGrammes;
    }

    public UnsignedLong getPrice() {
        return price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public boolean isOfType(OrderType orderType) {
        return this.orderType.equals(orderType);
    }

    @Override
    // BUY comes before SELL
    // then
    //   buy orders - highest price first
    //   sell orders - lowest price first
    public int compareTo(Object o) {
        Order order = (Order)o;
        if (this.orderType == order.orderType) {
            if (this.orderType.equals(OrderType.BUY)) {
                return order.price.compareTo(this.price);
            } else {
                return this.price.compareTo(order.price);
            }
        }
        else {
            return this.orderType.compareTo(order.orderType);
        }
    }

    @Override
    public String toString() {
        // e.g. 5.5 kg for £306
        return String.format("%.1f kg for £%d\n", this.quantityGrammes.doubleValue()/1000, this.price.longValue());
    }

    public Order mergeSamePrice(Order order) {
        if (!this.orderType.equals(order.orderType)) {
            throw new RuntimeException("Can't merge different order types");
        }
        return new Order("<merged>", this.quantityGrammes.plus(order.quantityGrammes), this.price, this.orderType);
    }

}
