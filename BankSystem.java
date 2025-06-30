import java.io.*;
import java.util.*;

// Serializable Customer class to store account information
class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    String email;
    String accountType;
    double balance;
    List<String> transactionHistory; // Stores recent transactions

    // Constructor
    Customer(String name, String email, String accountType, double initialDeposit) {
        this.name = name;
        this.email = email.toLowerCase();
        this.accountType = accountType;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add("Account opened with ₹" + initialDeposit);
    }

    // Add a new transaction to the history
    void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    // Display the last 5 transactions
    void showMiniStatement() {
        System.out.println("\n--- Mini Statement for " + name + " ---");
        int start = Math.max(transactionHistory.size() - 5, 0);
        for (int i = start; i < transactionHistory.size(); i++) {
            System.out.println(transactionHistory.get(i));
        }
    }
}

public class bb {
    static Map<String, Customer> customerDatabase = new HashMap<>();
    static final String DATA_FILE = "data.txt"; // File to persist data

    public static void main(String[] args) {
        loadData(); // Load customer data from file
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("\n--- Bank System Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Amount");
            System.out.println("3. Transfer Amount");
            System.out.println("4. Account Info");
            System.out.println("5. Mini Statement");
            System.out.println("6. Check Balance");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();

            // Developer access using secret keyword
            if (input.equalsIgnoreCase("dev")) {
                System.out.print("Enter developer password: ");
                String password = scanner.nextLine();
                if (password.equals("admin123")) {
                    showAllAccounts();
                } else {
                    System.out.println("Unauthorized access.");
                }
                continue;
            }

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> createAccount(scanner);
                    case 2 -> depositAmount(scanner);
                    case 3 -> transferAmount(scanner);
                    case 4 -> accountInfo(scanner);
                    case 5 -> miniStatement(scanner);
                    case 6 -> checkBalance(scanner);
                    case 7 -> {
                        saveData(); // Save before exiting
                        System.out.println("Exiting the program. Thank you!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Enter a number from 1 to 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 7 or type 'dev'.");
            }
        }
    }

    // Create new customer account
    private static void createAccount(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim().toLowerCase();
        if (!email.contains("@") || customerDatabase.containsKey(email)) {
            System.out.println("Invalid or duplicate email.");
            return;
        }

        System.out.print("Enter your account type (e.g., Savings): ");
        String accountType = scanner.nextLine().trim();

        System.out.print("Enter initial deposit amount: ");
        try {
            double initialDeposit = Double.parseDouble(scanner.nextLine());
            if (initialDeposit < 0) throw new NumberFormatException();
            Customer customer = new Customer(name, email, accountType, initialDeposit);
            customerDatabase.put(email, customer);
            saveData();
            System.out.println("Account created successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Deposit funds into account
    private static void depositAmount(Scanner scanner) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim().toLowerCase();

        if (customerDatabase.containsKey(email)) {
            Customer customer = customerDatabase.get(email);
            System.out.print("Enter deposit amount: ");
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                if (amount <= 0) throw new NumberFormatException();
                customer.balance += amount;
                customer.addTransaction("Deposited ₹" + amount + " | Balance: ₹" + customer.balance);
                saveData();
                System.out.println("Deposit successful. New Balance: ₹" + customer.balance);
            } catch (NumberFormatException e) {
                System.out.println("Invalid deposit amount.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    // Transfer funds between accounts
    private static void transferAmount(Scanner scanner) {
        System.out.print("Enter your email: ");
        String senderEmail = scanner.nextLine().trim().toLowerCase();

        if (!customerDatabase.containsKey(senderEmail)) {
            System.out.println("Sender account not found.");
            return;
        }

        System.out.print("Enter recipient's email: ");
        String recipientEmail = scanner.nextLine().trim().toLowerCase();

        if (!customerDatabase.containsKey(recipientEmail)) {
            System.out.println("Recipient account not found.");
            return;
        }

        Customer sender = customerDatabase.get(senderEmail);
        Customer recipient = customerDatabase.get(recipientEmail);

        System.out.print("Enter transfer amount: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) throw new NumberFormatException();
            if (sender.balance >= amount) {
                sender.balance -= amount;
                recipient.balance += amount;

                sender.addTransaction("Transferred ₹" + amount + " to " + recipient.email + " | Balance: ₹" + sender.balance);
                recipient.addTransaction("Received ₹" + amount + " from " + sender.email + " | Balance: ₹" + recipient.balance);
                saveData();

                System.out.println("Transfer successful.");
            } else {
                System.out.println("Insufficient funds.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }

    // Show account details
    private static void accountInfo(Scanner scanner) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim().toLowerCase();

        if (customerDatabase.containsKey(email)) {
            Customer customer = customerDatabase.get(email);
            System.out.println("\n--- Account Info ---");
            System.out.println("Name: " + customer.name);
            System.out.println("Email: " + customer.email);
            System.out.println("Account Type: " + customer.accountType);
            System.out.println("Balance: ₹" + customer.balance);
        } else {
            System.out.println("Account not found.");
        }
    }

    // Show recent transactions
    private static void miniStatement(Scanner scanner) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim().toLowerCase();

        if (customerDatabase.containsKey(email)) {
            customerDatabase.get(email).showMiniStatement();
        } else {
            System.out.println("Account not found.");
        }
    }

    // Show account balance
    private static void checkBalance(Scanner scanner) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim().toLowerCase();

        if (customerDatabase.containsKey(email)) {
            System.out.println("Current Balance: ₹" + customerDatabase.get(email).balance);
        } else {
            System.out.println("Account not found.");
        }
    }

    // Developer-only method to show all accounts
    private static void showAllAccounts() {
        if (customerDatabase.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        System.out.println("\n--- All Saved Accounts ---");
        for (Customer c : customerDatabase.values()) {
            System.out.println("Name: " + c.name);
            System.out.println("Email: " + c.email);
            System.out.println("Account Type: " + c.accountType);
            System.out.println("Balance: ₹" + c.balance);
            System.out.println("------------------------");
        }
    }

    // Save all customer data to file
    private static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(customerDatabase);
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    // Load data from file when program starts
    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            customerDatabase = (Map<String, Customer>) in.readObject();
        } catch (Exception e) {
            System.out.println("Failed to load saved data: " + e.getMessage());
        }
    }
}
