package micazuela.activities;

import micazuela.MiCazuela;
import micazuela.entities.*;
import simulationModelling.ConditionalActivity;

public class SeatTakeOrder extends ConditionalActivity{
    CustomerGroup icCustomerGroup;
    MiCazuela model;
       
    public static boolean precondition(MiCazuela simModel){
        return simModel.udp.canSeatGroup()!=Tables.NONE;
    }

    public SeatTakeOrder(MiCazuela model){ this.model = model;}
    @Override
    protected double duration() {
        return model.rvp.duSeatTakeOrder();
    }

    @Override
    public void startingEvent() {
        int sizeId = model.udp.canSeatGroup();
        icCustomerGroup = model.qService[sizeId].spRemoveQueue();
        model.rgTables[sizeId].insertGrp(icCustomerGroup);
        model.rgPersonnel[Personnel.WAITERS].numBusy++;

        double t = model.getClock();
        model.output.phiTimeWaiting.put(t,t-icCustomerGroup.arrivalTime);
    }

    @Override
    protected void terminatingEvent() {
        model.qService[Service.IN].spInsertQueue(icCustomerGroup);
        model.rgPersonnel[Personnel.WAITERS].numBusy--;
    }

}