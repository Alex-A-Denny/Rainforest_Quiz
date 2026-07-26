package rainforestapi.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA Entity representing a user in the database.
 * 
 * This class maps to the "users" table in the MySQL database and is used
 * by Spring Data JPA for persistence operations. It mirrors the structure
 * of the User domain model but is specific to database interactions.
 * 
 * The entity stores user information including:
 * - username: The unique identifier for the user
 * - slothBadge: Whether the user has earned the sloth badge
 * - parrotBadge: Whether the user has earned the parrot badge
 * - jagBadge: Whether the user has earned the jaguar badge
 * 
 * @author Alex Denny
 * @version 1.0
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(nullable = false, length = 100)
    private String username;

    @Column(name = "sloth_badge", nullable = false)
    private boolean slothBadge;

    @Column(name = "parrot_badge", nullable = false)
    private boolean parrotBadge;

    @Column(name = "jag_badge", nullable = false)
    private boolean jagBadge;

    /**
     * Default constructor for JPA.
     * 
     * Protected to prevent direct instantiation; JPA requires a no-argument constructor.
     */
    protected UserEntity() {
    }

    /**
     * Constructs a UserEntity with the specified attributes.
     * 
     * @param username The unique identifier for the user
     * @param slothBadge Whether the user has earned the sloth badge
     * @param parrotBadge Whether the user has earned the parrot badge
     * @param jagBadge Whether the user has earned the jaguar badge
     */
    public UserEntity(String username, boolean slothBadge, boolean parrotBadge, boolean jagBadge) {
        this.username = username;
        this.slothBadge = slothBadge;
        this.parrotBadge = parrotBadge;
        this.jagBadge = jagBadge;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSlothBadge() {
        return slothBadge;
    }

    public void setSlothBadge(boolean slothBadge) {
        this.slothBadge = slothBadge;
    }

    public boolean isParrotBadge() {
        return parrotBadge;
    }

    public void setParrotBadge(boolean parrotBadge) {
        this.parrotBadge = parrotBadge;
    }

    public boolean isJagBadge() {
        return jagBadge;
    }

    public void setJagBadge(boolean jagBadge) {
        this.jagBadge = jagBadge;
    }
}