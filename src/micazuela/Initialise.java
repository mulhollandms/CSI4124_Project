package micazuela;

import micazuela.entities.*;
import simulationModelling.OutputSequence;
import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction
{
	MiCazuela model;
	
	// Constructor
	protected Initialise(MiCazuela model) { this.model = model; }

	double [] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
	int tsix = 0;  // set index to first entry.
	protected double timeSequence() 
	{
		return ts[tsix++];  // only invoked at t=0
	}

	protected void actionEvent() 
	{
		// System Initialisation
				// Add initilisation instructions
		model.rgTables[Tables.SMALL].clear();
		model.rgTables[Tables.LARGE].clear();

		model.rgPersonnel[Personnel.COOKS].numBusy = 0;
		model.rgPersonnel[Personnel.WAITERS].numBusy = 0;

		for(Service qS : model.qService)
			qS.clear();
		
		model.output.timeSpent = new OutputSequence("phiTimeSpent");
		model.output.phiTimeWaiting = new OutputSequence("phiTimeWaiting");
		model.output.countCustomerGroupBalking = 0;
		model.output.profitDay = -(model.rgPersonnel[Personnel.COOKS].numTotal*Constants.COOK_SALARY
			+model.rgPersonnel[Personnel.WAITERS].numTotal*Constants.WAITER_SALARY+Constants.OVERHEAD_COST);
		if(model.usingAHD)
			model.output.profitDay -= model.rgPersonnel[Personnel.WAITERS].numTotal*Constants.AHD_COST;
	}
	

}
