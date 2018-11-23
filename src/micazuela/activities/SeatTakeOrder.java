package micazuela.activities;

import micazuela.Constants;
import micazuela.MiCazuela;
import micazuela.entities.CustomerGroup;
import simulationModelling.ConditionalActivity;

public class SeatTakeOrder extends ConditionalActivity{
    CustomerGroup icCustomerGroup;
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
        icCustomerGroup = model.qService[tableSize].spRemoveQueue();
        model.rgTables[tableSize].insertGrp(icCustomerGroup);
        model.rgPersonnel[Constants.WAITERS].numBusy++;

        double t = model.getClock();
        model.output.timeWaiting.put(t,t-icCustomerGroup.arrivalTime);
    }

    @Override
    protected void terminatingEvent() {
        model.qService[Constants.IN].spInsertQueue(icCustomerGroup);
        model.rgPersonnel[Constants.WAITERS].numBusy--;
    }

}