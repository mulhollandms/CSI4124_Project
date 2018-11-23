package micazuela.activities;

import micazuela.Constants;
import micazuela.MiCazuela;
import micazuela.entities.CustomerGroup;
import simulationModelling.ConditionalActivity;

public class SeatTakeOrder extends ConditionalActivity{
    CustomerGroup icCustomer;
    MiCazuela model;
    int tableSize;
    public static boolean precondition(MiCazuela simModel, int tableSize){
        return simModel.rgPersonnel[Constants.WAITERS].numBusy < simModel.rgPersonnel[Constants.WAITERS].numTotal
            && simModel.qService[Constants.LARGE].getN() > 0 && simModel.udp.canSeatGroup(tableSize)!=Constants.NONE;
    }

    public SeatTakeOrder(MiCazuela model,int tableSize){
        this.model = model;
        this.tableSize = tableSize;
    }
    @Override
    protected double duration() {
        return model.rvp.duSeatTakeOrder();
    }

    @Override
    public void startingEvent() {
        
    }

    @Override
    protected void terminatingEvent() {

    }

}