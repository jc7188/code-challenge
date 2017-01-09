package ltv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Description : This class holds below information at customer level-
 *               1. customer information : last name, address city, address state
 *               2. customer activity information: 
 *                  2.1 all orders this customer placed
 *                  2.2 all sites this customer visited
 *                  2.3 all images this customer uploaded
 *                  2.4 life time value of this customer
 *                  2.5 Initial visit date and time 
 *                  2.6 most recent visit date and time
 */
public class Customer extends Event implements Comparable<Customer>{
    private String last_name;
    private String adr_city;
    private String adr_state;
    private HashMap<String, Order> orders = new HashMap<>();
    private ArrayList<SiteVisit> siteVisits = new ArrayList<>();
    private ArrayList<Image> images = new ArrayList<>();
    private double LTV;
    private Date firstVisit = null;
    private Date lastVisit = null;
    
    /*
     * Customer constructor
     */
    public Customer(String key, Date event_time, String last_name, String adr_city, String adr_state) {
        super(key, event_time);
        this.setLast_name(last_name);
        this.setAdr_city(adr_city);
        this.setAdr_state(adr_state);
    }
    
    /**
     * Description: add new site visit information
     *              compare event time and update if it's most recent visit, or if it's the initial visit
     * @param visit
     */
    public void addSite(SiteVisit visit) {
        if(visit != null) {
            if(firstVisit == null || visit.getEvent_time().compareTo(firstVisit) < 0) {
                firstVisit = visit.getEvent_time();
            }
            if(lastVisit == null || visit.getEvent_time().compareTo(lastVisit) > 0) {
                lastVisit = visit.getEvent_time();
            }
            siteVisits.add(visit);
        } else {
            System.out.println("SiteVisit is null");
            return;
        }
    }
    
    /**
     * Description: add image information
     *              
     * @param image
     */
    public void addImage(Image image) {
        if(image != null) {
            images.add(image);
        } else {
            System.out.println("Image is null");
            return;
        }
    }
    
    /**
     * Description: add order information
     *              
     * @param order
     */
    public void addOrder(Order order) {
        if(order != null) {            
           orders.put(order.getKey(), order);
        } else {
            System.out.println("Order is null");
        }
    }
    
    public double getTotalAmount() {
        if(orders == null || orders.size() == 0) return 0;
        double total = 0;
        for(Order order : orders.values()) {
            total += order.getTotal_amount_num();
        }
        return total;
    }
    
    public long getWeekNum() {
        if(lastVisit == null || firstVisit == null) return 0;
        long weeks = (lastVisit.getTime()-firstVisit.getTime())/(7 * 24 * 60 * 60 * 1000);
        if(weeks < 1) return 1;
        return weeks;
    }
    
    public double calculateLTV() {
        long weeks = getWeekNum();
        double total = getTotalAmount();
        this.LTV = total/weeks;
        return total;
    }
    
    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }
    
    public Date getFirstVisit() {
        return firstVisit;
    }
    
    public Date getLastVisit() {
        return lastVisit;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the adr_city
     */
    public String getAdr_city() {
        return adr_city;
    }

    /**
     * @param adr_city the adr_city to set
     */
    public void setAdr_city(String adr_city) {
        this.adr_city = adr_city;
    }

    /**
     * @return the adr_state
     */
    public String getAdr_state() {
        return adr_state;
    }

    /**
     * @param adr_state the adr_state to set
     */
    public void setAdr_state(String adr_state) {
        this.adr_state = adr_state;
    }

    /**
     * @return the lTV
     */
    public double getLTV() {
        return LTV;
    }
    
    /**
     * @return the lTV
     */
    public String getOrders() {
        StringBuilder sb = new StringBuilder();
        for(Order order : orders.values()) {
            sb.append(order.toString()).append('\n');
        }
        return sb.toString();
    }

    /**
     * @param lTV the lTV to set
     */
    public void setLTV(double lTV) {
        LTV = lTV;
    } 
    
    public String toString() {
        return super.toString() + ", last name: " + this.last_name + ", city: " + this.adr_city + ", state: " + this.adr_state + ", lift time value: " + this.LTV;        
    }
    
    public String getHistory () {
        StringBuilder sb = new StringBuilder();
        sb.append("\nOrder history: ");
        for(Order order : orders.values()) {
            sb.append(order.toString());
        }
        sb.append("\nVisted sites: ");
        for(SiteVisit site : siteVisits) {
            sb.append(site.toString());
        }
        sb.append("\nImage uploaded: ");
        for(Image image : images) {
            sb.append(image.toString());
        }
        return "Customer - "+ super.toString() + ", last name: " + this.last_name + ", city: " + this.adr_city + ", state: " + this.adr_state + "history: "+ sb.toString();        
    }

    @Override
    public int compareTo(Customer other) {
        return Double.compare(other.LTV, this.LTV);
    }


}
