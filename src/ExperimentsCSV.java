// File: Experiment.java
// Description:

import micazuela.*;
import outputAnalysis.ConfidenceInterval;
import cern.jet.random.engine.*;
import java.util.Arrays;

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
	   }
	   int[] runCounts = {32,64,128,256,512,1024,2048,4096};
       int n;
        for(int i=0; i<runCounts.length; i++){
			n = runCounts[i];
			ConfidenceInterval profitConf = new ConfidenceInterval(Arrays.copyOfRange(profitDay,0,n-1), 0.9973);
			ConfidenceInterval balkConf = new ConfidenceInterval(Arrays.copyOfRange(balkCount,0,n-1), 0.9973);
			ConfidenceInterval waitConf = new ConfidenceInterval(Arrays.copyOfRange(waitTime,0,n-1), 0.9973);
			ConfidenceInterval spentConf = new ConfidenceInterval(Arrays.copyOfRange(timeSpent,0,n-1), 0.9973);
			System.out.printf("%d,%d,%d,%d,%b,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f\n",
                                n,
                                numLargeTables,
                                personnelConfig[0],
                                personnelConfig[1],
                                usingAHD,
								profitConf.getPointEstimate(),
								profitConf.getStdDev(),
								profitConf.getCfMin(),
								profitConf.getCfMax(),
								profitConf.getZeta(),
								balkConf.getPointEstimate(),
								balkConf.getStdDev(),
								balkConf.getCfMin(),
								balkConf.getCfMax(),
								balkConf.getZeta(),
								waitConf.getPointEstimate(),
								waitConf.getStdDev(),
								waitConf.getCfMin(),
								waitConf.getCfMax(),
								waitConf.getZeta(),
								spentConf.getPointEstimate(),
								spentConf.getStdDev(),
								spentConf.getCfMin(),
								spentConf.getCfMax(),
								spentConf.getZeta());
        }
    }
   
   public static void main(String[] args)
   {
	   final double startTime = 0.0, endTime = 360.0;
	   final int numRuns = 4096;
	   String str_runs = Integer.toString(numRuns);
	   final int[] tableArrangements = { 1, 2, 3, 4, 5 };
	   
	   final int[][] personnelArrangements = {
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
	   
	System.out.println("n,RG.Tables[LARGE].capacity,RG.Personnel[COOKS].numTotal,RG.Personnel[WAITERS].numTotal,usingAHD,profit mean,profit sd,profit CI min,profit CI max,profit CI zeta,"
				+"balk avg,balk sd,balk CI min,balk CI max,balk zeta,"
				+"wait avg,wait sd,wait CI min,wait CI max,wait zeta,"
				+"spent avg,spent sd,spent CI min,spent CI max,spent zeta");
	   for(int numLargeTable: tableArrangements){
		   for(int[] currentPersonnelArrangement: personnelArrangements){
			   for(boolean currentAhdCase: ahdArrangements){
				   runcase(startTime, endTime, numRuns, numLargeTable, currentPersonnelArrangement, currentAhdCase, logging);
			   }
		   }
	   }
       
   }
}
