package micazuela.activities;

import micazuela.Constants;
import micazuela.MiCazuela;
import micazuela.entities.CustomerGroup;
import simulationModelling.ConditionalActivity;

public class DeliverFood extends ConditionalActivity{
    CustomerGroup icCustomerGroup;
    MiCazuela model;
    public static boolean precondition(MiCazuela simModel){
        return simModel.qService[Constants.OUT].getN() > 0
            && simModel.rgPersonnel[Constants.WAITERS].numTotal > simModel.rgPersonnel[Constants.WAITERS].numBusy;
    }
    public DeliverFood(MiCazuela model){this.model = model;}
    @Override
    protected double duration() {
        return model.rvp.duServeTime();
    }

    @Override
    public void startingEvent() {
        icCustomerGroup = model.qService[Constants.OUT].spRemoveQueue();
        model.rgPersonnel[Constants.WAITERS].numBusy++;
    }

    @Override
    protected void terminatingEvent() {
        model.rgPersonnel[Constants.WAITERS].numBusy--;
        model.spStart(new Eat(model, icCustomerGroup));
    }

}