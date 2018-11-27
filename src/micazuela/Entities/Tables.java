package micazuela.entities;

import java.util.ArrayList;

public class Tables extends ArrayList<CustomerGroup>{
    public static final int SMALL=0;
    public static final int LARGE=1;
    public static final int NONE=-1;
    public int capacity;

    public int getN(){return this.size();}
    public void insertGrp(CustomerGroup icCustomerGroup) {this.add(icCustomerGroup);}
    public boolean removeGrp(CustomerGroup icCustomerGroup){return this.remove(icCustomerGroup);}
}

