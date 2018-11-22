package micazuela.entities;

import java.util.ArrayList;

public class Tables{
    public int capacity;
    public ArrayList<CustomerGroup> list;

    public int getN(){return list.size();}
    public void insertGrp(CustomerGroup icCustomerGroup) {list.add(icCustomerGroup);}
    public boolean removeGrp(CustomerGroup icCustomerGroup){return list.remove(icCustomerGroup);}
}

