package rainforestapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import rainforestapi.model.User;
import rainforestapi.persistence.UserDAO;
import rainforestapi.model.request.LoginRequest;
import rainforestapi.model.request.Registration;

/**
 * REST Controller for handling user-related endpoints.
 * 
 * Provides HTTP endpoints for user registration, retrieval, searching, and badge management.
 * All endpoints are prefixed with "/Users" and return JSON responses.
 * 
 * This controller handles:
 * - User registration
 * - Retrieving all users
 * - Searching for users by username
 * - Awarding badges to users for completing quiz categories
 * 
 * @author Alex Denny
 * @version 1.0
 */
@RestController
@RequestMapping("/Users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private final SecurityContextRepository securityContextRepository =
        new HttpSessionSecurityContextRepository();
    private UserDAO userDAO;

    /**
     * Constructs a UserController with the specified UserDAO.
     * 
     * @param userDAO The data access object for user operations
     */
    public UserController(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> login(
        @Valid @RequestBody LoginRequest loginRequest,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        User user = userDAO.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(UsernamePasswordAuthenticationToken.authenticated(
            user.getUsername(), null, List.of()));
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Registers a new user in the system.
     * 
     * Endpoint: POST /Users
     * Accepts: JSON representation of a User
     * Returns: The newly created User object with initialized badges
     * 
     * @param user The user object to register
     * @return ResponseEntity containing the registered user or INTERNAL_SERVER_ERROR if an issue occurs
     */
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> registerUser(@Valid @RequestBody Registration registration){
        LOG.log(Level.INFO, "POST /user {0}", registration);
        try {
            User user = new User(
                registration.getUsername(), 
                registration.getPassword()
            );
            
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return new ResponseEntity<>(userDAO.registerUser(user),header,HttpStatus.OK);
        }
        catch (IOException e) {
            User user = new User(
                registration.getUsername(), 
                registration.getPassword()
            );

            LOG.log(Level.SEVERE, "IOException when registering user: " + user, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all users currently registered in the system.
     * 
     * Endpoint: GET /Users
     * Returns: Array of all User objects
     * 
     * @return ResponseEntity containing an array of all users or INTERNAL_SERVER_ERROR if an issue occurs
     */
    @GetMapping("")
    public ResponseEntity<User[]> getUsers(){
        LOG.log(Level.INFO, "GET /UsersALL");
        try{
            User[] users = userDAO.getUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, "IOException when getting users", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Searches for a user by username.
     * 
     * Endpoint: GET /Users/search?username={username}
     * Returns: The User object if found, or NOT_FOUND if the user does not exist
     * 
     * @param username The username to search for
     * @return ResponseEntity containing the found user, NOT_FOUND if not found, or INTERNAL_SERVER_ERROR if an issue occurs
     */
    @GetMapping("/search")
    public ResponseEntity<User> findUsers(@RequestParam String username){
        LOG.log(Level.INFO, "GET /Users/search?username={0}", username);
        try{
            User user = userDAO.findUser(username);

            if( user != null)
                return new ResponseEntity<>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, "IOException when searching for users with username: " + username, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Awards a badge to a user for completing a quiz category.
     * 
     * Endpoint: PUT /Users/{username}/animals/{badgeName}
     * Supported badges: slothBadge, parrotBadge, jagBadge
     * Returns: The updated User object with the new badge awarded
     * 
     * @param username The username of the user to award the badge to
     * @param badgeName The name of the badge to award (slothBadge, parrotBadge, or jagBadge)
     * @return ResponseEntity containing the updated user, NOT_FOUND if user doesn't exist, or INTERNAL_SERVER_ERROR if an issue occurs
     */
    @PutMapping("/{username}/animals/{badgeName}")
    public ResponseEntity<User> awardBadge(
        @PathVariable String username,
        @PathVariable String badgeName,
        Authentication authentication
    ){
        LOG.log(Level.INFO, "PUT /Users/{0}/animals/{1}", new Object[]{username, badgeName});
        if (authentication == null || !username.equals(authentication.getName())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try{
            User updatedUser = userDAO.awardBadge(username, badgeName);
            if( updatedUser != null)
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, "IOException when awarding badge: " + badgeName + " to user: " + username, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
