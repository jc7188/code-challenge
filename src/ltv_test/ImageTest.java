package ltv_test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import ltv.Image;

public class ImageTest {
    static Image image = null;
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd:hh:mm");
    static Date event_time = null;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        event_time = df.parse("2017-01-06:12:46");
        image = new Image("96f55c7d8f42", event_time, "96f55c7d8f41", "Canon","EOS 80D");
    }

    @Test
    public void testToString() {
        assertEquals(image.toString(), 
                "Event key: 96f55c7d8f42, "
                + "Event time: Jan 6, 2017 12:46:00 AM, "
                + "customer_id: 96f55c7d8f41, "
                + "camera_make: Canon, camera_model: EOS 80D");
    }

}
