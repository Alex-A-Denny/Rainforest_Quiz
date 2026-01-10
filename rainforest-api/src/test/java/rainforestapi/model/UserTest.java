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

    @Test
    public void testSetSlothBadge() {
        User user = new User("testuser");
        user.setSlothBadge(true);
        assertTrue(user.isSlothBadge());
    }

    @Test
    public void testSetParrotBadge() {
        User user = new User("testuser");
        user.setParrotBadge(true);
        assertTrue(user.isParrotBadge());
    }

    @Test
    public void testSetJagBadge() {
        User user = new User("testuser");
        user.setJagBadge(true);
        assertTrue(user.isJagBadge());
    }

    @Test
    public void testSetMultipleBadges() {
        User user = new User("testuser");
        user.setSlothBadge(true);
        user.setParrotBadge(true);
        user.setJagBadge(false);
        assertTrue(user.isSlothBadge());
        assertTrue(user.isParrotBadge());
        assertFalse(user.isJagBadge());
    }

    @Test
    public void testBadgeToggle() {
        User user = new User("testuser");
        user.setSlothBadge(true);
        assertTrue(user.isSlothBadge());
        user.setSlothBadge(false);
        assertFalse(user.isSlothBadge());
    }
}
