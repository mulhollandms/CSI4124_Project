// File: Experiment.java
// Description:

import micazuela.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class ExperimentAll
{
	
	private static void runcase(double startTime, double endTime, int numRuns, int numLargeTables, int[] personnelConfig, boolean usingAHD, boolean enableTraceLogs)
	{ 
	   Seeds[] sds = new Seeds[numRuns];
	   MiCazuela simModel;
	   
	   RandomSeedGenerator rsg = new RandomSeedGenerator();
	   for(int i=0 ; i<numRuns ; i++)
		   sds[i] = new Seeds(rsg);
   
	   double[] profitDay = new double[numRuns];
	   double[] balkCount = new double[numRuns];
	   double[] waitTime = new double[numRuns];
	   double[] timeSpent = new double[numRuns];
	   
	   
	   for(int i=0 ; i < numRuns ; i++)
	   {
		   System.out.println("~~Starting run: "+Integer.toString(i)+"~~");
		   simModel = new MiCazuela(
				   startTime,						//Double t0time (Start Time)
				   endTime,							//Double tftime (End Time)
				   numLargeTables,					//int rgTablesLargeCap (Number of large tables)
				   personnelConfig[0],				//int numCooks (number of cooks)
				   personnelConfig[1],				//int numWaiters (number of waiters)
				   usingAHD,						//boolean usingAHD (usage of automated hand-held devices)
				   sds[i],							//seeds sd (seed used for random variate procedures in this run)
				   enableTraceLogs					//boolean traceLogFlag (enable trace logs?)
				   );

		   simModel.runSimulation();
		   profitDay[i]=simModel.output.profitDay;
		   balkCount[i]=simModel.output.countCustomerGroupBalking;
		   waitTime[i]=simModel.output.avgTimeWaiting();
		   timeSpent[i]=simModel.output.avgTimeSpent();
		   System.out.println("~~Ending run: "+Integer.toString(i)+"~~");
	   }
	}
   
   public static void main(String[] args)
   {
	   final double startTime = 0.0, endTime = 360.0;
	   final int numRuns = 50;
	   String str_runs = Integer.toString(numRuns);
	   final int[] tableArrangements = { 4, 2, 1, 2, 3, 5 };
	   
	   final int[][] personnelArrangements = {
			 //{a,b} -- a: number of cooks; b: number of waiters
			   {2,2},
			   {3,2},
			   {2,3},
			   {3,3},
			   {4,2},
			   {2,4},
			   {4,3},
			   {3,4},
			   {4,4}
	   };
	   
	   final boolean[] ahdArrangements = {false, true};
	   
	   final boolean logging = true;
	   
	   
	   //all the cases
	   for(int numLargeTable: tableArrangements){
		   for(int[] currentPersonnelArrangement: personnelArrangements){
			   for(boolean currentAhdCase: ahdArrangements){
				   String largeTables = Integer.toString(numLargeTable);
				   String cooks = Integer.toString(currentPersonnelArrangement[0]);
				   String waiters = Integer.toString(currentPersonnelArrangement[1]);
				   String ahdenabled = (currentAhdCase) ? "enabled" : "disabled";
				   
				   System.out.println("~Starting "+str_runs+" tests with "+largeTables+" tables, "+cooks+" cooks, "+waiters+" waiters, and automated hand-held devices "+ahdenabled+"~");
				   runcase(startTime, endTime, numRuns, numLargeTable, currentPersonnelArrangement, currentAhdCase, logging);
				   System.out.println("~Finished "+str_runs+" tests with "+largeTables+" tables, "+cooks+" cooks, "+waiters+" waiters, and automated hand-held devices "+ahdenabled+"~");
			   }
		   }
	   }
       
   }
}
