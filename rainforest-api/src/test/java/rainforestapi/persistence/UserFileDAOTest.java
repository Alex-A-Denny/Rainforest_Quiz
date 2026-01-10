package rainforestapi.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.databind.ObjectMapper;
import rainforestapi.model.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserFileDAOTest {
    
    @Mock
    private ObjectMapper objectMapper;
    
    @TempDir
    private Path tempDir;
    
    private UserFileDAO userFileDAO;
    private String testFilename;
    
    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        testFilename = tempDir.resolve("users.json").toString();
        when(objectMapper.readValue(any(File.class), eq(User[].class)))
            .thenReturn(new User[]{});
        userFileDAO = new UserFileDAO(testFilename, objectMapper);
    }
    
    @Test
    void testRegisterUser() throws IOException {
        User user = new User("testuser");
        User result = userFileDAO.registerUser(user);
        
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }
    
    @Test
    void testGetUsers() throws IOException {
        userFileDAO.registerUser(new User("user1"));
        userFileDAO.registerUser(new User("user2"));
        
        User[] users = userFileDAO.getUsers();
        
        assertEquals(2, users.length);
    }
    
    @Test
    void testFindUser() throws IOException {
        userFileDAO.registerUser(new User("testuser"));
        
        User foundUser = userFileDAO.findUser("testuser");
        
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }
    
    @Test
    void testFindUserNotFound() throws IOException {
        User foundUser = userFileDAO.findUser("nonexistent");
        
        assertNull(foundUser);
    }

    @Test
    void testAwardBadgeSloth() throws IOException {
        userFileDAO.registerUser(new User("testuser"));
        
        User result = userFileDAO.awardBadge("testuser", "sloth");
        
        assertNotNull(result);
        assertTrue(result.isSlothBadge());
    }

    @Test
    void testAwardBadgeParrot() throws IOException {
        userFileDAO.registerUser(new User("testuser"));
        
        User result = userFileDAO.awardBadge("testuser", "parrot");
        
        assertNotNull(result);
        assertTrue(result.isParrotBadge());
    }

    @Test
    void testAwardBadgeJag() throws IOException {
        userFileDAO.registerUser(new User("testuser"));
        
        User result = userFileDAO.awardBadge("testuser", "jag");
        
        assertNotNull(result);
        assertTrue(result.isJagBadge());
    }

    @Test
    void testAwardBadgeCaseInsensitive() throws IOException {
        userFileDAO.registerUser(new User("testuser"));
        
        User result = userFileDAO.awardBadge("testuser", "SLOTH");
        
        assertNotNull(result);
        assertTrue(result.isSlothBadge());
    }

    @Test
    void testAwardBadgeUserNotFound() throws IOException {
        User result = userFileDAO.awardBadge("nonexistent", "sloth");
        
        assertNull(result);
    }

    @Test
    void testAwardBadgeInvalidBadge() throws IOException {
        userFileDAO.registerUser(new User("testuser"));
        
        User result = userFileDAO.awardBadge("testuser", "invalid");
        
        assertNull(result);
    }
}
