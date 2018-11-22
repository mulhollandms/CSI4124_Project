package micazuela.activities;

import micazuela.*;
import micazuela.entities.CustomerGroup;
import simulationModelling.ConditionalActivity;

public class CookFood extends ConditionalActivity {

    CustomerGroup icCustomerGroup;
    MiCazuela model;
    public static boolean precondition(MiCazuela simModel){
        return simModel.rgPersonnel[Constants.COOKS].numBusy < simModel.rgPersonnel[Constants.COOKS].numTotal
            && simModel.qService[Constants.IN].getN() > 0;
    }
    public CookFood(MiCazuela model){this.model = model;}
    @Override
    public double duration() {
        return model.rvp.duOrderPrep();
    }

    @Override
    public void startingEvent() {
        icCustomerGroup = model.qService[Constants.IN].spRemoveQueue();
        model.rgPersonnel[Constants.COOKS].numBusy++;
    }

    @Override
    protected void terminatingEvent() {
        model.qService[Constants.OUT].spInsertQueue(icCustomerGroup);
        model.rgPersonnel[Constants.COOKS].numBusy--;
    }

}