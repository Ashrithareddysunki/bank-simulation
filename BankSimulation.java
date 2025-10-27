import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Account {
    protected String accountHolder;
    protected double balance;
    protected List<String> transactionHistory;
    public Account(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with initial balance: " + balance);
    }
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount + " | Balance: " + balance);
            System.out.println("Successfully deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: " + amount + " | Balance: " + balance);
            System.out.println("Successfully withdrew: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }
    public void displayBalance() {
        System.out.println("Current Balance: " + balance);
    }
    public void showTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        for (String record : transactionHistory) {
            System.out.println(record);
        }
    }
}
class SavingsAccount extends Account {
    private double interestRate;
    public SavingsAccount(String accountHolder, double balance, double interestRate) {
        super(accountHolder, balance);
        this.interestRate = interestRate;
    }
    public void addInterest() {
        double interest = balance * interestRate / 100;
        balance += interest;
        transactionHistory.add("Interest added: " + interest + " | Balance: " + balance);
        System.out.println("Interest added: " + interest);
    }
}
class CurrentAccount extends Account {
    private double overdraftLimit;
    public CurrentAccount(String accountHolder, double balance, double overdraftLimit) {
        super(accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }
    @Override
    public void withdraw(double amount) {
        if (amount > 0 && (balance + overdraftLimit) >= amount) {
            balance -= amount;
            transactionHistory.add("Withdrew (overdraft allowed): " + amount + " | Balance: " + balance);
            System.out.println("Successfully withdrew: " + amount);
        } else {
            System.out.println("Withdrawal exceeds overdraft limit.");
        }
    }
}
public class BankSimulation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("🏦 Welcome to the Bank Account Simulation System!");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = sc.nextDouble();
        System.out.print("Choose account type (1. Savings / 2. Current): ");
        int choice = sc.nextInt();
        Account account;
        if (choice == 1) {
            account = new SavingsAccount(name, balance, 5.0);
        } else {
            account = new CurrentAccount(name, balance, 1000.0);
        }
        int option;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. View Transaction History");
            if (account instanceof SavingsAccount) {
                System.out.println("5. Add Interest");
            }
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double dep = sc.nextDouble();
                    account.deposit(dep);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double wd = sc.nextDouble();
                    account.withdraw(wd);
                    break;
                case 3:
                    account.displayBalance();
                    break;
                case 4:
                    account.showTransactionHistory();
                    break;
                case 5:
                    if (account instanceof SavingsAccount) {
                        ((SavingsAccount) account).addInterest();
                    } else {
                        System.out.println("Option not available for this account type.");
                    }
                    break;
                case 0:
                    System.out.println("Thank you for using our banking system!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 0);
        sc.close();
    }
}
