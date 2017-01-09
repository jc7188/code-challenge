package ltv_test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import ltv.Order;

public class OrderTest {
    private static Order order = null;
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd:hh:mm"); 
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Date event_time1 = df.parse("2017-02-06:12:46:52");
        order = new Order("68d84e5d1a43", event_time1,"96f55c7d8f42", "12.34 USD");            
    }

    @Test
    public void test() {
        assertEquals(order.toString(),"Event key: 68d84e5d1a43, "
                + "Event time: Feb 6, 2017 12:46:00 AM, "
                + "customer_id: 96f55c7d8f42, "
                + "total_amount: 12.34 USD , "
                + "total_amount_num: 12.34");
    }

}
