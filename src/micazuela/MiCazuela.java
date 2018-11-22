package micazuela;

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
	public int rgTablesLargeCap=4;
	public int numCooks=2,numWaiters=2;
	public boolean usingAHD=false;
	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	// Define the reference variables to the various 
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialise Action
	public Tables [] rgTables = new Tables[2];
	public Personnel [] rgPersonnel = new Personnel[2];
	public Service [] qService = new Service[2];

	/* Input Variables */
	// Define any Independent Input Varaibles here
	
	// References to RVP and DVP objects
	public RVPs rvp;  // Reference to rvp object - object created in constructor
	public DVPs dvp = new DVPs(this);  // Reference to dvp object
	public UDPs udp = new UDPs(this);

	// Output object
	public Output output = new Output(this);
	
	// Output values - define the public methods that return values
	// required for experimentation.


	// Constructor
	public MiCazuela(double t0time, double tftime, int rgTablesLargeCap, int numCooks, int numWaiters, boolean usingAHD,/*define other args,*/ Seeds sd)
	{
		// Initialise parameters here
		this.rgTablesLargeCap=rgTablesLargeCap;
		this.numCooks=numCooks;
		this.numWaiters=numWaiters;
		this.usingAHD=usingAHD;

		// Create RVP object with given seed
		rvp = new RVPs(this,sd);
		
		// rgCounter and qCustLine objects created in Initalise Action
		for(int i=0; i<rgTables.length; i++)
			rgTables[i]=new Tables();
		rgTables[Constants.LARGE].capacity = rgTablesLargeCap;
		rgTables[Constants.SMALL].capacity = 11-2*rgTablesLargeCap;

		for(int i=0; i<rgPersonnel.length; i++)
			rgPersonnel[i]=new Personnel();
		
		for(int i=0; i<qService.length; i++)
			qService[i]=new Service();
		
		// Initialise the simulation model
		initAOSimulModel(t0time,tftime);   

		     // Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * Testing preconditions
	 */
	public void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
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
		// Check preconditions of Interruptions in Extended Activities
	}
	
	public void eventOccured()
	{
		//this.showSBL();
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

}


