package micazuela;

import java.util.ArrayList;
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
		for(Tables t : model.rgTables){
			t.list.clear();
		}
		for(ArrayList<CustomerGroup> l : model.qService){
			l.clear();
		}

	}
	

}
