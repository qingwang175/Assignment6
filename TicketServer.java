package Assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TicketServer {
	static int PORT = 2222;
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;

	public static void start(int portNumber) throws IOException {
		PORT = portNumber;
		Runnable serverThread = new ThreadedTicketServer();
		Thread t = new Thread(serverThread);
		t.start();
	}
	
	char row = 'A';
	int seat = 1;
	
	//For use on Rows A-X
	int midRightOffset = 107;
	int leftOffset = 79;
	
	//For use on rows Y-AA
	//int endRightOffset = 126;
	//int endLeftOffset = 92;
	
	//Spits out the best seat available in a String, in the form "X104" or "AA120"
	//If no best seat, it spits back a string that tells you so
	//Had it the way Bates is, but found on Piazza we can simplify to A-Z in the format of Rows C-X, so commented it out (we can always change Back
	
	public String bestAvailableSeat () {
		String temp;
		
		if(row > 90 || (row == 90 && seat > 28)) {
			return "No seats available";
		} else {
			temp = Character.toString(row);
		}
		
		//if(row < 'D') {
		//	temp += (seat + midRightOffset);
		//} else if (row > 'C') {
		temp += seatToSeatNum(seat);
		//} 
		/* else {
			if(seat < 8) {
				temp += (seat + endRightOffset);
			} else {
				temp += (seat + endLeftOffset);
			}
		}
		*/
		return temp;
	}
	
	//Returns a "X102" string 
	public synchronized String markAvailableSeatTaken () {
		String temp;
		if(row == 'Z' && seat == 28) {
			return null;
		} else if(seat == 28) {
			temp =  Integer.toString(row) + seatToSeatNum(seat);
			seat = 1;
			row += 1;
		} else {
			temp =  Integer.toString(row) + seatToSeatNum(seat);
			seat += 1;
		}
		return temp;
	}
	
	public String printTicketSeat(String seat) {
		return null;
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

class ThreadedTicketServer implements Runnable {

	String hostname = "127.0.0.1";
	String threadname = "X";
	String testcase;
	TicketClient sc;

	public void run() {
		// TODO 422C
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(TicketServer.PORT);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}