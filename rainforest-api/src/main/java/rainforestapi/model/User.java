package rainforestapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    @JsonProperty("username") private final String username;
    @JsonProperty("slothBadge") private boolean slothBadge;
    @JsonProperty("parrotBadge") private boolean parrotBadge;
    @JsonProperty("jagBadge") private boolean jagBadge;

    public User(@JsonProperty("username") String username) {
        this.username = username;
        this.slothBadge = false;
        this.parrotBadge = false;
        this.jagBadge = false;
        LOG.info("User created with username: " + username);
    }

    @JsonCreator
    public static User create(@JsonProperty("username") String username) {
        return new User(username);
    }

    public void setSlothBadge(boolean slothBadge) {
        this.slothBadge = slothBadge;
    }

    public void setParrotBadge(boolean parrotBadge) {
        this.parrotBadge = parrotBadge;
    }

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