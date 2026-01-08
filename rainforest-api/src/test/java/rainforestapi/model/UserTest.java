package rainforestapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class UserTest {
    
    @Test
    public void testUserConstructor() {
        User user = new User("testuser");
        assertEquals("testuser", user.getUsername());
    }
    
    @Test
    public void testUserConstructorInitializesBadgesAsFalse() {
        User user = new User("testuser");
        assertFalse(user.isSlothBadge());
        assertFalse(user.isParrotBadge());
        assertFalse(user.isJagBadge());
    }
    
    @Test
    public void testUserCreateFactory() {
        User user = User.create("testuser");
        assertEquals("testuser", user.getUsername());
    }
    
    @Test
    public void testGetUsername() {
        User user = new User("alice");
        assertEquals("alice", user.getUsername());
    }
    
    @Test
    public void testUserConstructorWithNullUsername() {
        User user = new User(null);
        assertNull(user.getUsername());
    }
}
