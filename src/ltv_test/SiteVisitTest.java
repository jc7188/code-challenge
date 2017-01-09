package ltv_test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import ltv.SiteVisit;

public class SiteVisitTest {
    static SiteVisit sv1 = null, sv2 = null;
    static double DELTA = 1e-15;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd:hh:mm");
        Date event_time1 = null,event_time2 = null;
        try {
            event_time1 = df.parse("2017-01-07:12:46:52");
            event_time2 = df.parse("2017-01-06:12:45:57");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sv1 = new SiteVisit("ac05e815502f", event_time1, "96f55c7d8f42", "tags");
        sv2 = new SiteVisit("ac05e815502e", event_time2, "96f55c7d8f43", "tags");
    }
    
    @Test
    public void testCompare() {
        assertEquals(sv1.compareTo(sv2), 1, DELTA);
    }
    
    @Test
    public void testToString() {
        assertEquals(sv1.toString(), 
                "Event key: ac05e815502f, "
                + "Event time: Jan 7, 2017 12:46:00 AM, "
                + "customer_id: 96f55c7d8f42, "
                + "tags: tags");
        assertEquals(sv2.toString(), 
                "Event key: ac05e815502e, "
                + "Event time: Jan 6, 2017 12:45:00 AM, "
                + "customer_id: 96f55c7d8f43, "
                + "tags: tags");
    }
}
