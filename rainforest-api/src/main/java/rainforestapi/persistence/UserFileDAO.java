package rainforestapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import rainforestapi.model.User;

@Component
public class UserFileDAO implements UserDAO{
    
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());

    Map<String,User> users;

    private ObjectMapper objectMapper; 

    private String filename;

    /**
     * Creates a new User File Data Access Object
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper)throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.users = new TreeMap<>();
        load();
    }
    
    
    /**
     * Generates an array of {@linkplain User user} from the tree map
     * 
     * @return  The array of {@link User userArray}, may be empty
     */
    private User[] getUserArray(){
        return getUserArray(null);
    }
    
    /**
     * Gets a filtered array of {@linkplain User users} that has containsText in their username 
     * @param containsText the text used to filter the array
     * @return The {@link User user} array, may be empty
     */
    private User[] getUserArray(String containsText){
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()){
            if (containsText == null || user.getUsername().contains(containsText)) {
                userArrayList.add(user);
            }
        }
        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    private boolean save()throws IOException{
        User[] userArray = getUserArray();
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    private boolean load()throws IOException{
        File file = new File(filename);
        if (!file.exists()) {
            // Create empty file if it doesn't exist
            save();
            return true;
        }

        users = new TreeMap<>();
        try {
            User[] userArray = objectMapper.readValue(new File(filename), User[].class);

            for(User user : userArray){
                users.put(user.getUsername(), user);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
        
    }

    //Public

    /**
     * {@InheritDoc}
     */
    @Override
    public User registerUser(User user) throws IOException{
        synchronized (users) {
            User newUser = new User(user.getUsername());
            users.put(newUser.getUsername(), newUser);
            save();
            return newUser;
        }
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public User[] getUsers()throws IOException{
        synchronized (users) {
            return(getUserArray());
        }
    }

     /**
     * {@InheritDoc}
     */
    @Override
    public User findUser(String username) throws IOException{
        synchronized (users) {
            if(getUserArray(username).length < 1)
                return null;
            return getUserArray(username)[0];
        }
    }


    /**
     * {@InheritDoc}
     */
    @Override
    public User awardBadge(String username, String badgeName) throws IOException{
        synchronized (users) {
            System.out.println("AWARDING BADGE " + badgeName + " TO USER " + username);
            User user = users.get(username);
            if(user == null){
                System.out.println("USER NOT FOUND");
                return null;
            }
            switch (badgeName.toLowerCase()) {
                case "slothbadge":
                    user.setSlothBadge(true);
                    break;
                case "parrotbadge":
                    System.out.println("AWARDING PARROT BADGE");
                    user.setParrotBadge(true);
                    break;
                case "jagbadge":
                    System.out.println("AWARDING JAG BADGE");
                    user.setJagBadge(true);
                    break;
                default:
                    System.out.println(badgeName + " BADGE NOT FOUND");
                    return null;
            }
            users.put(user.getUsername(), user);
            save();
            return user;
        }
    }
    
}
