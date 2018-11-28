package micazuela.activities;

import micazuela.*;
import micazuela.entities.*;
import simulationModelling.ConditionalActivity;

public class CookFood extends ConditionalActivity {

    CustomerGroup icCustomerGroup;
    MiCazuela model;
    public static boolean precondition(MiCazuela simModel){
        return simModel.rgPersonnel[Personnel.COOKS].numBusy < simModel.rgPersonnel[Personnel.COOKS].numTotal
            && simModel.qService[Service.IN].getN() > 0;
    }
    public CookFood(MiCazuela model){this.model = model;}
    @Override
    public double duration() {
        return model.rvp.duOrderPrep();
    }

    @Override
    public void startingEvent() {
        icCustomerGroup = model.qService[Service.IN].spRemoveQueue();
        model.rgPersonnel[Personnel.COOKS].numBusy++;
    }

    @Override
    protected void terminatingEvent() {
        model.qService[Service.OUT].spInsertQueue(icCustomerGroup);
        model.rgPersonnel[Personnel.COOKS].numBusy--;
    }

}