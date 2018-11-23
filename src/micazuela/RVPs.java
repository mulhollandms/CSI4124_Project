package micazuela;

import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;

public class RVPs 
{
	MiCazuela model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds


	// Constructor
	protected RVPs(MiCazuela model, Seeds sd) 
	{ 
		this.model = model; 
		// Set up distribution functions

		seatTakeOrder = new Normal(MEAN_SEAT+MEAN_TAKEORDER+MEAN_DELIVERORDER,
										VAR_SEAT+VAR_TAKEORDER+VAR_DELIVERORDER,
										new MersenneTwister());
	}

	public double duCGarr(){
		return 0.0;
	}
	public int uCustomerGroupSize(){
		return 0;
	}

	static final double MEAN_SEAT=2.0, MEAN_TAKEORDER=3.0, MEAN_DELIVERORDER=2.0;
	static final double VAR_SEAT=0.5, VAR_TAKEORDER=0.7, VAR_DELIVERORDER=0.5;
	static final double MEAN_TAKEORDER_DELIVER_AHD=1.5, VAR_TAKEORDER_DELIVER_AHD=1.2;
	Normal seatTakeOrder;
	public double duSeatTakeOrder(){
		if(model.usingAHD)
			return seatTakeOrder.nextDouble(MEAN_SEAT+MEAN_TAKEORDER_DELIVER_AHD, VAR_SEAT+VAR_TAKEORDER_DELIVER_AHD);
		else
			return seatTakeOrder.nextDouble();
	}

	public double duOrderPrep(){
		return 0.0;
	}
	public double duServeTime(){
		return 0.0;
	}
	public double duEatTime(){
		return 0.0;
	}
	public double duExitProcessTime(){
		return 0.0;
	}
}
