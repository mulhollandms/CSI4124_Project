package micazuela.entities;

import java.util.ArrayList;

public class Service extends ArrayList<CustomerGroup>{
    public int getN(){return this.size();}

    public boolean spInsertQueue(CustomerGroup icCustomerGroup){
        return this.add(icCustomerGroup);
    }
    public CustomerGroup spRemoveQueue(){
        return this.remove(0);
    }
}