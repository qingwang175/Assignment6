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

public class ServerThread extends Thread{

	Socket clientSocket;
	String queuename;
	String temp;
	TicketServer server;
	PrintWriter out;
	BufferedReader in;

	public ServerThread(Socket socket) {
		this.clientSocket = socket;
	}

	public TicketServer returnTheater(TicketServer serve) throws IOException {
		this.server = serve;
		queuename = in.readLine();
		out.println(server.bestAvailableSeat(queuename));
		if(server.full == false) {
			temp = server.markAvailableSeatTaken("A");
			out.println(temp);
		}
		if(server.full == true) {
			out.println("STOP");
		}
		return server;
	}
	
	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start(){
		run();
	}
	
	
}
