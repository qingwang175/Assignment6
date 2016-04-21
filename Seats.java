package Assignment6;
/**
 * @author Sumedh Shah
 * @author Qing Wang
 * EE422C Assignment 6
 */
public class Seats {
	boolean full = false;
	//String markingThread = null;
	
	char row = 'A';  //Seating chart variables
	int seat = 1;
	
	//For use on Rows A-X
	int midRightOffset = 107;
	int leftOffset = 79;
	
	public Seats ()  //Constructor
	{
		
	}
	//For use on rows Y-AA
	//int endRightOffset = 126;
	//int endLeftOffset = 92;
	
	//Spits out the best seat available in a String, in the form "X104" or "AA120"
	//If no best seat, it spits back a string that tells you so
	//Had it the way Bates is, but found on Piazza we can simplify to A-Z in the format of Rows C-X, so commented it out (we can always change Back
	
	public String bestAvailableSeat (String office) { //Returns a phrase saying which is the best available seat (does not reserve)
		String temp;
		
		temp = Integer.toString(seatToSeatNum(seat));
		
		if(row > 'Z' || (row == 'Z' && seat > 28)) {
			full = true;
			return "Sorry, we are sold out.";
		} else {
			temp += Character.toString(row);
		}
		
		//if(row < 'D') {
		//	temp += (seat + midRightOffset);
		//} else if (row > 'C') {
		//} 
		/* else {
			if(seat < 8) {
				temp += (seat + endRightOffset);
			} else {
				temp += (seat + endLeftOffset);
			}
		}
		*/
		return temp + " is the best available seat. (Request by Thread " + office + ")";
		//return null;
	}
	
	//Returns a "102X" string
	//Synchronized so that only one seat can be used at a time ?
	//Question: Should we let all through and simply return that the other threads entering should print a line saying seat not bought?
	public synchronized String markAvailableSeatTaken (String threadNum) {
		/*
		if(markingThread != null) {
			return "Box Office " + threadNum + ": Failed to reserve HR," + seat + row + ". Already allocated.";
		} else {
			markingThread = threadNum;
		}
		*/
		
		String temp;
		
		if(row > 'Z') {
			full = true;
			return "Box Office " + threadNum + " did not reserve any seats.";
		} else if(seat == 28) {
			temp = seatToSeatNum(seat) + Character.toString(row);
			seat = 1;
			row += 1;
		} else {
			temp = seatToSeatNum(seat) + Character.toString(row);
			seat += 1;
		}
		
		//markingThread = null;
		return "Box Office " + threadNum + ": Reserved HR, " + temp + ".";
		//return null;
	}
	
	//Changes from seat 1-28 to the according seat, 101 - 128
	private int seatToSeatNum (int seat) {
		if(seat > 21) {
			return (seat + leftOffset);
		} else {
			return (seat + midRightOffset);
		}
	}
	
}
