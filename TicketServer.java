package Assignment6;

/**
 * @author Sumedh Shah
 * @author Qing Wang
 * EE422C Assignment 6
 */

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
	
	boolean full = false;
	String markingThread = null;
	
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
	
	public String bestAvailableSeat (String office) {
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
	}
	
	//Returns a "102X" string
	//Synchronized so that only one seat can be used at a time 
	//Question: Should we let all through and simply return that the other threads entering should print a line saying seat not bought?
	public synchronized String markAvailableSeatTaken (String threadNum) {
		
		if(markingThread != null) {
			return "Box Office " + threadNum + ": Failed to reserve HR," + seat + row + ". Already allocated.";
		} else {
			markingThread = threadNum;
		}
		
		String temp;
		
		if(row == 'Z' && seat > 28) {
			return null;
		} else if(seat == 28) {
			temp = seatToSeatNum(seat) + Character.toString(row);
			seat = 1;
			row += 1;
		} else {
			temp = seatToSeatNum(seat) + Character.toString(row);
			seat += 1;
		}
		
		markingThread = null;
		return "Box Office " + threadNum + ": Reserved HR, " + temp + ".";
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
	String queuename;
	String testcase;
	TicketClient sc;
	String temp;
	

	public void run() {
		// TODO 422C
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(TicketServer.PORT);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			TicketServer server = new TicketServer();
			
			queuename = in.readLine();
			out.println(server.bestAvailableSeat(queuename));
			while(server.full == false) {
				temp = server.markAvailableSeatTaken(threadname);
				if(temp != null) {
					out.println(temp);
				} 
				out.println(server.bestAvailableSeat(queuename));
			}
			out.println("STOP");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}