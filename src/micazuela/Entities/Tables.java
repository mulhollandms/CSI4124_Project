package micazuela.entities;

import java.util.ArrayList;

public class Tables extends ArrayList<CustomerGroup>{
    public int capacity;

    public int getN(){return this.size();}
    public void insertGrp(CustomerGroup icCustomerGroup) {this.add(icCustomerGroup);}
    public boolean removeGrp(CustomerGroup icCustomerGroup){return this.remove(icCustomerGroup);}
}

