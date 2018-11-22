package micazuela;

import micazuela.entities.CustomerGroup;

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

	public int canSeatGroup(int icCustomerGroupSize){
		if(model.rgTables[icCustomerGroupSize].getN() < model.rgTables[icCustomerGroupSize].capacity){
			return icCustomerGroupSize;
		} else {
			return Constants.NONE;
		}
	}
	
}
