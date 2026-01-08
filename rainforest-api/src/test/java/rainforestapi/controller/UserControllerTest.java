package rainforestapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import rainforestapi.model.User;
import rainforestapi.persistence.UserDAO;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {


    @Mock
    private UserDAO userDAO;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserController userController;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.create("testuser");
    }

    @Test
    void testRegisterUser_Success() throws IOException {
        
        when(userDAO.registerUser(testUser)).thenReturn(testUser);
        
        
        ResponseEntity<User> response = userController.registerUser(testUser);
        
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        
        verify(userDAO, times(1)).registerUser(testUser);
    }

    @Test
    void testRegisterUser_IOException() throws IOException {
        
        when(userDAO.registerUser(testUser)).thenThrow(new IOException("Database error"));
        
        
        ResponseEntity<User> response = userController.registerUser(testUser);
        
        
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(userDAO, times(1)).registerUser(testUser);
    }

    @Test
    void testGetUsers_Success() throws IOException {
        
        User[] expectedUsers = new User[]{testUser};
        when(userDAO.getUsers()).thenReturn(expectedUsers);
        
        
        ResponseEntity<User[]> response = userController.getUsers();
        
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(expectedUsers, response.getBody());
        
        verify(userDAO, times(1)).getUsers();
    }

    @Test
    void testGetUsers_EmptyArray() throws IOException {
        
        User[] emptyUsers = new User[]{};
        when(userDAO.getUsers()).thenReturn(emptyUsers);
        
        
        ResponseEntity<User[]> response = userController.getUsers();
        
       
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().length);
        
        verify(userDAO, times(1)).getUsers();
    }

    @Test
    void testGetUsers_IOException() throws IOException {
        
        when(userDAO.getUsers()).thenThrow(new IOException("Connection error"));
        
        
        ResponseEntity<User[]> response = userController.getUsers();
        
        
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(userDAO, times(1)).getUsers();
    }

    @Test
    void testFindUsers_UserFound() throws IOException {
        
        String username = "testuser";
        when(userDAO.findUser(username)).thenReturn(testUser);
        
        
        ResponseEntity<User> response = userController.findUsers(username);
        
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
        
        verify(userDAO, times(1)).findUser(username);
    }

    @Test
    void testFindUsers_UserNotFound() throws IOException {
        
        String username = "nonexistent";
        when(userDAO.findUser(username)).thenReturn(null);
        
        
        ResponseEntity<User> response = userController.findUsers(username);
        
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(userDAO, times(1)).findUser(username);
    }

    @Test
    void testFindUsers_IOException() throws IOException {
        
        String username = "testuser";
        when(userDAO.findUser(username)).thenThrow(new IOException("Search error"));
        
        
        ResponseEntity<User> response = userController.findUsers(username);
        
        
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(userDAO, times(1)).findUser(username);
    }

    @Test
    void testFindUsers_NullUsername() throws IOException {
        
        String username = null;
        when(userDAO.findUser(username)).thenReturn(null);
        
        
        ResponseEntity<User> response = userController.findUsers(username);
        
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(userDAO, times(1)).findUser(username);
    }

    @Test
    void testFindUsers_EmptyUsername() throws IOException {
        
        String username = "";
        when(userDAO.findUser(username)).thenReturn(null);
        
        
        ResponseEntity<User> response = userController.findUsers(username);
        
       
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(userDAO, times(1)).findUser(username);
    }

    @Test
    void testConstructor() {
        
        UserController controller = new UserController(userDAO);
        
        assertNotNull(controller);
    }

    @Test
    void testFindUsers_SpecialCharactersUsername() throws IOException {
        String username = "test@user#123";
        when(userDAO.findUser(username)).thenReturn(testUser);
        
        ResponseEntity<User> response = userController.findUsers(username);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
        verify(userDAO, times(1)).findUser(username);
    }

    @Test
    void testFindUsers_WhitespaceUsername() throws IOException {
        String username = "   ";
        when(userDAO.findUser(username)).thenReturn(null);
        
        ResponseEntity<User> response = userController.findUsers(username);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userDAO, times(1)).findUser(username);
    }

    @Test
    void testFindUsers_LongUsername() throws IOException {
        String username = "a".repeat(255);
        when(userDAO.findUser(username)).thenReturn(testUser);
        
        ResponseEntity<User> response = userController.findUsers(username);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
        verify(userDAO, times(1)).findUser(username);
    }
}