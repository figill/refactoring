import java.util.ArrayList; 

public class Customer {

	String pps ="";
	String surname = "";
	String firstName = "";
	String dob ="";
	String customerID = "";
	String password = "";
	
	ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount> ();

	//Blank constructor
	public Customer()
	{
		this.pps = "";
		this.surname = "";
		this.firstName = "";
		this.dob = "";
		this.customerID = "";
		this.password = "";
		this.accounts = null;
	}
	
	//Constructor with details
	public Customer(String pps, String surname, String firstName, String dob, String customerID, String password, ArrayList<CustomerAccount> accounts)
	{
		this.pps = pps;
		this.surname = surname;
		this.firstName = firstName;
		this.dob = dob;
		this.customerID = customerID;
		this.password = password;;
		this.accounts = accounts;
	}
	
	//Accessor methods
	public String getPPS()
	{
		return this.pps;
	}
	
	public String getSurname()
	{
		return this.surname;
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getDOB()
	{
		return this.dob;
	}
	
	public String getCustomerID()
	{
		return this.customerID;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public ArrayList<CustomerAccount> getAccounts()
	{
		return this.accounts;
	}
	
	//mutator methods
	public void setPPS(String pps)
	{
		this.pps = pps;
	}
	
	public void setSurname(String surname)
	{
		this.surname = surname;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public void setDOB(String dob)
	{
		this.dob = dob;
	}
	
	public void setCustomerID(String customerID)
	{
		this.customerID = customerID;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setAccounts(ArrayList<CustomerAccount> accounts)
	{
		this.accounts = accounts;
	}
	
	public String toString()
	{
		return "PPS number = " + this.pps + "\n"
				+ "Surname = " + this.surname + "\n"
				+ "First Name = " + this.firstName + "\n"
				+ "Date of Birth = " + this.dob + "\n"
				+ "Customer ID = " + this.customerID;
			
	}
	
	
}
