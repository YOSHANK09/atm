import java.util.*;

//USER ACCOUNT NUMBER AND PINCODE IS FIXED BUT CAN BE CHANGED
    //ACCOUNT NUMBER = 123456789012
    //PIN CODE = 1234

//CLASS USER FOR THIS PURPOSE


class Transaction {
    private String type;
    private double amount;
    private Date timestamp;

    // Transaction constructor to record the type, amount, and timestamp
    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date(); // Record the current date and time
    }

    // Override toString() method to display transaction details
    @Override
    public String toString() {
        return timestamp + " - " + type + ": $" + amount;
    }
}

class User {
    private String accountNumber;
    private String pin;

    // User constructor to set account number and PIN
    public User(String accountNumber, String pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    // Getter method for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter method for PIN
    public String getPin() {
        return pin;
    }
    public void setPin(String newPin) {
        this.pin = newPin;
    }
}


public class ATM {
    private static final int PIN_LENGTH = 4;
    private static final double MAX_WITHDRAWAL_LIMIT = 25000.0;

    private double balance;
    private List<Transaction> transactionHistory;
    private User user;
    private Scanner scanner;

    // ATM constructor, initializes user data, balance, transaction history, and
    // scanner
    public ATM(User user) {
        this.user = user;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<Transaction>();
        this.scanner = new Scanner(System.in);
    }

    // Main method to run the ATM simulation
    public void run() {
        boolean quit = false;

        // Ask the user for their account number
        System.out.print("Enter your 12-Digit Account Number: ");
        String recipientAccount = scanner.next();

        // Verify the account number
        if (recipientAccount.equals(user.getAccountNumber())) {
            System.out.println("Account verified. Welcome!");

            while (!quit) {
                // Main Menu
                System.out.println("\nChoose an option:");
                System.out.println("1. Balance");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. View Transaction History");
                System.out.println("6. Change Pincode");
                System.out.println("7. Quit");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        checkBalance();
                        break;
                    case 2:
                        withdrawMoney();
                        break;
                    case 3:
                        depositMoney();
                        break;
                    case 4:
                        transferMoney();
                        break;
                    case 5:
                        viewTransactionHistory();
                        break;
                    case 6:
                        changePIN();
                        break;
                    case 7:
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                        break;
                }
            }

            System.out.println("Thank you for using the ATM. Have a nice day!");
        } else {
            System.out.println("Invalid Account Number. Please Try Again.");
            run();
        }
    }

    // Method to check account balance
    private void checkBalance() {
        if (validatePin()) {
            System.out.println("Your current balance is: $" + balance);
            transactionHistory.add(new Transaction("Balance Inquiry", 0.0));
        }
    }

    // Method to withdraw money
    private void withdrawMoney() {
        if (validatePin()) {
            System.out.print("Enter the amount to withdraw (multiple of 100, max $" + MAX_WITHDRAWAL_LIMIT + "): $");
            double amount = scanner.nextDouble();

            if (amount % 100 == 0 && amount <= MAX_WITHDRAWAL_LIMIT && amount > 0) {
                if (amount <= balance) {
                    balance -= amount;
                    transactionHistory.add(new Transaction("Withdraw", amount));
                    System.out.println("Withdrawal successful. Your new balance: $" + balance);
                } else {
                    System.out.println("Insufficient Balance. Please Try Again.");
                }
            } else {
                System.out.println("Invalid amount. Please enter a valid amount.");
            }
        }
    }

    // Method to deposit money
    private void depositMoney() {
        if (validatePin()) {
            System.out.print("Enter the amount to deposit (multiple of 100): $");
            double amount = scanner.nextDouble();

            if (amount % 100 == 0 && amount > 0) {
                balance += amount;
                transactionHistory.add(new Transaction("Deposit", amount));
                System.out.println("Deposit successful. Your new balance: $" + balance);
            } else {
                System.out.println("Invalid amount. Please enter a valid amount.");
            }
        }
    }

    // Method to transfer money to another account
    private void transferMoney() {
        if (validatePin()) {
            System.out.print("Enter the 12-Digit account number of the receiver: ");
            String recipientAccount = scanner.next();

            if (recipientAccount.length() == 12) {
                System.out.print("Enter the amount to transfer (multiple of 100): $");
                double amount = scanner.nextDouble();

                if (amount % 100 == 0 && amount > 0 && amount <= balance) {
                    balance -= amount;
                    transactionHistory.add(new Transaction("Transfer to " + recipientAccount, amount));
                    System.out.println("Transfer successful. Your new balance: $" + balance);
                } else {
                    System.out.println("Invalid amount or insufficient balance. Please try again.");
                }
            } else {
                System.out.println("Invalid account number. Please enter a valid 12-digit account number.");
            }
        }
    }

    // Method to view transaction history
    private void viewTransactionHistory() {
        if (validatePin()) {
            System.out.println("Transaction History:");
            if (transactionHistory.isEmpty()) {
                System.out.println("No Transaction History found");
            } else {
                for (Transaction transaction : transactionHistory) {
                    System.out.println(transaction);
                }
            }
        }
    }

    // Method to validate PIN
    private boolean validatePin() {
        System.out.print("Enter your 4-Digit PIN: ");
        String pin = scanner.next();

        if (pin.length() == PIN_LENGTH && pin.equals(user.getPin())) {
            return true;
        } else {
            System.out.println("Invalid PIN. Access denied.");
            return false;
        }
    }

    //change pin
    private void changePIN() {
        if (validatePin()) {
            System.out.print("Enter your new 4-Digit PIN: ");
            String newPin = scanner.next();
            if(newPin.length()==4){
                user.setPin(newPin);
                System.out.println("PIN changed successfully.");
            }
            else
            {
                System.out.println("Invalid PIN. Access denied.");
            }
        }
    }
    // Main method to start the ATM simulation
    public static void main(String[] args) {
        User user = new User("123456789012", "1234"); // Replace with actual account number and PIN
        ATM atm = new ATM(user);
        atm.run();
    }
}
