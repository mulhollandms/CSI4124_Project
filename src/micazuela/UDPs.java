package micazuela;

import micazuela.entities.*;

public class UDPs 
{
	MiCazuela model;  // for accessing the clock
	
	// Constructor
	protected UDPs(MiCazuela model) { this.model = model; }

	// Translate User Defined Procedures into methods
    /*-------------------------------------------------
	                       Example
	    protected int ClerkReadyToCheckOut()
        {
        	int num = 0;
        	Clerk checker;
        	while(num < model.NumClerks)
        	{
        		checker = model.Clerks[num];
        		if((checker.currentstatus == Clerk.status.READYCHECKOUT)  && checker.list.size() != 0)
        		{return num;}
        		num +=1;
        	}
        	return -1;
        }
	------------------------------------------------------------*/
	public int tableSize(CustomerGroup icCustomerGroup){
		return icCustomerGroup.size < 3 ? Constants.SMALL : Constants.LARGE;
	}

	// GAComment:  the logic doe not seem correct to me.  You are using icCustomerGroupSize as 
	//   the identifier for rgTables.  This is not what is describe in the CM.
	//   YOu can use the tableSize UDP to get the ID first as in
	//    id = tableSize(icCustomerGroupSize);
	//   and then use id as the identifier
	public int canSeatGroup(){
		if(model.rgPersonnel[Personnel.WAITERS].numBusy < model.rgPersonnel[Personnel.WAITERS].numTotal){
			int largeLineN = model.qService[Constants.LARGE].getN(),
				smallLineN = model.qService[Constants.SMALL].getN();
			if(largeLineN >= smallLineN && largeLineN > 0 && model.rgTables[Constants.LARGE].getN() < model.rgTables[Constants.LARGE].capacity){
				return Constants.LARGE;
			}
			if(smallLineN > 0 && model.rgTables[Constants.SMALL].getN() < model.rgTables[Constants.SMALL].capacity){
				return Constants.SMALL;
			}
		}
		return Constants.NONE;
	}
	
}
