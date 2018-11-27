package micazuela.activities;

import micazuela.Constants;
import micazuela.MiCazuela;
import micazuela.entities.CustomerGroup;
import simulationModelling.ConditionalActivity;

public class PayCleanTable extends ConditionalActivity{

    CustomerGroup icCustomerGroup;
    MiCazuela model;

    public static boolean precondition(MiCazuela simModel){
        return simModel.qService[Constants.PAYMENT].getN() > 0
            && simModel.rgPersonnel[Constants.WAITERS].numTotal > simModel.rgPersonnel[Constants.WAITERS].numBusy;
    }

    public PayCleanTable(MiCazuela model){this.model = model;}
    @Override
    protected double duration() {
        return model.rvp.duExitProcessTime();
    }

    @Override
    public void startingEvent() {
        icCustomerGroup = model.qService[Constants.PAYMENT].spRemoveQueue();
        model.rgPersonnel[Constants.WAITERS].numBusy++;
    }

    @Override
    protected void terminatingEvent() {
        model.rgTables[model.udp.tableSize(icCustomerGroup)].removeGrp(icCustomerGroup);
        model.rgPersonnel[Constants.WAITERS].numBusy--;

        double t = model.getClock();
        model.output.timeSpent.put(t,t-icCustomerGroup.arrivalTime);
        model.output.profitDay += (model.rvp.uCustomerBill(icCustomerGroup.size));
    }

}
