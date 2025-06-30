import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
class Customer {
String name;
String email;
String accountType;
double balance;
Customer(String name, String email, String accountType, double initialDeposit)
{
this.name = name;
this.email = email;
this.accountType = accountType;
this.balance = initialDeposit;
}
}
public class BankSystem {
static Map<String, Customer> customerDatabase = new HashMap<>();
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
while (true) {
System.out.println("\nBank System Menu:");
System.out.println("1. Create Account");
System.out.println("2. Deposit Amount");
System.out.println("3. Transfer Amount");
System.out.println("4. Account Info");
System.out.println("5. Mini Statement");
System.out.println("6. Check Balance");
System.out.println("7. Exit");
System.out.print("Enter your choice: ");
int choice = scanner.nextInt();
switch (choice) {
case 1:
createAccount();
break;
case 2:
depositAmount();
break;
case 3:
transferAmount();
break;
case 4:
accountInfo();
break;
case 5:
miniStatement();
break;
case 6:
checkBalance();
break;
case 7:
System.out.println("Exiting the program. Thank you!");
System.exit(0);
break;
default:
System.out.println("Invalid choice. Please enter a valid option.");
}
}
}
private static void createAccount() {
Scanner scanner = new Scanner(System.in);
System.out.print("Enter your name: ");
String name = scanner.nextLine();
System.out.print("Enter your email: ");
String email = scanner.nextLine();
System.out.print("Enter your account type: ");
String accountType = scanner.nextLine();
System.out.print("Enter initial deposit amount: ");
double initialDeposit = scanner.nextDouble();
Customer customer = new Customer (name, email, accountType, initialDeposit);
customerDatabase.put(email, customer);
System.out.println("Account created successfully!");
}
private static void depositAmount() {
Scanner scanner = new Scanner(System.in);
System.out.print("Enter your email: ");
String email = scanner.nextLine();
if (customerDatabase.containsKey(email)) {
Customer customer = customerDatabase.get(email);
System.out.print("Enter deposit amount: ");
double depositAmount = scanner.nextDouble();
customer.balance += depositAmount;
System.out.println("Deposit successful. Updated balance: " +
customer.balance);
} else {
System.out.println("Account not found. Please create an account first.");
}
}
private static void transferAmount() {
Scanner scanner = new Scanner (System.in);
System.out.print("Enter your email: ");
String senderEmail = scanner.nextLine();
if (customerDatabase.containsKey(senderEmail)) {
System.out.print("Enter recipient's email: ");
String recipientEmail = scanner.nextLine();
if (customerDatabase.containsKey(recipientEmail)) {
Customer sender = customerDatabase.get(senderEmail);
Customer recipient = customerDatabase.get(recipientEmail);
System.out.print("Enter transfer amount: ");
double transferAmount = scanner.nextDouble();
if (sender.balance >= transferAmount) {
sender.balance -= transferAmount;
recipient.balance += transferAmount;
System.out.println("Transfer successful.");
} else {
System.out.println("Insufficient funds for transfer.");
}
} else {
System.out.println("Recipient account not found.");
}
} else {
System.out.println("Account not found. Please create an account first.");
}
}
private static void accountInfo() {
Scanner scanner = new Scanner (System.in);
System.out.print("Enter your email: ");
String email = scanner.nextLine();
if (customerDatabase.containsKey(email)) {
Customer customer = customerDatabase.get(email);
System.out.println("Account Type: " + customer.accountType);
System.out.println("Name: " + customer.name);
System.out.println("Email: " + customer.email);
System.out.println("Balance: " + customer.balance);
} else {
System.out.println("Account not found. Please create an account first.");
}
}
private static void miniStatement() {
Scanner scanner = new Scanner (System.in);
System.out.print("Enter your email: ");
String email = scanner.nextLine();
if (customerDatabase.containsKey(email)) {
// Assuming mini statement is not implemented in this example
System.out.println("Mini statement feature not implemented.");
} else {
System.out.println("Account not found. Please create an account first.");
}
}
private static void checkBalance() {
Scanner scanner = new Scanner (System.in);
System.out.print("Enter your email: ");
String email = scanner.nextLine();
if (customerDatabase.containsKey(email)) {
Customer customer = customerDatabase.get(email);
System.out.println("Your current balance: " + customer.balance);
} else {
System.out.println("Account not found. Please create an account first.");
}}}
