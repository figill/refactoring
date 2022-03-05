import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CustomerCurrentAccount extends CustomerAccount 
{
	ATMCard atm;
	
public CustomerCurrentAccount()
{
	super();
	this.atm = null;
	
}

public CustomerCurrentAccount(ATMCard atm, String number, double balance, ArrayList<AccountTransaction> transactionList)
{
	super(number, balance, transactionList);	
	this.atm = atm;
}

public ATMCard getAtm()
{
	return this.atm;
}

public void setAtm(ATMCard atm)
{
	this.atm = atm;
}

public void getBalance(String euro, JFrame f) {
	JOptionPane.showMessageDialog(f,
			"15" + euro + " current account fee aplied.", "",
			JOptionPane.INFORMATION_MESSAGE);
	setBalance(getBalance() - 25);
	JOptionPane.showMessageDialog(f, "New balance = " + getBalance(),
			"Success!", JOptionPane.INFORMATION_MESSAGE);
}


// Overdraft (this will be a real number which will be initialized to 0.0 but can be updated by means of a dialog 
// it only applies to current accounts)

}