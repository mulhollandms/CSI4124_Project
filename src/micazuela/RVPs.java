package micazuela;

import cern.jet.random.Exponential;
import cern.jet.random.Normal;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;

public class RVPs 
{
	MiCazuela model; // for accessing the clock
	int n;
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds

	// Constructor
	protected RVPs(MiCazuela model, Seeds sd) 
	{ 
		this.model = model; 
		n = new Uniform(30,50,sd.custArrCount).nextInt();
		// Set up distribution functions
		cgArr = new Exponential(n*0.1/60.0, new MersenneTwister(sd.custArr));
		//cgArrCount = new Uniform(MIN_CGCOUNT,MAX_CGCOUNT,sd.custArrCount);
		customerGroupSize = new Uniform(MIN_GROUPSIZE, MAX_GROUPSIZE, sd.cgSize);
		customerBill = new Uniform(MIN_CUSTOMER_BILL,MAX_CUSTOMER_BILL,sd.custBill);
		seatTakeOrder = new Normal(MEAN_SEAT+MEAN_TAKEORDER+MEAN_DELIVERORDER,
										VAR_SEAT+VAR_TAKEORDER+VAR_DELIVERORDER,
										new MersenneTwister(sd.seatTakeOrder));
		orderPrep = new Normal(MEAN_ORDERPREP,VAR_ORDERPREP, new MersenneTwister(sd.orderPrep));
		serveTime = new Normal(MEAN_SERVETIME,VAR_SERVETIME, new MersenneTwister(sd.foodDelivery));
		eatTime = new Normal(MEAN_EATTIME,VAR_EATTIME, new MersenneTwister(sd.eatTm));
		exitProcessTime = new Normal(MEAN_EXITTIME,VAR_EXITTIME, new MersenneTwister(sd.payLeave));

	}

	static final double ARRIVAL_PERIOD_1=0.1;
	static final double ARRIVAL_PERIOD_2=0.2;
	static final double ARRIVAL_PERIOD_3=0.55;
	static final double ARRIVAL_PERIOD_4=0.1;
	static final double ARRIVAL_PERIOD_5=0.05;
	Exponential cgArr;
	public double duCGarr(){
		double t = model.getClock();
		double MEAN_INTER_ARR_CG;
		if(t<61){
			MEAN_INTER_ARR_CG = 60/ARRIVAL_PERIOD_1/n;
		} else if (t<121){
			MEAN_INTER_ARR_CG = 60/ARRIVAL_PERIOD_2/n;
		} else if (t<241){
			MEAN_INTER_ARR_CG = 120/ARRIVAL_PERIOD_3/n;
		} else if (t<301){
			MEAN_INTER_ARR_CG = 60/ARRIVAL_PERIOD_4/n;
		} else {
			MEAN_INTER_ARR_CG = 60/ARRIVAL_PERIOD_5/n;
		}
		double arriveAt = cgArr.nextDouble(1/MEAN_INTER_ARR_CG)+model.getClock();
		if(arriveAt > model.closingTime)
			return -1.0;
		return arriveAt;
	}

	static final double MIN_GROUPSIZE=1;
	static final double MAX_GROUPSIZE=4;
	Uniform customerGroupSize;
	public int uCustomerGroupSize(){
		return customerGroupSize.nextInt();
	}

	static final double MIN_CUSTOMER_BILL=10.0;
	static final double MAX_CUSTOMER_BILL=16.0;
	Uniform customerBill;
	public double duCustomerBill(int icCustomerGroupSize){
		return (customerBill.nextDouble()-1)*icCustomerGroupSize;
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

	static final double MEAN_ORDERPREP=7.0,VAR_ORDERPREP=1.5;
	Normal orderPrep;
	public double duOrderPrep(){
		return orderPrep.nextDouble();
	}

	static final double MEAN_SERVETIME=2.0,VAR_SERVETIME=0.5;
	Normal serveTime;
	public double duServeTime(){
		return serveTime.nextDouble();
	}

	static final double MEAN_EATTIME=10.0,VAR_EATTIME=2.0;
	Normal eatTime;
	public double duEatTime(){
		return eatTime.nextDouble();
	}

	static final double MEAN_EXITTIME=3.0,VAR_EXITTIME=0.8;
	Normal exitProcessTime;
	public double duExitProcessTime(){
		return exitProcessTime.nextDouble();
	}
}
