package rainforest-api.persistence;


import java.io.IOException;

import rainforest-api.model.User;

/**
 * Defines the Data Access Object for Users
 * @author Alex Denny
 */
public interface  UserDAO {
    
    /**
     * Creates a new user object
     * @param user {@linkplain User user} the user that will be created
     * @return The newly created user.
     * @throws IOException If an issue with underlying storage
     */
    User registerUser(User user) throws IOException;

    /**
     * Gets a list of all the users currently stored in data
     * @return The array of users
     * @throws IOException If an issue with underlying storage
     */
    User[] getUsers()throws IOException;

    /**
     * Finds {@linkplain User users} with the specific Username
     * @param Username The username of the {@linkplain User user} your searching for 
     * @return The {@linkplain User user}(if their is one) with the provided username
     * @throws IOException If an issue with underlying storage
     */
    User findUser(String Username) throws IOException;




}
