import org.example.BankAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(BankAccountParameterResolver.class)
public class BankAccountRepeatedTest {

    @RepeatedTest(5)
    @DisplayName("Deposit 500 successfully")
    public void testDeposit(BankAccount bankAccount) {

        //bankAccount provided by BankAccountParameterResolver with balance 0 and min balance 0
        bankAccount.deposit(500);
        assertEquals(500, bankAccount.getBalance());
    }

    @RepeatedTest(5)
    @DisplayName("Deposit 500 successfully")
    public void testDepositRepetitionInfo(BankAccount bankAccount, RepetitionInfo repetitionInfo) {

        //bankAccount provided by BankAccountParameterResolver with balance 0 and min balance 0
        bankAccount.deposit(500);
        assertEquals(500, bankAccount.getBalance());
        System.out.println("Nr: " + repetitionInfo.getCurrentRepetition());
    }
}