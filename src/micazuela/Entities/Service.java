package micazuela.entities;

import java.util.ArrayList;

public class Service{
    private ArrayList<CustomerGroup> list = new ArrayList<CustomerGroup>();
    public int length = -1;
    public int getN(){return list.size();}

    public void enqueue(CustomerGroup icCustomerGroup){
        list.add(icCustomerGroup);
    }
    public CustomerGroup dequeue(){
        return list.remove(0);
    }
}