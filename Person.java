package Assignment6;

/**
 * @author Sumedh Shah
 * @author Qing Wang
 * EE422C Assignment 6
 */

public class Person {
	private String HostName;
	private int CustNumber;
	
	Person(String hostName, int custNumber){
		this.HostName = hostName;
		this.CustNumber = custNumber;
	}
	
	public int getCustNumber(){
		return CustNumber;
	}
	
	public String getHostName(){
		return HostName;
	}
	
	
}
