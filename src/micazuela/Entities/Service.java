package micazuela.entities;

import java.util.ArrayList;

public class Service extends ArrayList<CustomerGroup>{
    public static final int SMALL=0, LARGE=1,
        IN=2, OUT=3, PAYMENT=4;
    public int getN(){return this.size();}

    public boolean spInsertQueue(CustomerGroup icCustomerGroup){
        return this.add(icCustomerGroup);
    }
    public CustomerGroup spRemoveQueue(){
        return this.remove(0);
    }
}