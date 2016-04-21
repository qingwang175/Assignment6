package Assignment6;

/**
 * @author Sumedh Shah
 * @author Qing Wang
 * EE422C Assignment 6
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	TicketClient sc;
	boolean full = false;

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
	}

	public void run() {
		System.out.flush();
		try {
			Socket echoSocket = new Socket(hostname, TicketServer.PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			out.println(threadname);
			String output = in.readLine(); //read String from server, what is the best seat
			
			while(!output.equals("STOP")){ //STOP is flag from server when seats are out
				System.out.println(output);
				try {
					Thread.sleep(5); //Allows concurrency to work
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				out.println(threadname); //Sends threadname to server, to buy ticket
				output = in.readLine(); //The results of buying the ticket
			}
			echoSocket.close();
		} catch (ConnectException cs) {  //Once the seats are sold out, just end program and catch these
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";
	ArrayList<Person> queue;

	TicketClient(String hostname, String threadname) {
		tc = new ThreadedTicketClient(this, hostname, threadname);
		hostName = hostname;
		threadName = threadname;
		generateRandomClients();
	}

	TicketClient(String name) {
		this("localhost", name);
		generateRandomClients();
	}

	TicketClient() {
		this("localhost", "unnamed queue");
		generateRandomClients();
	}

	void requestTicket() { //Use this
		// TODO thread.run()
		tc.run();
		
		
		//while(!queue.isEmpty()){
		//	queue.remove(0);
		//}
		
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void generateRandomClients(){
		Random rand = new Random();
		int max = 1000;
		int min = 100;
	    int randomNum = rand.nextInt((max - min) + 1) + min;
		this.queue = new ArrayList<Person>();
		for(int i = 0; i < randomNum; i++){
			Person t = new Person("localhost",i+1);
			this.queue.add(t);
		}
		
	}
}

