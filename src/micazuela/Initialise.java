package micazuela;

import micazuela.entities.*;

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
		model.rgTables[Constants.SMALL].clear();
		model.rgTables[Constants.LARGE].clear();

		model.rgPersonnel[Constants.COOKS].numBusy = 0;
		model.rgPersonnel[Constants.WAITERS].numBusy = 0;

		for(Service qS : model.qService)
			qS.clear();
	}
	

}
