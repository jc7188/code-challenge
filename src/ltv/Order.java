package ltv;

import java.util.Date;

/**
 * Description : Order class holds order information, including customer_id, total_amount
 *               
 */

public class Order extends Event {
    private String customer_id;
    private String total_amount;
    //remove currency from total_amount
    private double total_amount_num;
    
    public Order(String key, Date event_time, String customer_id, String total_amount) {
        super(key, event_time);
        String[] amount_array = total_amount.trim().split(" "); 
        double total_amount_num = Double.parseDouble(amount_array[0]);
        this.customer_id = customer_id;
        this.total_amount = total_amount;
        this.total_amount_num = total_amount_num;
        
    }   

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public double getTotal_amount_num() {
        return total_amount_num;
    }

    public void setTotal_amount(double total_amount_num) {
        this.total_amount_num = total_amount_num;
    }
    
    public String toString() {
        return super.toString() + ", customer_id: " + this.customer_id + ", total_amount: " + this.total_amount + " , total_amount_num: "+ this.total_amount_num;
    }
    
    /**
     * User unique order_id for hashing.
     */
    public int hashCode() {
        return super.getKey().hashCode();
    }
    
}
