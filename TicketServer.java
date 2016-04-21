package Assignment6;


/**
 * @author Sumedh Shah
 * @author Qing Wang
 * EE422C Assignment 6
 */

//import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;

public class TicketServer {
	static int PORT = 2222;
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;

	public static void start(int portNumber) throws IOException {  
		PORT = portNumber;
		Seats theater = new Seats();
		Runnable serverThread = new ThreadedTicketServer(theater);
		Thread t = new Thread(serverThread);
		t.start();
	}
	
	
} 

class ThreadedTicketServer implements Runnable {

	String hostname = "127.0.0.1";
	String threadname = "X";
	String queuename;
	String testcase;
	TicketClient sc;
	String temp;
	ServerThread st;
	Seats theater;
	
	public ThreadedTicketServer(Seats t)
	{
		theater = t;
	}
	public void run() {
		// TODO 422C
		try (ServerSocket serverSocket = new ServerSocket(TicketServer.PORT)) {
			
		while(true) {
				ServerThread socket = new ServerThread(serverSocket.accept(), theater);  //Make new threads that are accepted
				//Takes in the theater class to get the whole seating chart
				Thread t = new Thread(socket);
				t.start();			
		}
			
//			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}