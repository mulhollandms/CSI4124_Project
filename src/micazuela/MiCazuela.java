package micazuela;

import micazuela.actions.Arrivals;
import micazuela.activities.*;
import micazuela.entities.*;
import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
import simulationModelling.SequelActivity;

//
// The Simulation model Class
public class MiCazuela extends AOSimulationModel
{
	// Constants available from Constants class
	/* Parameter */
        // Define the parameters
	public boolean usingAHD=false;
	
	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	// Define the reference variables to the various 
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialise Action
	public Tables [] rgTables = new Tables[2];
	public Personnel [] rgPersonnel = new Personnel[2];
	public Service [] qService = new Service[5];

	/* Input Variables */
	// Define any Independent Input Varaibles here
	public int numArrivals = 40;
	// References to RVP and DVP objects
	public RVPs rvp;  // Reference to rvp object - object created in constructor
	public DVPs dvp = new DVPs(this);  // Reference to dvp object
	public UDPs udp = new UDPs(this);

	// Output object
	public Output output = new Output(this);
	
	// Output values - define the public methods that return values
	// required for experimentation.
	public double getProfitDay(){
		return output.profitDay;
	}
	public double getCountCustomerGroupBalking(){
		return output.countCustomerGroupBalking;
	}
	public double getAvgTimeWaiting(){
		return output.avgTimeWaiting();
	}
	public double getAvgTimeSpent(){
		return output.avgTimeSpent();
	}
	// Constructor
	public MiCazuela(double t0time, double tftime, int rgTablesLargeCap, int numCooks, int numWaiters, boolean usingAHD, Seeds sd, boolean traceLogFlag)
	{
		// Initialise parameters here
		this.rgTablesLargeCap=rgTablesLargeCap;
		this.numCooks=numCooks;
		this.numWaiters=numWaiters;
		this.usingAHD=usingAHD;
		this.traceLogFlag=traceLogFlag;

		// Create RVP object with given seed
		rvp = new RVPs(this,sd);
		numArrivals = rvp.duCGArrCount();
		
		// rgCounter and qCustLine objects created in Initalise Action
		for(int i=0; i<rgTables.length; i++)
			rgTables[i]=new Tables();
		rgTables[Constants.LARGE].capacity = rgTablesLargeCap;
		rgTables[Constants.SMALL].capacity = 11-2*rgTablesLargeCap;

		for(int i=0; i<rgPersonnel.length; i++)
			rgPersonnel[i]=new Personnel();
		rgPersonnel[Constants.COOKS].numTotal=numCooks;
		rgPersonnel[Constants.WAITERS].numTotal=numWaiters;
		
		for(int i=0; i<qService.length; i++)
			qService[i]=new Service();
		
		// Initialise the simulation model
		initAOSimulModel(t0time,tftime+120);
		closingTime=tftime;
		     // Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here

		Arrivals arr = new Arrivals(this);
		scheduleAction(arr);
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * Testing preconditions
	 */
	public void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
		if(SeatTakeOrder.precondition(this,Constants.LARGE)){
			SeatTakeOrder act = new SeatTakeOrder(this, Constants.LARGE);
			act.startingEvent();
			scheduleActivity(act);
		}
		if(SeatTakeOrder.precondition(this,Constants.SMALL)){
			SeatTakeOrder act = new SeatTakeOrder(this, Constants.SMALL);
			act.startingEvent();
			scheduleActivity(act);
		}
		// Check preconditions of Conditional Activities
		if(CookFood.precondition(this)){
			CookFood act = new CookFood(this);
			act.startingEvent();
			scheduleActivity(act);
		}
		if(DeliverFood.precondition(this)){
			DeliverFood act = new DeliverFood(this);
			act.startingEvent();
			scheduleActivity(act);
		}
		if(PayCleanTable.precondition(this)){
			PayCleanTable act = new PayCleanTable(this);
			act.startingEvent();
			scheduleActivity(act);
		}
		
		// Check preconditions of Interruptions in Extended Activities
	}
	
	boolean traceLogFlag = false;
	public void eventOccured()
	{
		// System.out.printf("-------->Clock: %f<-----------------\n",getClock());
		if(traceLogFlag){
			System.out.printf("-------->Clock: %f<-----------------\n",getClock());
			System.out.printf("Current State:\nQ.Service[LARGE].n = %d, Q.Service[SMALL].n = %d\n\n",qService[Constants.LARGE].getN(),qService[Constants.SMALL].getN());
			System.out.printf("RG.Personnel[COOKS].numBusy = %d, RG.Personnel[WAITERS].numBusy = %d\n",rgPersonnel[Constants.COOKS].numBusy,rgPersonnel[Constants.WAITERS].numBusy);
			System.out.printf("RG.Tables[LARGE].n = %d, RG.Tables[SMALL].n = %d\n\n",rgTables[Constants.LARGE].getN(),rgTables[Constants.SMALL].getN());
			System.out.printf("Q.Service[IN].n = %d, Q.Service[OUT].n = %d, Q.Service[PAYMENT].n = %d\n\n",qService[Constants.IN].getN(),qService[Constants.OUT].getN(),qService[Constants.PAYMENT].getN());
			System.out.printf("RG.Tables[LARGE].capacity = %d, RG.Personnel[COOKS].numTotal = %d\n",rgTablesLargeCap,numCooks);
			System.out.printf("RG.Personnel[WAITERS].numTotal = %d, usingAHD = %b\n",numWaiters,usingAHD);
			this.showSBL();
		}
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

		// Setup an updateTrjSequences() method in the Output class
		// and call here if you have Trajectory Sets
		// updateTrjSequences() 
	}

	// Standard Procedure to start Sequel Activities with no parameters
	public void spStart(SequelActivity seqAct)
	{
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}	

	public double closingTime;
	@Override
	protected boolean implicitStopCondition(){
		if(getClock() >= closingTime){
			return (rgTables[Constants.LARGE].getN()==0 && rgTables[Constants.SMALL].getN()==0);
		}
		return false;
	}
}


