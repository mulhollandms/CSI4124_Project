package micazuela;

import simulationModelling.OutputSequence;

public class Output 
{
	MiCazuela model;
	
	protected Output(MiCazuela md) { model = md; }
    // Use OutputSequence class to define Trajectory and Sample Sequences
    // Trajectory Sequences

    // Sample Sequences
    public OutputSequence timeWaiting;
    public OutputSequence timeSpent;
    
    public double avgTimeWaiting(){
        timeWaiting.computePhiDSOVs();
        return timeWaiting.getMean();
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
