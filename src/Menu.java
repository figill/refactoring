import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import java.util.Date;
import java.util.Scanner;

public class Menu extends JFrame implements IMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<Customer> customerList = new ArrayList<Customer>();
	private int position = 0;
	private Customer customer = null;
	private CustomerAccount customerAccount = new CustomerAccount();
	Customer cust;
	
	JFrame f, f1;
	JLabel firstNameLabel, surnameLabel, pPPSLabel, dOBLabel, customerIDLabel, passwordLabel;
	JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField, customerIDTextField, passwordTextField;
	JPanel panel2;
	JButton addButton;
	Container content;
	
	static String pps;
	static String firstName;
	static String surname;
	static String dob;
	static String customerID;
	private static String password;

	public static void main(String[] args) {
		Menu driver = new Menu();
		readFromFile();
		driver.userMenu();
	}

	public void userMenu() {

		f = frame("User Type");
		f.setVisible(true);

		JPanel userTypePanel = new JPanel();
		final ButtonGroup userType = new ButtonGroup();
		JRadioButton radioButton;
		userTypePanel.add(radioButton = new JRadioButton("Existing Customer"));
		radioButton.setActionCommand("Customer");
		userType.add(radioButton);

		userTypePanel.add(radioButton = new JRadioButton("Administrator"));
		radioButton.setActionCommand("Administrator");
		userType.add(radioButton);

		userTypePanel.add(radioButton = new JRadioButton("New Customer"));
		radioButton.setActionCommand("New Customer");
		userType.add(radioButton);

		JPanel continuePanel = new JPanel();
		JButton continueButton = new JButton("Continue");
		continuePanel.add(continueButton);

		Container content = f.getContentPane();
		content.setLayout(new GridLayout(2, 1));
		content.add(userTypePanel);
		content.add(continuePanel);

		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String user = userType.getSelection().getActionCommand();

				// if user selects NEW CUSTOMER
				if (user.equals("New Customer")) {
					createCustomer();

				}

				// if user select ADMIN
				if (user.equals("Administrator")) {
					loginAdministrator();
				}

				// if user selects CUSTOMER
				if (user.equals("Customer")) {
					loginCustomer();
				}
			}
		});
		f.setVisible(true);
	}

	public void adminMenu() {
		dispose();

		f = frame("Administrator Menu");
		f.setVisible(true);

		JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteCustomer = new JButton("Delete Customer");
		deleteCustomer.setPreferredSize(new Dimension(250, 20));
		deleteCustomerPanel.add(deleteCustomer);

		JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteAccount = new JButton("Delete Account");
		deleteAccount.setPreferredSize(new Dimension(250, 20));
		deleteAccountPanel.add(deleteAccount);

		JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bankChargesButton = new JButton("Apply Bank Charges");
		bankChargesButton.setPreferredSize(new Dimension(250, 20));
		bankChargesPanel.add(bankChargesButton);

		JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton interestButton = new JButton("Apply Interest");
		interestPanel.add(interestButton);
		interestButton.setPreferredSize(new Dimension(250, 20));

		JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton editCustomerButton = new JButton("Edit existing Customer");
		editCustomerPanel.add(editCustomerButton);
		editCustomerButton.setPreferredSize(new Dimension(250, 20));

		JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton navigateButton = new JButton("Navigate Customer Collection");
		navigatePanel.add(navigateButton);
		navigateButton.setPreferredSize(new Dimension(250, 20));

		JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton summaryButton = new JButton("Display Summary Of All Accounts");
		summaryPanel.add(summaryButton);
		summaryButton.setPreferredSize(new Dimension(250, 20));

		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton accountButton = new JButton("Add an Account to a Customer");
		accountPanel.add(accountButton);
		accountButton.setPreferredSize(new Dimension(250, 20));

		JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton returnButton = new JButton("Exit Admin Menu");
		returnPanel.add(returnButton);

		JLabel label1 = new JLabel("Please select an option");

		content = f.getContentPane();
		content.setLayout(new GridLayout(9, 1));
		content.add(label1);
		content.add(accountPanel);
		content.add(bankChargesPanel);
		content.add(interestPanel);
		content.add(editCustomerPanel);
		content.add(navigatePanel);
		content.add(summaryPanel);
		content.add(deleteCustomerPanel);
		content.add(deleteAccountPanel);
		content.add(returnPanel);

		bankChargesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (customerList.isEmpty()) {
					noCustomers();

				} else {
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Apply Charges to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							userNotFound(loop);
						} else {
							f = frame("Administartor Menu");
							f.setVisible(true);

							JComboBox<String> box = new JComboBox<String>();
							for (int i = 0; i < customer.getAccounts().size(); i++) {

								box.addItem(customer.getAccounts().get(i).getNumber());
							}

							box.getSelectedItem();
							JPanel boxPanel = new JPanel();
							boxPanel.add(box);
							JPanel buttonPanel = new JPanel();
							JButton continueButton = new JButton("Apply Charge");
							JButton returnButton = new JButton("Return");
							buttonPanel.add(continueButton);
							buttonPanel.add(returnButton);
							Container content = f.getContentPane();
							content.setLayout(new GridLayout(2, 1));
							content.add(boxPanel);
							content.add(buttonPanel);

							if (customer.getAccounts().isEmpty()) { //REFACTOR
								JOptionPane.showMessageDialog(f,
										"This customer has no accounts! \n The admin must add acounts to this customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								f.dispose();
								adminMenu();
							} else {

								for (int i = 0; i < customer.getAccounts().size(); i++) {
									if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
										customerAccount = customer.getAccounts().get(i);
									}
								}

								continueButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										String euro = "\u20ac";

										if (customerAccount instanceof CustomerDepositAccount) {

											((CustomerDepositAccount) customerAccount).displayBalance(euro, f);
										}

										if (customerAccount instanceof CustomerCurrentAccount) {

											((CustomerCurrentAccount) customerAccount).displayBalance(euro, f);
										}

										f.dispose();
										adminMenu();
									}
								});

								returnButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										f.dispose();
										userMenu();
									}
								});

							}
						}
					}
				}

			}
		});

		interestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (customerList.isEmpty()) {
					noCustomers();

				} else {
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Apply Interest to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							userNotFound(loop);
						} else {
							f = frame("Administrator Menu");
							f.setVisible(true);

							JComboBox<String> box = new JComboBox<String>();
							for (int i = 0; i < customer.getAccounts().size(); i++) {
								box.addItem(customer.getAccounts().get(i).getNumber());
							}

							box.getSelectedItem();

							JPanel boxPanel = new JPanel();
							JLabel label = new JLabel("Select an account to apply interest to:");
							boxPanel.add(label);
							boxPanel.add(box);
							JPanel buttonPanel = new JPanel();
							JButton continueButton = new JButton("Apply Interest");
							JButton returnButton = new JButton("Return");
							buttonPanel.add(continueButton);
							buttonPanel.add(returnButton);
							Container content = f.getContentPane();
							content.setLayout(new GridLayout(2, 1));

							content.add(boxPanel);
							content.add(buttonPanel);

							if (customer.getAccounts().isEmpty()) { //REFACTOR
								JOptionPane.showMessageDialog(f,
										"This customer has no accounts! \n The admin must add acounts to this customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								f.dispose();
								adminMenu();
							} else {

								for (int i = 0; i < customer.getAccounts().size(); i++) {
									if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
										customerAccount = customer.getAccounts().get(i);
									}
								}

								continueButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										String euro = "\u20ac";
										double interest = 0;
										boolean loop = true;

										while (loop) {
											String interestString = JOptionPane.showInputDialog(f,
													"Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");// the

											if (isNumeric(interestString)) {

												interest = Double.parseDouble(interestString);
												loop = false;

												customerAccount.setBalance(
														customerAccount.getBalance() + (customerAccount.getBalance() * (interest / 100)));

												JOptionPane.showMessageDialog(f,
														interest + "% interest applied. \n new balance = "
																+ customerAccount.getBalance() + euro,
																"Success!", JOptionPane.INFORMATION_MESSAGE);
											}

											else {
												JOptionPane.showMessageDialog(f, "You must enter a numerical value!",
														"Oops!", JOptionPane.INFORMATION_MESSAGE);
											}

										}

										f.dispose();
										adminMenu();
									}
								});

								returnButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										f.dispose();
										userMenu();
									}
								});

							}
						}
					}
				}

			}
		});

		editCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true,  found = false;


				if (customerList.isEmpty()) {
					noCustomers();

				} else {

					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f, "Enter Customer ID:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							userNotFound(loop);
						} else {
							loop = false;
						}

					}

					f.dispose();

					f.dispose();
					f = frame("Administrator Menu");
					

					firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
					surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
					pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
					customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
					passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
					firstNameTextField = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					customerIDTextField = new JTextField(20);
					passwordTextField = new JTextField(20);

					JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

					JPanel cancelPanel = new JPanel();

					textPanel.add(firstNameLabel);
					textPanel.add(firstNameTextField);
					textPanel.add(surnameLabel);
					textPanel.add(surnameTextField);
					textPanel.add(pPPSLabel);
					textPanel.add(pPSTextField);
					textPanel.add(dOBLabel);
					textPanel.add(dOBTextField);
					textPanel.add(customerIDLabel);
					textPanel.add(customerIDTextField);
					textPanel.add(passwordLabel);
					textPanel.add(passwordTextField);

					firstNameTextField.setText(customer.getFirstName());
					surnameTextField.setText(customer.getSurname());
					pPSTextField.setText(customer.getPPS());
					dOBTextField.setText(customer.getDOB());
					customerIDTextField.setText(customer.getCustomerID());
					passwordTextField.setText(customer.getPassword());

					JButton saveButton = new JButton("Save");
					JButton cancelButton = new JButton("Exit");

					cancelPanel.add(cancelButton, BorderLayout.SOUTH);
					cancelPanel.add(saveButton, BorderLayout.SOUTH);
					// content.removeAll();
					Container content = f.getContentPane();
					content.setLayout(new GridLayout(2, 1));
					content.add(textPanel, BorderLayout.NORTH);
					content.add(cancelPanel, BorderLayout.SOUTH);

					f.setContentPane(content);
					f.setSize(340, 350);
					f.setLocation(200, 200);
					f.setVisible(true);
					f.setResizable(false);

					saveButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							customer.setFirstName(firstNameTextField.getText());
							customer.setSurname(surnameTextField.getText());
							customer.setPPS(pPSTextField.getText());
							customer.setDOB(dOBTextField.getText());
							customer.setCustomerID(customerIDTextField.getText());
							customer.setPassword(passwordTextField.getText());
							

							JOptionPane.showMessageDialog(null, "Changes Saved.");
						}
					});

					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							f.dispose();

							adminMenu();
						}
					});
				}
			}
		});

		summaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				f = frame("Summary of Transactions");
				f.setVisible(true);

				JLabel label1 = new JLabel("Summary of all transactions: ");

				JPanel returnPanel = new JPanel();
				JButton returnButton = new JButton("Return");
				returnPanel.add(returnButton);

				JPanel textPanel = new JPanel();

				textPanel.setLayout(new BorderLayout());
				JTextArea textArea = new JTextArea(40, 20);
				textArea.setEditable(false);
				textPanel.add(label1, BorderLayout.NORTH);
				textPanel.add(textArea, BorderLayout.CENTER);
				textPanel.add(returnButton, BorderLayout.SOUTH);

				JScrollPane scrollPane = new JScrollPane(textArea);
				textPanel.add(scrollPane);

				for (int a = 0; a < customerList.size(); a++)// For each customer, for each account, it displays each
					// transaction.
				{
					for (int b = 0; b < customerList.get(a).getAccounts().size(); b++) {
						customerAccount = customerList.get(a).getAccounts().get(b);
						for (int c = 0; c < customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {

							textArea.append(customerAccount.getTransactionList().get(c).toString());

						}
					}
				}

				textPanel.add(textArea);
				content.removeAll();

				Container content = f.getContentPane();
				content.setLayout(new GridLayout(1, 1));
				content.add(textPanel);

				returnButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						f.dispose();
						adminMenu();
					}
				});
			}
		});

		navigateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				if (customerList.isEmpty()) {
					noCustomers();
				} else {

					JButton first, previous, next, last, cancel;
					JPanel gridPanel, buttonPanel, cancelPanel;

					Container content = getContentPane();

					content.setLayout(new BorderLayout());

					buttonPanel = new JPanel();
					gridPanel = new JPanel(new GridLayout(8, 2));
					cancelPanel = new JPanel();

					firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
					surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
					pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
					customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
					passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
					firstNameTextField = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					customerIDTextField = new JTextField(20);
					passwordTextField = new JTextField(20);

					first = new JButton("First");
					previous = new JButton("Previous");
					next = new JButton("Next");
					last = new JButton("Last");
					cancel = new JButton("Cancel");

					firstNameTextField.setText(customerList.get(0).getFirstName());
					surnameTextField.setText(customerList.get(0).getSurname());
					pPSTextField.setText(customerList.get(0).getPPS());
					dOBTextField.setText(customerList.get(0).getDOB());
					customerIDTextField.setText(customerList.get(0).getCustomerID());
					passwordTextField.setText(customerList.get(0).getPassword());

					firstNameTextField.setEditable(false);
					surnameTextField.setEditable(false);
					pPSTextField.setEditable(false);
					dOBTextField.setEditable(false);
					customerIDTextField.setEditable(false);
					passwordTextField.setEditable(false);

					gridPanel.add(firstNameLabel);
					gridPanel.add(firstNameTextField);
					gridPanel.add(surnameLabel);
					gridPanel.add(surnameTextField);
					gridPanel.add(pPPSLabel);
					gridPanel.add(pPSTextField);
					gridPanel.add(dOBLabel);
					gridPanel.add(dOBTextField);
					gridPanel.add(customerIDLabel);
					gridPanel.add(customerIDTextField);
					gridPanel.add(passwordLabel);
					gridPanel.add(passwordTextField);

					buttonPanel.add(first);
					buttonPanel.add(previous);
					buttonPanel.add(next);
					buttonPanel.add(last);

					cancelPanel.add(cancel);

					content.add(gridPanel, BorderLayout.NORTH);
					content.add(buttonPanel, BorderLayout.CENTER);
					content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
					first.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							position = 0;
							displayCustomerInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField, customerIDTextField, passwordTextField, position);
						}
					});

					previous.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							if (position < 1) {
							} else {
								position = position - 1;

								displayCustomerInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField, customerIDTextField, passwordTextField, position);
							}
						}
					});

					next.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							if (position == customerList.size() - 1) {
							} else {
								position = position + 1;

								displayCustomerInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField, customerIDTextField, passwordTextField, position);
							}

						}
					});

					last.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							position = customerList.size() - 1;

							displayCustomerInfo(firstNameTextField, surnameTextField, pPSTextField, dOBTextField, customerIDTextField, passwordTextField, position);
						}
					});

					cancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							dispose();
							adminMenu();
						}
					});
					setContentPane(content);
					setSize(400, 300);
					setVisible(true);
				}
			}
		});

		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				if (customerList.isEmpty()) {
					noCustomers();
				} else {
					boolean loop = true, found = false;


					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Add an Account to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							userNotFound(loop);

						} else {
							loop = false;
							String[] choices = { "Current Account", "Deposit Account" };
							String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
									"Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

							if (account.equals("Current Account")) {
								// create current account
								boolean valid = true;
								double balance = 0;
								String number = String.valueOf("C" + (customerList.indexOf(customer) + 1) * 10
										+ (customer.getAccounts().size() + 1));// this simple algorithm generates the
								// account number
								ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
								int randomPIN = (int) (Math.random() * 9000) + 1000;
								String pin = String.valueOf(randomPIN);

								ATMCard atm = new ATMCard(randomPIN, valid);

								CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance,
										transactionList); // add to current class ?

								customer.getAccounts().add(current);
								JOptionPane.showMessageDialog(f, "Account number = " + number + "\n PIN = " + pin,
										"Account created.", JOptionPane.INFORMATION_MESSAGE);

								f.dispose();
								adminMenu();
							}

							if (account.equals("Deposit Account")) {
								// create deposit account

								double balance = 0, interest = 0;
								String number = String.valueOf("D" + (customerList.indexOf(customer) + 1) * 10
										+ (customer.getAccounts().size() + 1));// this simple algorithm generates the
								// account number
								ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();

								CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance,
										transactionList); // add to deposit class ?

								customer.getAccounts().add(deposit);
								JOptionPane.showMessageDialog(f, "Account number = " + number, "Account created.",
										JOptionPane.INFORMATION_MESSAGE);

								f.dispose();
								adminMenu();
							}

						}
					}
				}
			}
		});

		deleteCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;

				if (customerList.isEmpty()) {
					noCustomers();
				} else {
					{
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Delete:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							userNotFound(loop);
						} else {
							if (customer.getAccounts().size() > 0) {
								JOptionPane.showMessageDialog(f,
										"This customer has accounts. \n You must delete a customer's accounts before deleting a customer ",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
							} else {
								customerList.remove(customer);
								JOptionPane.showMessageDialog(f, "Customer Deleted ", "Success.",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					}
				}
			}
		});

		deleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;

				{
					Object customerID = JOptionPane.showInputDialog(f,
							"Customer ID of Customer from which you wish to delete an account");

					for (Customer aCustomer : customerList) {

						if (aCustomer.getCustomerID().equals(customerID)) {
							found = true;
							customer = aCustomer;
							loop = false;
						}
					}

					if (found == false) {
						userNotFound(loop);
					} else {

					}

				}
			}

		});
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
				userMenu();
			}
		});
	}

	public void customerMenu(Customer cust1) {
		cust = cust1;
		f = frame("Customer Menu");
		f.setVisible(true);

		if (cust.getAccounts().size() == 0) {
			JOptionPane.showMessageDialog(f,
					"This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ",
					"Oops!", JOptionPane.INFORMATION_MESSAGE);
			f.dispose();
			userMenu();
		} else {
			JPanel buttonPanel = new JPanel();
			JPanel boxPanel = new JPanel();
			JPanel labelPanel = new JPanel();

			JLabel label = new JLabel("Select Account:");
			labelPanel.add(label);

			JButton returnButton = new JButton("Return");
			buttonPanel.add(returnButton);
			JButton continueButton = new JButton("Continue");
			buttonPanel.add(continueButton);

			JComboBox<String> box = new JComboBox<String>();
			for (int i = 0; i < cust.getAccounts().size(); i++) {
				box.addItem(cust.getAccounts().get(i).getNumber());
			}

			for (int i = 0; i < cust.getAccounts().size(); i++) {
				if (cust.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
					customerAccount = cust.getAccounts().get(i);
				}
			}

			boxPanel.add(box);
			content = f.getContentPane();
			content.setLayout(new GridLayout(3, 1));
			content.add(labelPanel);
			content.add(boxPanel);
			content.add(buttonPanel);

			returnButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					f.dispose();
					userMenu();
				}
			});

			continueButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {

					f.dispose();

					f = frame("Customer Menu");
					f.setVisible(true);

					JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton statementButton = new JButton("Display Bank Statement");
					statementButton.setPreferredSize(new Dimension(250, 20));

					statementPanel.add(statementButton);

					JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton lodgementButton = new JButton("Lodge money into account");
					lodgementPanel.add(lodgementButton);
					lodgementButton.setPreferredSize(new Dimension(250, 20));

					JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton withdrawButton = new JButton("Withdraw money from account");
					withdrawalPanel.add(withdrawButton);
					withdrawButton.setPreferredSize(new Dimension(250, 20));

					JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
					JButton returnButton = new JButton("Exit Customer Menu");
					returnPanel.add(returnButton);

					JLabel label1 = new JLabel("Please select an option");

					content = f.getContentPane();
					content.setLayout(new GridLayout(5, 1));
					content.add(label1);
					content.add(statementPanel);
					content.add(lodgementPanel);
					content.add(withdrawalPanel);
					content.add(returnPanel);

					statementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							f.dispose();
							f = frame("Customer Menu");
							f.setVisible(true);

							JLabel label1 = new JLabel("Summary of account transactions: ");

							JPanel returnPanel = new JPanel();
							JButton returnButton = new JButton("Return");
							returnPanel.add(returnButton);
							JPanel textPanel = new JPanel();
							textPanel.setLayout(new BorderLayout());
							JTextArea textArea = new JTextArea(40, 20);
							textArea.setEditable(false);
							textPanel.add(label1, BorderLayout.NORTH);
							textPanel.add(textArea, BorderLayout.CENTER);
							textPanel.add(returnButton, BorderLayout.SOUTH);

							JScrollPane scrollPane = new JScrollPane(textArea);
							textPanel.add(scrollPane);

							for (int i = 0; i < customerAccount.getTransactionList().size(); i++) {
								textArea.append(customerAccount.getTransactionList().get(i).toString());

							}

							textPanel.add(textArea);
							content.removeAll();

							Container content = f.getContentPane();
							content.setLayout(new GridLayout(1, 1));
							content.add(label1);
							content.add(textPanel);
							content.add(returnPanel);

							returnButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									f.dispose();
									customerMenu(cust);
								}
							});
						}
					});

					lodgementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean on = true;
							double balance = 0;

							if (customerAccount instanceof CustomerCurrentAccount) {
								int count = 3;
								checkPin(count);

							}
							if (on == true) {
								String balanceTest = JOptionPane.showInputDialog(f, "Enter amount you wish to lodge:");

								if (isNumeric(balanceTest)) {

									balance = Double.parseDouble(balanceTest);

								} else {
									JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}

								displayTransaction(balance, 0);

							}

						}
					});

					withdrawButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean on = true;
							double withdraw = 0;

							if (customerAccount instanceof CustomerCurrentAccount) {
								int count = 3;
								checkPin(count);
							}
							if (on == true) {
								String balanceTest = JOptionPane.showInputDialog(f,
										"Enter amount you wish to withdraw (max 500):");
								if (isNumeric(balanceTest)) {

									withdraw = Double.parseDouble(balanceTest);
								

								} else {
									JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}
								if (withdraw > 500) {
									JOptionPane.showMessageDialog(f, "500 is the maximum you can withdraw at a time.",
											"Oops!", JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}
								if (withdraw > customerAccount.getBalance()) {
									JOptionPane.showMessageDialog(f, "Insufficient funds.", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}

								displayTransaction(withdraw, 1);

							}

						}
					});

					returnButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							f.dispose();
							userMenu();
						}
					});
				}
			});
		}
	}

	public static boolean isNumeric(String str) // a method that tests if a string is numeric
	{
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public void createCustomer() {
		f.dispose();
		f1 = frame("Create New Customer");
		Container content = f1.getContentPane();
		content.setLayout(new BorderLayout());

		firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
		surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
		pPPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
		dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
		firstNameTextField = new JTextField(20);
		surnameTextField = new JTextField(20);
		pPSTextField = new JTextField(20);
		dOBTextField = new JTextField(20);
		JPanel panel = new JPanel(new GridLayout(6, 2));
		panel.add(firstNameLabel);
		panel.add(firstNameTextField);
		panel.add(surnameLabel);
		panel.add(surnameTextField);
		panel.add(pPPSLabel);
		panel.add(pPSTextField);
		panel.add(dOBLabel);
		panel.add(dOBTextField);

		panel2 = new JPanel();
		addButton = new JButton("Add");

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				pps = pPSTextField.getText();
				firstName = firstNameTextField.getText();
				surname = surnameTextField.getText();
				dob = dOBTextField.getText();
				password = "";
				customerID = "ID" + pps;

				ArrayList<String> customersID = new ArrayList<String>();

				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						f1.dispose();

						boolean loop = true;
						while (loop) {
							password = JOptionPane.showInputDialog(f, "Enter 7 character Password;");

							if (password.length() != 7)// Making sure password is 7 characters
							{
								JOptionPane.showMessageDialog(null, null,
										"Password must be 7 charatcers long", JOptionPane.OK_OPTION);
							} else {
								loop = false;
							}
						}
						try {
							File readFile = new File("/Users/fionagill/Downloads/customer_login_information.txt");
							Scanner scan = new Scanner(readFile);
							while (scan.hasNextLine()) {
								String data = scan.nextLine();
								customersID.add(data);
							}

							scan.close();
						} catch (FileNotFoundException fileNotFound) {
							fileNotFound.printStackTrace();

						}

						if (!customersID.contains(customerID)) {
							ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();
							Customer customer = new Customer(pps, surname, firstName, dob, customerID , password, accounts);
							customerList.add(customer);
							try {
								FileWriter myWriter = new FileWriter("/Users/fionagill/Downloads/customer_login_information.txt", true);
								myWriter.write(customer.getCustomerID() + "\n");
								myWriter.close();
								FileWriter details = new FileWriter("/Users/fionagill/Downloads/customer_login_information.txt", true);
								details.write("\n" + customer.toString());
								details.close();
								System.out.println("Successfully wrote to the file.");

							} catch (IOException exception) {
								System.out.println("An error occurred.");
								exception.printStackTrace();
							}
							userMenu();
						} else {

						}

					}
				});
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f1.dispose();
				userMenu();
			}
		});

		panel2.add(addButton);
		panel2.add(cancel);

		content.add(panel, BorderLayout.CENTER);
		content.add(panel2, BorderLayout.SOUTH);

		f1.setVisible(true);

	}

	public void loginAdministrator() {
		boolean loop = true, loop2 = true;
		boolean cont = false;
		while (loop) {
			Object adminUsername = JOptionPane.showInputDialog(f, "Enter Administrator Username:");

			if (!adminUsername.equals("admin"))// search admin list for admin with matching admin username
			{
				int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					loop = true;
				} else if (reply == JOptionPane.NO_OPTION) {
					f1.dispose();
					loop = false;
					loop2 = false;
					userMenu();
				}
			} else {
				loop = false;
			}
		}

		while (loop2) {
			Object adminPassword = JOptionPane.showInputDialog(f, "Enter Administrator Password;");

			if (!adminPassword.equals("admin11"))// search admin list for admin with matching admin password
			{
				int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {

				} else if (reply == JOptionPane.NO_OPTION) {
					f1.dispose();
					loop2 = false;
					userMenu();
				}
			} else {
				loop2 = false;
				cont = true;
			}
		}

		if (cont) {
			f.dispose();
			loop = false;
			adminMenu();
		}

	}

	private static void readFromFile() {
		ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();

		// Read from file
		try {
			Scanner file = new Scanner(new File("/Users/fionagill/Downloads/customer_login_information.txt"));
			while (file.hasNextLine()) {
				String data = file.nextLine();
				if (data.contains("PPS number")) {
					String[] split = data.split("= ");
					pps = split[1];
				} else if (data.contains("Surname")) {
					String[] split1 = data.split("= ");
					surname = split1[1];
				} else if (data.contains("First Name")) {
					String[] split2 = data.split("= ");
					firstName = split2[1];
				} else if (data.contains("Date of Birth")) {
					String[] split3 = data.split("= ");
					dob = split3[1];
				} else if (data.contains("Customer ID")) {
					String[] split4 = data.split("= ");
					customerID = split4[1];
				} else if (data.contains("Password")) {
					String[] split5 = data.split("= ");
					password = split5[1];
				}

				if (!pps.isEmpty() && !surname.isEmpty() && !firstName.isEmpty() && !dob.isEmpty()
						&& !customerID.isEmpty() && !password.isEmpty()) {
					
					customerList.add(new Customer(pps, surname, firstName, dob, customerID, password, accounts));

					pps = "";
					surname = "";
					firstName = "";
					dob = "";
					customerID = "";
					password = "";

				} else {
					
				}

			}
			file.close();
		} catch (FileNotFoundException fileNotFound) {
			fileNotFound.printStackTrace();
		}

	}

	public void loginCustomer() {
		boolean loop = true;
		Customer customer = null;
		while (loop) {
			Object customerID = JOptionPane.showInputDialog(f, "Enter Customer ID:");
			

			if (searchCustomer(customer, customerID, f) == false) {
				int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					loop = true;
				} else if (reply == JOptionPane.NO_OPTION) {
					f.dispose();
					loop = false;
					userMenu();
				}
			} else {
				loop = false;
			}

		}


	}

	@Override
	public JFrame frame(String type) {
		JFrame f = new JFrame(type);
		f.setSize(600, 400);
		f.setLocation(300, 300);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		return f;
	}

	public void displayCustomerInfo(JTextField firstNameTextField, JTextField surnameTextField, JTextField pPSTextField,
			JTextField dOBTextField, JTextField customerIDTextField, JTextField passwordTextField, int p) {
	
		firstNameTextField.setText(customerList.get(p).getFirstName());
		surnameTextField.setText(customerList.get(p).getSurname());
		pPSTextField.setText(customerList.get(p).getPPS());
		dOBTextField.setText(customerList.get(p).getDOB());
		customerIDTextField.setText(customerList.get(p).getCustomerID());
		passwordTextField.setText(customerList.get(p).getPassword());

	}

	@Override
	public void noCustomers() {
		JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
		f.dispose();
		adminMenu();

	}

	@Override
	public void userNotFound(boolean loop) {
		int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			loop = true;
		} else if (reply == JOptionPane.NO_OPTION) {
			f.dispose();
			loop = false;

			adminMenu();
		}

	}

	@Override
	public void displayTransaction(double a, int i) {
		if (i == 0) {
			String euro = "\u20ac";
			customerAccount.setBalance(customerAccount.getBalance() + a);
			Date date = new Date();
			String date2 = date.toString();
			String type = "Lodgement";
			double amount = a;

			AccountTransaction transaction = new AccountTransaction(date2, type, amount);
			customerAccount.getTransactionList().add(transaction);

			JOptionPane.showMessageDialog(f, a + euro + " added do you account!", "Lodgement",
					JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(f, "New balance = " + customerAccount.getBalance() + euro, "Lodgement",
					JOptionPane.INFORMATION_MESSAGE);

		} else {

			String euro = "\u20ac";
			customerAccount.setBalance(customerAccount.getBalance() - a);
			Date date = new Date();
			String date2 = date.toString();

			String type = "Withdraw";
			double amount = a;

			AccountTransaction transaction = new AccountTransaction(date2, type, amount);
			customerAccount.getTransactionList().add(transaction);

			JOptionPane.showMessageDialog(f, a + euro + " withdrawn.", "Withdraw", JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(f, "New balance = " + customerAccount.getBalance() + euro, "Withdraw",
					JOptionPane.INFORMATION_MESSAGE);

		}

	}

	@Override
	public void checkPin(int count) {
		int checkPin = ((CustomerCurrentAccount) customerAccount).getAtm().getPin();
		boolean loop = true;
		boolean on = true;

		while (loop) {
			if (count == 0) {
				JOptionPane.showMessageDialog(f, "Pin entered incorrectly 3 times. ATM card locked.", "Pin",
						JOptionPane.INFORMATION_MESSAGE);
				((CustomerCurrentAccount) customerAccount).getAtm().setValid(false);
				customerMenu(cust);
				loop = false;
				on = false;
			}

			String Pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN;");
			int i = Integer.parseInt(Pin);

			if (on) {
				if (checkPin == i) {
					loop = false;
					JOptionPane.showMessageDialog(f, "Pin entry successful", "Pin", JOptionPane.INFORMATION_MESSAGE);

				} else {
					count--;
					JOptionPane.showMessageDialog(f, "Incorrect pin. " + count + " attempts remaining.", "Pin",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		}

	}


	public boolean searchCustomer(Customer customer, Object customerID, JFrame f) {
		boolean cont = false;
		boolean found = false;
		for (Customer aCustomer : customerList) {

			if (aCustomer.getCustomerID().equals(customerID))// search customer list for matching customer ID
			{
				found = true;
				customer = aCustomer;
				Object customerPassword = JOptionPane.showInputDialog(f, "Enter Customer Password;");

				if (!customer.getPassword().equals(customerPassword))// check if customer password is correct
				{
					int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?",
							JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {

					} else if (reply == JOptionPane.NO_OPTION) {
						f.dispose();
						
						userMenu();
					}
				} else {
					
					cont = true;
				}
				
				if (cont) {
					f.dispose();
					
					customerMenu(customer);
				}
			}
		}
		return found;
	}


}
