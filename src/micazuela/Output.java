package micazuela;

import simulationModelling.OutputSequence;

public class Output 
{
	MiCazuela model;
	
	protected Output(MiCazuela md) { model = md; }
    // Use OutputSequence class to define Trajectory and Sample Sequences
    // Trajectory Sequences

    // Sample Sequences
    public OutputSequence phiTimeWaiting;
    public OutputSequence timeSpent;
    
    public double avgTimeWaiting(){
        phiTimeWaiting.computePhiDSOVs();
        return phiTimeWaiting.getMean();
    }
    public double avgTimeSpent(){
        timeSpent.computePhiDSOVs();
        return timeSpent.getMean();
    }
    // DSOVs available in the OutputSequence objects
    // If seperate methods required to process Trajectory or Sample
    // Sequences - add them here

    // SSOVs
    public double profitDay;
    public int countCustomerGroupBalking;
    
}
