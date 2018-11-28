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
		return icCustomerGroup.size < 3 ? Tables.SMALL : Tables.LARGE;
	}

	
	public int canSeatGroup(){
		if(model.rgPersonnel[Personnel.WAITERS].numBusy < model.rgPersonnel[Personnel.WAITERS].numTotal){
			int largeLineN = model.qService[Service.LARGE].getN(),
				smallLineN = model.qService[Service.SMALL].getN();
			if(largeLineN >= smallLineN && largeLineN > 0 && model.rgTables[Tables.LARGE].getN() < model.rgTables[Tables.LARGE].capacity){
				return Tables.LARGE;
			}
			if(smallLineN > 0 && model.rgTables[Tables.SMALL].getN() < model.rgTables[Tables.SMALL].capacity){
				return Tables.SMALL;
			}
		}
		return Tables.NONE;
	}
	
}
