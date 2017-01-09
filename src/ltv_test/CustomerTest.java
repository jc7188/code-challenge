package ltv_test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import ltv.Customer;
import ltv.Order;
import ltv.SiteVisit;

public class CustomerTest {
    static Customer cust1 = null, cust2 = null;
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd:hh:mm"); 
    static double DELTA = 1e-15;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Date event_time1 = null,event_time2 = null;      
        try {
            event_time1 = df.parse("2017-02-06:12:46:52");
            event_time2 = df.parse("2017-02-07:12:45:57");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cust1 = new Customer("96f55c7d8f42", event_time1,"Smith", "Middletown", "AK");
        cust2 = new Customer("16f55c7d8f42", event_time2,"Smith", "Middletown", "AK");
     
       }

    @Test
    public void testAddSite() throws ParseException {
        Date event_time1 = df.parse("2017-03-06:12:46:52");
        Date event_time2 = df.parse("2017-04-07:12:45:57");
        Date event_time3 = df.parse("2017-03-12:12:45:57");
        SiteVisit sv1 = new SiteVisit("sv05e815502f", event_time1, "96f55c7d8f42", "tags");
        SiteVisit sv2 = new SiteVisit("sv15e815502e", event_time2, "96f55c7d8f43", "tags");
        SiteVisit sv3 = new SiteVisit("sv25e815502e", event_time3, "96f55c7d8f43", "tags");       
        cust1.addSite(sv1);
        cust1.addSite(sv2);
        cust1.addSite(sv3);
        assertEquals(cust1.getFirstVisit(), event_time1);
        assertEquals(cust1.getLastVisit(),event_time2);
    }
    
    @Test
    public void testAddOrder() throws ParseException {
        Date event_time1 = df.parse("2017-03-06:12:46:52");
        Date event_time2 = df.parse("2017-04-07:12:45:57");
        Order order1 = new Order("68d84e5d1a43", event_time1,"96f55c7d8f42", "12.34 USD");
        Order order2 = new Order("68d84e5d1a43", event_time2,"96f55c7d8f42", "15.34 USD");
        Order order3 = new Order("78d84e5d1a43", event_time1,"96f55c7d8f42", "52.34 USD");        
        cust1.addOrder(order1);
        cust1.addOrder(order2);
        cust1.addOrder(order3);
        assertEquals(cust1.getOrders(), 
         "Event key: 68d84e5d1a43, Event time: Apr 7, 2017 12:45:00 AM, customer_id: 96f55c7d8f42, total_amount: 15.34 USD , total_amount_num: 15.34\n"
         +"Event key: 78d84e5d1a43, Event time: Mar 6, 2017 12:46:00 AM, customer_id: 96f55c7d8f42, total_amount: 52.34 USD , total_amount_num: 52.34\n");
    }
    @Test
    public void testGetTotalAmount() throws ParseException {
        assertEquals(cust1.getTotalAmount(), 67.68,DELTA);
    }
    
    @Test
    public void testGetWeekNumt() throws ParseException {
        assertEquals(cust1.getWeekNum(), 4,DELTA);
    }
    
    @Test
    public void testCalculateLTV() throws ParseException {
        cust1.calculateLTV();
        assertEquals(cust1.getLTV(), 16.92, DELTA);
    }

}
