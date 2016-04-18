package Assignment6;

public class Person {
	private String hostName;
	private int CustNumber;
	
	Person(String hostName, int custNumber){
		this.hostName = hostName;
		this.CustNumber = custNumber;
	}
	public int getCustNumber(){
		return CustNumber;
	}
	
}
