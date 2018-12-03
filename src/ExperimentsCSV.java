// File: Experiment.java
// Description:

import micazuela.*;
import outputAnalysis.ConfidenceInterval;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class ExperimentsCSV
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
		//    System.out.println("~~Starting run: "+Integer.toString(i)+"~~");
		   simModel = new MiCazuela(
				   startTime,						//Double t0time (Start Time)
				   endTime,							//Double tftime (End Time)
				   numLargeTables,					//int rgTablesLargeCap (Number of large tables)
				   personnelConfig[0],				//int numCooks (number of cooks)
				   personnelConfig[1],				//int numWaiters (number of waiters)
				   usingAHD,						//boolean usingAHD (usage of automated hand-held devices)
				   sds[i],							//seeds sd (seed used for random variate procedures in this run)
				   false					//boolean traceLogFlag (enable trace logs?)
				   );

		   simModel.runSimulation();
		   profitDay[i]=simModel.output.profitDay;
		   balkCount[i]=simModel.output.countCustomerGroupBalking;
		   waitTime[i]=simModel.output.avgTimeWaiting();
		   timeSpent[i]=simModel.output.avgTimeSpent();
		//    System.out.println("~~Ending run: "+Integer.toString(i)+"~~");
	   }
	   ConfidenceInterval profitConf = new ConfidenceInterval(profitDay, 0.9973);
	   ConfidenceInterval balkConf = new ConfidenceInterval(balkCount, 0.9973);
	   ConfidenceInterval waitConf = new ConfidenceInterval(waitTime, 0.9973);
	   ConfidenceInterval spentConf = new ConfidenceInterval(timeSpent, 0.9973);
	   System.out.printf("%d,%d,%d,%b,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f\n",
							numLargeTables,
							personnelConfig[0],
							personnelConfig[1],
	   						usingAHD,
						   profitConf.getCfMin(),
						   profitConf.getCfMax(),
						   profitConf.getPointEstimate(),
						   balkConf.getCfMin(),
						   balkConf.getCfMax(),
						   balkConf.getPointEstimate(),
						   waitConf.getCfMin(),
						   waitConf.getCfMax(),
						   waitConf.getPointEstimate(),
						   spentConf.getCfMin(),
						   spentConf.getCfMax(),
						   spentConf.getPointEstimate());
	//    System.out.printf("profit range: CI(%.2f,%.2f)\naverage balking: %.2f\ntime spent: CI(%.2f,%.2f)\nwaiting time: CI(%.2f,%.2f)\n",profitConf.getCfMin(),profitConf.getCfMax(),balkConf.getPointEstimate(),spentConf.getCfMin(),spentConf.getCfMax(),waitConf.getCfMin(),waitConf.getCfMax());
	}
   
   public static void main(String[] args)
   {
	   final double startTime = 0.0, endTime = 360.0;
	   final int numRuns = 50;
	   String str_runs = Integer.toString(numRuns);
	   final int[] tableArrangements = { 4, 2, 1, 2, 3, 5 };
	   								   //^ BASE CASE
	   
	   final int[][] personnelArrangements = {
			 //{a,b} -- a: number of cooks; b: number of waiters
			   {2,2},//BASE CASE
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
	   								   //^ BASE CASE
	   
	   final boolean logging = true;
	   
	   //runcase(double startTime, double endTime, int numRuns, int numLargeTables, int numCooks, int numWaiters, boolean usingAHD, boolean enableTraceLogs)
	   
	   final int baseTableArrangement = tableArrangements[0];
	   final int[] basePersonnelArrangement = personnelArrangements[0];
	   final boolean baseAhdArrangement = ahdArrangements[0];
	   
	//    //BASECASE
	//    System.out.println(">Testing: Base Case:");
	//    System.out.println("~Starting "+str_runs+" tests of the base case~");
	//    runcase(startTime, endTime, numRuns, baseTableArrangement, basePersonnelArrangement, baseAhdArrangement, logging);
	//    System.out.println("~Finished "+str_runs+" tests of the base case~");
	   
	//    //Table cases
	//    System.out.println(">Testing: Table Configurations:");
	//    for(int numLargeTable: tableArrangements){
	// 	   System.out.println("~Starting "+str_runs+" tests with "+Integer.toString(numLargeTable)+" large tables~");
	// 	   runcase(startTime, endTime, numRuns, numLargeTable, basePersonnelArrangement, baseAhdArrangement, logging);
	// 	   System.out.println("~Finished "+str_runs+" tests with "+Integer.toString(numLargeTable)+" large tables~");
	//    }
	   
	//    //Personnel cases
	//    System.out.println(">Testing: Personnel Configurations:");
	//    for(int[] currentPersonnelArrangement: personnelArrangements){
	// 	   System.out.println("~Starting "+str_runs+" tests with "+Integer.toString(currentPersonnelArrangement[0])+" cooks and, "+Integer.toString(currentPersonnelArrangement[1])+" waiters~");
	// 	   runcase(startTime, endTime, numRuns, baseTableArrangement, currentPersonnelArrangement, baseAhdArrangement, logging);
	// 	   System.out.println("~Finished "+str_runs+" tests with "+Integer.toString(currentPersonnelArrangement[0])+" cooks and, "+Integer.toString(currentPersonnelArrangement[1])+" waiters~");
	//    }
	   
	//    //AHD cases
	//    System.out.println(">Testing: Automated Hand-held device configurations");
	//    for(boolean currentAhdCase: ahdArrangements){
	// 	   String enabled = (currentAhdCase) ? "enabled" : "disabled";
	// 	   System.out.println("~Starting "+str_runs+" tests with automated hand-held devices "+enabled+"~");
	// 	   runcase(startTime, endTime, numRuns, baseTableArrangement, basePersonnelArrangement, currentAhdCase, logging);
	// 	   System.out.println("~Finished "+str_runs+" tests with automated hand-held devices "+enabled+"~");
	//    }
	   
	   //all the cases
	//    System.out.println(">Testing each and every combination: ");
	   for(int numLargeTable: tableArrangements){
		   for(int[] currentPersonnelArrangement: personnelArrangements){
			   for(boolean currentAhdCase: ahdArrangements){
				   String largeTables = Integer.toString(numLargeTable);
				   String cooks = Integer.toString(currentPersonnelArrangement[0]);
				   String waiters = Integer.toString(currentPersonnelArrangement[1]);
				   String ahdenabled = (currentAhdCase) ? "enabled" : "disabled";
				   
				//    System.out.println("~Starting "+str_runs+" tests with "+largeTables+" tables, "+cooks+" cooks, "+waiters+" waiters, and automated hand-held devices "+ahdenabled+"~");
				   runcase(startTime, endTime, numRuns, numLargeTable, currentPersonnelArrangement, currentAhdCase, logging);
				//    System.out.println("~Finished "+str_runs+" tests with "+largeTables+" tables, "+cooks+" cooks, "+waiters+" waiters, and automated hand-held devices "+ahdenabled+"~");
			   }
		   }
	   }
       
   }
}
