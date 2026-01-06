package rainforest-api.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    @JsonProperty("Username") private final String username;
    @JsonProperty("SlothBadge") private boolean slothBadge;
    @JsonProperty("ParrotBadge") private boolean parrotBadge;
    @JsonProperty("JagBadge") private boolean jagBadge;

    public User(@JsonProperty("Username") String username) {
        this.username = username;
        this.slothBadge = false;
        this.parrotBadge = false;
        this.jagBadge = false;
        LOG.info("User created with username: " + username);
    }

    @JsonCreator
    public static User create(@JsonProperty("Username") String username) {
        return new User(username);
    }

    public String getUsername() {
        return username;
    }
}