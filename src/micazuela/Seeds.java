package micazuela;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds 
{
	int custArr;   // customer arrivals
	int custArrCount;
	int cgSize;
	int custBill; //customer bill
	int seating;
	int ordering;
	int orderDelivery;
	int ahd;
	int cooking;
	int bringfood;
	int foodDelivery;   // deliver food
	int eatTm;   // eat time
	int payLeave;   // pay and leave

	public Seeds(RandomSeedGenerator rsg)
	{
		custArr=rsg.nextSeed();
		custArrCount=rsg.nextSeed();
		cgSize=rsg.nextSeed();
		custBill=rsg.nextSeed();
		seating=rsg.nextSeed();
		ordering=rsg.nextSeed();
		orderDelivery=rsg.nextSeed();
		ahd=rsg.nextSeed();
		cooking=rsg.nextSeed();
		bringfood=rsg.nextSeed();
		foodDelivery=rsg.nextSeed();
		eatTm=rsg.nextSeed();
		payLeave=rsg.nextSeed();
	}
	
	public String toString() {
		return("CustArr: "+custArr+"  seating: "+seating+"  ordering: "+ordering+"  orderDelivery: "+orderDelivery+"  ahd: "+ahd
		+"  bringfood: "+bringfood+"  foodDelivery: "+foodDelivery+"  eatTm: "+eatTm+"  PayLeave: "+payLeave);
	}

	
}
