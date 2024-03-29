package micazuela.actions;
import micazuela.MiCazuela;
import micazuela.entities.CustomerGroup;
import simulationModelling.ScheduledAction;

public class Arrivals extends ScheduledAction{

    MiCazuela model;
    public Arrivals(MiCazuela model){this.model = model;}
    @Override
    protected double timeSequence() {
        return model.rvp.duCGarr();
    }

    @Override
    protected void actionEvent() {
        CustomerGroup icCustomerGroup = new CustomerGroup();
        icCustomerGroup.size = model.rvp.uCustomerGroupSize();
        icCustomerGroup.arrivalTime = model.getClock();
        if(model.qService[model.udp.tableSize(icCustomerGroup)].getN() < 2){
            model.qService[model.udp.tableSize(icCustomerGroup)].spInsertQueue(icCustomerGroup);
        } else {
            model.output.countCustomerGroupBalking++;
        }
        
    }
}