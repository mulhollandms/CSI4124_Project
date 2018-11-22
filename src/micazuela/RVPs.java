package micazuela;

import cern.jet.random.Exponential;
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
		interArrDist = new Exponential(1.0/WMEAN1,  
									   new MersenneTwister(sd.seed1));

		seatTakeOrder = new Normal(MEAN_SEAT+MEAN_TAKEORDER+MEAN_DELIVERORDER,
										SD_SEAT+SD_TAKEORDER+SD_DELIVERORDER,
										new MersenneTwister());
	}
	
	/* Random Variate Procedure for Arrivals */
	private Exponential interArrDist;  // Exponential distribution for interarrival times
	private final double WMEAN1=10.0;
	protected double duInput()  // for getting next value of duInput
	{
	    double nxtInterArr;

        nxtInterArr = interArrDist.nextDouble();
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
	    return(nxtInterArr+model.getClock());
	}

	public double duCGarr(){
		return 0.0;
	}
	public int uCustomerGroupSize(){
		return 0;
	}

	double MEAN_SEAT=0.1, MEAN_TAKEORDER=0.1, MEAN_DELIVERORDER=0.1;
	double SD_SEAT=0.1, SD_TAKEORDER=0.1, SD_DELIVERORDER=0.1;
	double MEAN_TAKEORDER_DELIVER_AHD=0.1, SD_TAKEORDER_DELIVER_AHD=0.1;
	Normal seatTakeOrder;

	public double duSeatTakeOrder(){
		if(model.usingAHD)
			return seatTakeOrder.nextDouble(MEAN_SEAT+MEAN_TAKEORDER_DELIVER_AHD, SD_SEAT+SD_TAKEORDER_DELIVER_AHD);
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
