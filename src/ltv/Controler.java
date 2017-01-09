/**
 * Created by Jie Chu
 * Creation date: Jan.8th 2017
 * Function : main function to 1. ingest json file 2. get top customers bt LTV
 */

package ltv;

public class Controler {
    
    public static void ingest(String file, Data d) {
        d.ingest(file);
    }
    
    public static Customer[] topXSimpleLTVCustomer(int x, Data d) {
        return d.TopXSimpleLTVCustomers(x);
    }
    
    /*
     * * args[1] : file name, args[2] number of customers
     */
    public static void main(String[] args) {
        Data data = new Data();      
        String file = args[0];
        System.out.println("File: " + args[0]);
        System.out.println("Number of customers to search: " + args[1]);
        ingest(file, data);
        Customer[] topKcustomers = topXSimpleLTVCustomer(Integer.parseInt(args[1]), data);
        for(int i = 0; i < topKcustomers.length; i++) {
            System.out.println(topKcustomers[i]);
        }        
    }
}
