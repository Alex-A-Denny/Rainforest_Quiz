package rainforestapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rainforestapi.model.User;
import rainforestapi.persistence.UserDAO;

@RestController
@RequestMapping("/Users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDAO;

    public UserController(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> registerUser(@RequestBody User user){
        LOG.log(Level.INFO, "POST /user {0}", user);
        try {
             HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return new ResponseEntity<>(userDAO.registerUser(user),header,HttpStatus.OK);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, "IOException when registering user: " + user, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    
}
