package micazuela.activities;

import micazuela.MiCazuela;
import micazuela.entities.*;
import simulationModelling.SequelActivity;

public class Eat extends SequelActivity{

    private CustomerGroup icCustomerGroup;
    MiCazuela model;

    public Eat(MiCazuela model, CustomerGroup icCustomerGroup) {
        this.model = model;
        this.icCustomerGroup = icCustomerGroup;
    }

    @Override
    protected double duration() {
        return model.rvp.duEatTime();
    }

    @Override
    public void startingEvent() {

    }

    @Override
    protected void terminatingEvent() {
        model.qService[Service.PAYMENT].spInsertQueue(icCustomerGroup);
    }
    

}