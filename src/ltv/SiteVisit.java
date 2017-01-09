package ltv;

import java.util.Date;

/**
 * Description : SiteVisit class holds site visit information
 *               1. this class is sorted by event time
 *               
 */

public class SiteVisit extends Event implements Comparable<SiteVisit>{
    private String customer_id;
    private String tags;
    
    public SiteVisit(String key, Date event_time, String customer_id, String tags) {
        super(key, event_time);
        this.customer_id = customer_id;
        this.tags = tags;
    }
    
    /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return the customer_id
     */
    public String getCustomer_id() {
        return customer_id;
    }

    /**
     * @param customer_id the customer_id to set
     */
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
    
    public String toString() {
        return super.toString() + ", customer_id: " + this.customer_id + ", tags: " + this.tags;               
    }
    
    /**
     * SiteVisit is sorted by event_time
     */
    @Override
    public int compareTo(SiteVisit other) {
        return this.event_time.compareTo(other.getEvent_time());        
    }
}