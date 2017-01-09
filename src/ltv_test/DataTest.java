package ltv_test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import ltv.Customer;
import ltv.Data;
import ltv.Event;

public class DataTest {
    static Data data = null;
    static double DELTA = 1e-15;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        data = new Data();
    }
    
    @Test
    public void TestgetEventArray() {
        Event[] events = data.getEventArray("/Users/jiechu/Downloads/json.txt");
        assertEquals(events.length, 4, DELTA);
        System.out.println(events[1].toString());
        assertEquals(events[0].toString(),"Event key: 96f55c7d8f42, "
                                        + "Event time: Jan 6, 2017 12:46:00 AM, "
                                        + "last name: Smith, "
                                        + "city: Middletown, state: AK,"
                                        + " lift time value: 0.0");
        assertEquals(events[1].toString(),"Event key: ac05e815502f, "
                                        + "Event time: Jan 6, 2017 12:45:00 AM, "
                                        + "customer_id: 96f55c7d8f42, "
                                        + "tags: {\"some key\":\"some value\"}");
        assertEquals(events[2].toString(),"Event key: d8ede43b1d9f, "
                                        + "Event time: Jan 6, 2017 12:47:00 AM, "
                                        + "customer_id: 96f55c7d8f42, "
                                        + "camera_make: Canon, camera_model: EOS 80D");
        assertEquals(events[3].toString(),"Event key: 68d84e5d1a43, "
                                        + "Event time: Jan 6, 2017 12:55:00 AM, "
                                        + "customer_id: 96f55c7d8f42, total_amount: "
                                        + "12.34 USD , total_amount_num: 12.34");
    }
    

    @Test
    public void testIngest() {
        data.ingest("/Users/jiechu/Downloads/json.txt");
        data.ingest("/Users/jiechu/Downloads/json2.txt");
        assertEquals(data.getData().size(), 3);
    }

    @Test
    public void testTopXSimpleLTVCustomers() {
        data.ingest("/Users/jiechu/Downloads/json.txt");
        data.ingest("/Users/jiechu/Downloads/json2.txt");
        Customer[] customers = data.TopXSimpleLTVCustomers(3);
        System.out.println(customers[0].getLTV());
        System.out.println(customers[1].getLTV());
        System.out.println(customers[2].getLTV());
    }


}
