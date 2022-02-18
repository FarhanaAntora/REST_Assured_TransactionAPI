import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;
import java.io.IOException;

public class TestRunner {
    TransactionAPI transaction;

    @Test(priority = 0, description = "Valid login")
    public void doLogin() throws IOException, ConfigurationException {
        transaction = new TransactionAPI();
        transaction.userLogin();
    }

    @Test(priority = 1, description = "Login with invalid email")
    public void LoginWithInvalidEmail() throws IOException, ConfigurationException {
        transaction = new TransactionAPI();
        transaction.invalidEmail();
    }

    @Test(priority = 2, description = "Login with invalid password")
    public void LoginWithInvalidPassword() throws IOException, ConfigurationException {
        transaction = new TransactionAPI();
        transaction.invalidPassword();
    }

    @Test(priority = 3, description = "Show customer list")
    public void ShowList() throws IOException {
        transaction = new TransactionAPI();
        transaction.showList();
    }

    @Test(priority = 4, description = "Show customers of by role")
    public void ShowCustomerByRole() throws IOException {
        transaction = new TransactionAPI();
        transaction.showByRole();
    }

    @Test(priority = 5, description = "Search user by id")
    public void userSearch() throws IOException {
        transaction = new TransactionAPI();
        transaction.searchUser();
    }

    @Test(priority = 6, description = "Generate random user")
    public void GenerateCustomer() throws ConfigurationException, IOException {
        transaction = new TransactionAPI();
        transaction.generateCustomer();
    }

    @Test(priority = 7, description = "Update a user information")
    public void UpdateCustomer() throws IOException {
        transaction = new TransactionAPI();
        transaction.updateCustomer();
    }

    @Test(priority = 8, description = "Show agent balance")
    public void showAgentBalance() throws IOException {
        transaction = new TransactionAPI();
        transaction.showBalance();
    }

    @Test(priority = 9, description = "Deposit money")
    public void moneyDeposit() throws IOException {
        transaction = new TransactionAPI();
        transaction.deposit();
    }

    @Test(priority = 10, description = "Deposit  insufficient balance")
    public void insufficientDeposit() throws IOException {
        transaction = new TransactionAPI();
        transaction.depositInsufficientBalance();
    }

    @Test(priority = 11, description = "Check balance after deposit")
    public void checkBalanceAfterDeposit() throws IOException {
        transaction = new TransactionAPI();
        transaction.checkBalance();
    }

    @Test(priority = 12, description = "Send money to other customer")
    public void sendCustomerMoney() throws IOException {
        transaction = new TransactionAPI();
        transaction.sendMoney();
    }
    @Test(priority = 13, description = "Send money to other customer")
    public void sendInsufficientMoney() throws IOException {
        transaction = new TransactionAPI();
        transaction.sendInsufficientBalance();
    }

    @Test(priority = 14, description = "Check balance after send money")
    public void checkBalance() throws IOException {
        transaction = new TransactionAPI();
        transaction.customerBalance();
    }

    @Test(priority = 15, description = "Withdraw")
    public void ShowWithdraw() throws IOException {
        transaction = new TransactionAPI();
        transaction.withdraw();
    }
    @Test(priority = 16, description = "Withdraw Insufficient Balance")
    public void withdrawInsufficientBalance() throws IOException {
        transaction = new TransactionAPI();
        transaction.withdrawInsufficientMoney();
    }

    @Test(priority = 17, description = "Display transaction list for any account")
    public void displayTransactionForAccount() throws IOException {
        transaction = new TransactionAPI();
        transaction.displayTransaction();
    }

    @Test(priority = 18, description = "Display transaction list for any ID")
    public void displayTransactionForID() throws IOException {
        transaction = new TransactionAPI();
        transaction.showTransaction();
    }

    @Test(priority = 19, description = "Display transaction list")
    public void displayCompleteTransaction() throws IOException {
        transaction = new TransactionAPI();
        transaction.transactionList();
    }
}


