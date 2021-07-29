import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.Assert.*;

public class JBCryptTest {
    @Test
    public void test() {
        String password = "password";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        assertTrue(BCrypt.checkpw(password, hashed));
    }
}
