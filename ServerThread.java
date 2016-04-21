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
import java.net.Socket;

public class ServerThread  implements Runnable {

	Socket clientSocket;
	String temp;
	TicketServer server;
	PrintWriter out;
	BufferedReader in;
	Seats theater;
	
	public ServerThread(Socket socket, Seats t) {  //Constructor that takes Seats (theater) class and the socket
		try {
			this.clientSocket = socket;
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		theater = t;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {  //Implemented in each of the threads
		try {
			while(theater.full == false)   //Until the end of the seats are filled
			{
			String queuename = in.readLine();
			out.println(theater.bestAvailableSeat(queuename));   //Show which is the best seat, sent through socket
			if(theater.full == false) {    //If still room, mark a seat available and send it to the socket
				temp = theater.markAvailableSeatTaken(queuename);
				out.println(temp);     //Send out the seat being taken
			}
			}	
			if(theater.full == true) {
				out.println("STOP");   //Always the last line sent, so we know to end the seating (box office closed)
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
}
