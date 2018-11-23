// File: Experiment.java
// Description:

import micazuela.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class Experiment
{
   public static void main(String[] args)
   {
       int i, NUMRUNS = 1; 
       double startTime=0.0, endTime=360.0;
       Seeds[] sds = new Seeds[NUMRUNS];
       MiCazuela mname;  // Simulation object

       // Lets get a set of uncorrelated seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       for(i=0 ; i<NUMRUNS ; i++)
        sds[i] = new Seeds(rsg);
       
       // Loop for NUMRUN simulation runs for each case
       // Case 1
       System.out.println(" Case 1");
       for(i=0 ; i < NUMRUNS ; i++)
       {
          mname = new MiCazuela(startTime,endTime,4,2,2,false,sds[i]);
          mname.runSimulation();
          // See examples for hints on collecting output
          // and developping code for analysis
          System.out.printf("Total Daily Profit:\t%f\n",mname.output.profitDay);
          System.out.printf("Total Customers Balked:\t%d\n",mname.output.countCustomerGroupBalking);
          System.out.printf("Average Time Spent:\t%f\n",mname.output.avgTimeSpent());
          System.out.printf("Average Time Waiting:\t%f\n",mname.output.avgTimeWaiting());
       }
   }
}
