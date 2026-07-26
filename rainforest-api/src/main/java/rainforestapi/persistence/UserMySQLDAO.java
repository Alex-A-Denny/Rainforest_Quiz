package rainforestapi.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import rainforestapi.model.User;

/**
 * MySQL implementation of the UserDAO interface.
 * 
 * This class provides data access operations for users using explicit SQL queries
 * through Spring Data JPA's @Query annotation. It serves as an adapter between
 * the business logic (UserController) and the database layer (UserRepository).
 * 
 * All database operations use explicit SQL queries rather than relying on
 * JPA's automatic query generation, providing more control and transparency
 * over the database access layer.
 * 
 * Key responsibilities:
 * - Converting between User domain objects and UserEntity database objects
 * - Implementing all UserDAO interface methods
 * - Managing badge operations and normalization
 * - Executing explicit SQL queries through the repository
 * 
 * The class is annotated with @Component to make it a Spring bean and
 * @Transactional to ensure all database operations are atomic.
 * 
 * @author Alex Denny
 * @version 1.0
 */
@Component
@Transactional
public class UserMySQLDAO implements UserDAO {

    private final UserRepository userRepository;

    /**
     * Constructs a UserMySQLDAO with the specified UserRepository.
     * 
     * @param userRepository The Spring Data JPA repository for user operations
     */
    public UserMySQLDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) throws IOException {
        UserEntity entity = toEntity(user);
        UserEntity savedUser = userRepository.save(entity);
        return toUser(savedUser);
    }

    @Override
    public User[] getUsers() throws IOException {
        List<UserEntity> entities = userRepository.findAllUsers();
        List<User> users = new ArrayList<>(entities.size());

        for (UserEntity entity : entities) {
            users.add(toUser(entity));
        }

        return users.toArray(new User[0]);
    }

    @Override
    public User findUser(String username) throws IOException {
        Optional<UserEntity> entity = userRepository.findUserByUsername(username);
        return entity.map(this::toUser).orElse(null);
    }

    @Override
    public User awardBadge(String username, String badgeName) throws IOException {
        Optional<UserEntity> entityOptional = userRepository.findUserByUsername(username);
        if (entityOptional.isEmpty()) {
            return null;
        }

        String normalizedBadge = normalizeBadgeName(badgeName);

        // Execute the appropriate update query based on the badge type
        int rowsUpdated = 0;
        switch (normalizedBadge) {
            case "slothbadge":
                rowsUpdated = userRepository.awardSlothBadge(username);
                break;
            case "parrotbadge":
                rowsUpdated = userRepository.awardParrotBadge(username);
                break;
            case "jagbadge":
                rowsUpdated = userRepository.awardJaguarBadge(username);
                break;
            default:
                return null;
        }

        // If the update was successful, fetch and return the updated user
        if (rowsUpdated > 0) {
            Optional<UserEntity> updatedEntity = userRepository.findUserByUsername(username);
            return updatedEntity.map(this::toUser).orElse(null);
        }
        
        return null;
    }

    /**
     * Converts a User domain object to a UserEntity for database persistence.
     * 
     * @param user The User domain object
     * @return A UserEntity with the same data as the User
     */
    private UserEntity toEntity(User user) {
        return new UserEntity(
            user.getUsername(),
            user.isSlothBadge(),
            user.isParrotBadge(),
            user.isJagBadge()
        );
    }

    /**
     * Converts a UserEntity database object to a User domain object.
     * 
     * @param entity The UserEntity from the database
     * @return A User domain object with the same data as the entity
     */
    private User toUser(UserEntity entity) {
        User user = new User(entity.getUsername());
        user.setSlothBadge(entity.isSlothBadge());
        user.setParrotBadge(entity.isParrotBadge());
        user.setJagBadge(entity.isJagBadge());
        return user;
    }

    /**
     * Normalizes a badge name to handle various input formats.
     * 
     * Converts the badge name to lowercase and removes underscores to support
     * multiple input formats (e.g., "sloth_badge", "SlothBadge", "slothbadge" all map to "slothbadge").
     * 
     * @param badgeName The raw badge name to normalize
     * @return The normalized badge name in lowercase without underscores, or empty string if badgeName is null
     */
    private String normalizeBadgeName(String badgeName) {
        if (badgeName == null) {
            return "";
        }

        return badgeName.toLowerCase(Locale.ROOT).replace("_", "");
    }
}