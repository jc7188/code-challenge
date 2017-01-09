package ltv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Description : This class is a collection class containing all events
 *               1. All events will be mapped by customer_id - customer
 *               2. site_visit, image, order will be stored in the customer object created them
 *               3. Data can be uploaded by .json file
 *               4. One can get top k customers sorted by life time values
 */
public class Data {
    private HashMap<String, Customer> data = new HashMap<>();
       
    /**
     * Helper method to read from file and generate event array
     * @param file : file name
     * @return array of events
     */
    public Event[] getEventArray(String file) {
        JSONTokener jsonTokener = null;
        try {
            jsonTokener = new JSONTokener(new FileReader(new File(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  
        JSONArray jsonArray = new JSONArray(jsonTokener);  
        Event[] re = new Event[jsonArray.length()];
        if(jsonArray == null || jsonArray.length() == 0) {
            System.out.println("fail to get data from file");
            return re;
        }
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String type = jsonObject.getString("type");
            String key = jsonObject.getString("key");
            String s_event_time = jsonObject.getString("event_time").substring(0,16);  
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd:hh:mm");
            Date event_time = null;
            try {
                event_time = df.parse(s_event_time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(key == null || key.length() == 0 || type == null || type.length() == 0 || event_time == null) {
                System.out.println("Missing key information in line: "+i);
                System.out.println(jsonArray.getJSONObject(i).toString());
                continue;
            }
            if(type.equalsIgnoreCase("CUSTOMER")) { 
                String last_name = jsonObject.getString("last_name");  
                String adr_city = jsonObject.getString("adr_city");
                String adr_state = jsonObject.getString("adr_state");
                re[i] = new Customer(key, event_time, last_name, adr_city, adr_state);
            } else if (type.equalsIgnoreCase("SITE_VISIT")) {
                String customer_id = jsonObject.getString("customer_id");
                JSONObject tags = jsonObject.getJSONObject("tags");
                re[i] = new SiteVisit(key, event_time, customer_id, tags.toString());
            } else if (type.equalsIgnoreCase("IMAGE")) {
                String customer_id = jsonObject.getString("customer_id");
                String camera_make = jsonObject.getString("camera_make");
                String camera_model = jsonObject.getString("camera_model");
                re[i] = new Image(key, event_time, customer_id, camera_make, camera_model);
            } else if(type.equalsIgnoreCase("ORDER")) {
                String customer_id = jsonObject.getString("customer_id");
                String total_amount = jsonObject.getString("total_amount").trim();
                re[i] = new Order(key, event_time, customer_id, total_amount);
            } else {
                System.out.println("fail to parse one json object at line "+ i);
                System.out.println(jsonObject.toString());
            }
        }
        return re;
    }
    
    /**
     * Load file into data object
     * @param file : file name
     */
    public void ingest(String file) {
        Event[] events = getEventArray(file);
        if(events == null || events.length == 0) {
            System.out.println("Failed to read events from file");
        }
        for(int i = 0; i < events.length; i++) {
            Event event = events[i];
            if(events[i].getClass().getName().endsWith("Customer")) {
                data.put(event.getKey(), (Customer)event);                
            } else if(events[i].getClass().getName().endsWith("SiteVisit")) {
                SiteVisit visit = (SiteVisit)event;
                Customer customer = data.get(visit.getCustomer_id());
                customer.addSite(visit);
            } else if(events[i].getClass().getName().endsWith("Image")) {
                Image image = (Image)events[i];
                Customer customer = data.get(image.getCustomer_id());
                customer.addImage(image);
            } else if(events[i].getClass().getName().endsWith("Order")) {
                Order order = (Order)events[i];
                Customer customer = data.get(order.getCustomer_id());
                customer.addOrder(order);
            } else {
                System.out.println("fail to load object at line " + i);
                System.out.println(events[i].toString());
            }
        }
        System.out.println("1. Number of events loaded: "+ events.length);
    }
    
    /**
     * get details of the data
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Customer c : data.values()) {
            sb.append(c.getHistory());
        }
        return sb.toString();
    }
    
    /**
     * Get customers with most life time values
     * @param x: number of customers with most life time values
     * @return array of customers with most life time values
     */
    public Customer[] TopXSimpleLTVCustomers(int x) {
        if(x <= 0) {
            System.out.println("Please input large than one customer to search!");
            return null;
        }
        TreeSet<Customer> re = new TreeSet<>();
        for(Customer c : data.values()) {
            double ltv = c.calculateLTV();
            if(re.size() < x) {
                re.add(c);
            } else if(ltv > re.first().getLTV()) {
                re.pollLast();
                re.add(c);
            }
        }
        return re.toArray(new Customer[re.size()]);
    }       
    
    public HashMap<String, Customer> getData() {
        return data;
    }
}