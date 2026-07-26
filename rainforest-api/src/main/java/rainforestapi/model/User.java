package rainforestapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user in the Rainforest Quiz application.
 * 
 * Each user has a unique username and can earn three types of badges:
 * - Sloth Badge: Awarded for completing the sloth quiz
 * - Parrot Badge: Awarded for completing the parrot quiz  
 * - Jag Badge: Awarded for completing the jaguar quiz
 * 
 * This class is serializable to/from JSON for API communication.
 * 
 * @author Alex Denny
 * @version 1.0
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    @JsonProperty("username") private final String username;
    @JsonProperty("slothBadge") private boolean slothBadge;
    @JsonProperty("parrotBadge") private boolean parrotBadge;
    @JsonProperty("jagBadge") private boolean jagBadge;

    /**
     * Constructs a new User with the specified username.
     * 
     * All badges are initialized to false when a user is first created.
     * 
     * @param username The unique identifier for the user
     */
    public User(@JsonProperty("username") String username) {
        this.username = username;
        this.slothBadge = false;
        this.parrotBadge = false;
        this.jagBadge = false;
        LOG.info("User created with username: " + username);
    }

    /**
     * JSON factory method for creating User instances.
     * 
     * @param username The unique identifier for the user
     * @return A new User instance
     */
    @JsonCreator
    public static User create(@JsonProperty("username") String username) {
        return new User(username);
    }

    /**
     * Sets the sloth badge status for the user.
     * 
     * @param slothBadge true if the user has earned the sloth badge, false otherwise
     */
    public void setSlothBadge(boolean slothBadge) {
        this.slothBadge = slothBadge;
    }

    /**
     * Sets the parrot badge status for the user.
     * 
     * @param parrotBadge true if the user has earned the parrot badge, false otherwise
     */
    public void setParrotBadge(boolean parrotBadge) {
        this.parrotBadge = parrotBadge;
    }

    /**
     * Sets the jaguar badge status for the user.
     * 
     * @param jagBadge true if the user has earned the jaguar badge, false otherwise
     */
    public void setJagBadge(boolean jagBadge) {
        this.jagBadge = jagBadge;
    }

    public boolean isSlothBadge() {
        return slothBadge;
    }

    public boolean isParrotBadge() {
        return parrotBadge;
    }

    public boolean isJagBadge() {
        return jagBadge;
    }

    public String getUsername() {
        return username;
    }
}