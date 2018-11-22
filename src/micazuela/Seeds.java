package micazuela;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds 
{
	int custArr;   // customer arrivals
	int seatTakeOrder;   // seat take order
	int orderPrep;   // order prepare
	int foodDelivery;   // deliver food
	int eatTm;   // eat time
	int payLeave;   // pay and leave

	public Seeds(RandomSeedGenerator rsg)
	{
		custArr=rsg.nextSeed();
		seatTakeOrder=rsg.nextSeed();
		orderPrep=rsg.nextSeed();
		foodDelivery=rsg.nextSeed();
		eatTm=rsg.nextSeed();
		payLeave=rsg.nextSeed();
	}
	
	public String toString() {
		return("CustArr: "+custArr+"  seatTakeOrder: "+seatTakeOrder+"  orderPrep: "+orderPrep
				+"  foodDelivery: "+foodDelivery+"  eatTm: "+eatTm+"  PayLeave: "+payLeave);
	}

	
}
