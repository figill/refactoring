import javax.swing.JFrame;

public interface IMenu {

	public JFrame frame(String type);
	public void noCustomers();
	public void userNotFound(boolean loop);
	public void displayTransaction(double a, int i);
	public void checkPin(int count);
}
