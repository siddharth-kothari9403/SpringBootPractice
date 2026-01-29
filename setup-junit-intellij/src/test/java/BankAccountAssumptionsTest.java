import org.example.BankAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class BankAccountAssumptionsTest {

    //assumptions when false, result in the test being aborted, not failed
    //it is like an if statement saying that only when the condition is true, this should be run

//    @Test
//    @DisplayName("Test activation account after creation")
//    public void testActive(){
//
//        BankAccount bankAccount = new BankAccount(500, 0);
//        assumeTrue(bankAccount == null, "Bank account is null");
//        assertTrue(bankAccount.isActive());
//    }

    @Test
    @DisplayName("Test activation account after creation")
    public void testActiveWithAssumeFalse(){

        BankAccount bankAccount = new BankAccount(500, 0);
        assumeFalse(bankAccount == null, "Bank account is null");
        assertTrue(bankAccount.isActive());
    }

    //assumeTrue and assumeFalse throw exceptions when not met, assumingThat will not, and will not abort, resulting in a passed test
    // conditional test - run only when the assumption is true
    @Test
    @DisplayName("Test activation account after creation")
    public void testActiveWithAssumingThat(){

        BankAccount bankAccount = new BankAccount(500, 0);
        assumingThat(bankAccount == null, () -> assertTrue(bankAccount.isActive()));
    }

}
