import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CustomerDepositAccount extends CustomerAccount
{
   double interestRate;

public CustomerDepositAccount()
{
	super();
	this.interestRate = 0;
}

public CustomerDepositAccount(double interestRate, String number, double balance, ArrayList<AccountTransaction> transactionList)
{
	super(number, balance, transactionList);	
	this.interestRate = interestRate;
}

public double getInterestRate()
{
	return this.interestRate;
}

public void setInterestRate(double interestRate)
{
	this.interestRate = interestRate;
}

public void getBalance(String euro, JFrame f) {
	JOptionPane.showMessageDialog(f,
			"25" + euro + " deposit account fee aplied.", "",
			JOptionPane.INFORMATION_MESSAGE);
	setBalance(getBalance() - 25);
	JOptionPane.showMessageDialog(f, "New balance = " + getBalance(),
			"Success!", JOptionPane.INFORMATION_MESSAGE);
	
}



}