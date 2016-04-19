package Assignment6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	TicketClient sc;

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
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			out.println("A");
			String output = in.readLine(); //read String from server
			System.out.println(output);
			
			echoSocket.close();
		} catch (Exception e) {
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

	synchronized void requestTicket() { 
		// TODO thread.run()
		tc.run();
		
		
		while(!queue.isEmpty()){
			queue.remove(0);
		}
		
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

