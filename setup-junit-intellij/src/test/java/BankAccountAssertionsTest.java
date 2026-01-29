import org.example.BankAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test BankAccount Class with various Assertions")
public class BankAccountAssertionsTest {

    //assertEquals
    @Test
    @DisplayName("Withdraw 300 successfully")
    public void testWithdrawal() {
        BankAccount bankAccount = new BankAccount(500, -1000);
        bankAccount.withdraw(300);
        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    @DisplayName("Deposit 500 successfully")
    public void testDeposit() {
        BankAccount bankAccount = new BankAccount(400, 0);
        bankAccount.deposit(500);
        assertEquals(900, bankAccount.getBalance());
    }

    //assertNotEquals
    @Test
    @DisplayName("Withdraw will become negative")
    public void testWithdrawNotStuckAtZero(){
        BankAccount bankAccount = new BankAccount(500, -1000);
        bankAccount.withdraw(800);
        assertNotEquals(0, bankAccount.getBalance());
    }

    //assertTrue
    @Test
    @DisplayName("Check Bank Account Active PostCcreation")
    public void testBankAccountActivePostCreation(){
        BankAccount bankAccount = new BankAccount(500, 0);
        assertTrue(bankAccount.isActive());
    }

    //assertNull and assertNotNull
    @Test
    @DisplayName("Check Bank Account Holder Name")
    public void testBankAccountHolderName(){
        BankAccount bankAccount = new BankAccount(500, 0);
        assertNull(bankAccount.getHolderName());
        bankAccount.setHolderName("Test");
        assertNotNull(bankAccount.getHolderName());
    }

    //assertThrows
    @Test
    @DisplayName("No Withdrawal below Minimum")
    public void testNoWithdrawalBelowMinimum(){
        BankAccount bankAccount = new BankAccount(500, 300);
        assertThrows(RuntimeException.class, () -> bankAccount.withdraw(500));
    }

    //assertAll
    //checks if no exceptions are thrown in the various executables
    @Test
    @DisplayName("Check Withdraw and Deposit without Exception")
    public void testWithdrawAndDeposit(){
        BankAccount bankAccount = new BankAccount(500, -1000);
        assertAll(() -> bankAccount.deposit(200), () -> bankAccount.withdraw(450));
    }

    //assertTimeout and assertTimeoutPreemptively
    // to check if a function is taking too much time
    @Test
    @DisplayName("Test Timeout")
    public void testTimeout(){
        BankAccount bankAccount = new BankAccount(500, 0);
        assertTimeout(Duration.ofNanos(1), () -> bankAccount.deposit(500));
    }
}
