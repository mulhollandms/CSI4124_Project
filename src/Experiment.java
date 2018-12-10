// File: Experiment.java
// Description:

import micazuela.*;
import outputAnalysis.ConfidenceInterval;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class Experiment
{
   public static void main(String[] args)
   {
       int i, NUMRUNS = 50; 
       double startTime=0.0, endTime=360.0;
       Seeds[] sds = new Seeds[NUMRUNS];
       MiCazuela simModel;  // Simulation object

       // Lets get a set of uncorrelated seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       for(i=0 ; i<NUMRUNS ; i++)
        sds[i] = new Seeds(rsg);
       
       // Loop for NUMRUN simulation runs for each case
       // Case 1
       double[] profitDay = new double[NUMRUNS];
       double[] balkCount = new double[NUMRUNS];
        double[] waitTime = new double[NUMRUNS];
        double[] timeSpent = new double[NUMRUNS];
       System.out.println("Base Case");
       for(i=0 ; i < NUMRUNS ; i++)
       {
            simModel = new MiCazuela(startTime,endTime,4,2,2,false,sds[i],true);
            simModel.runSimulation();
            profitDay[i]=simModel.getProfitDay();
            balkCount[i]=simModel.getCountCustomerGroupBalking();
            waitTime[i]=simModel.getAvgTimeWaiting();
            timeSpent[i]=simModel.getAvgTimeSpent();
            System.out.printf("*Terminated simulation run %d*\n",i+1);
            // See examples for hints on collecting output
            // and developping code for analysis
       }
   }
}
