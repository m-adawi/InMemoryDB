import Server.Authenticator;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthenticatorTest {

    @Test
    public void test() {
        Authenticator authenticator = new Authenticator("users test");
        String username = "Malek Adawi";
        String password = "password";
        authenticator.addUser(username, password);
        assertTrue(authenticator.areValidCredentials(username, password));
        authenticator.deleteUser(username);
        assertFalse(authenticator.areValidCredentials(username, password));
    }
}
