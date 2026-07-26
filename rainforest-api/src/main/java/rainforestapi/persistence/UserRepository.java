package rainforestapi.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for User entities.
 * 
 * Provides both standard CRUD operations and custom SQL queries for managing
 * User entities in the MySQL database.
 * 
 * Custom query methods use explicit SQL to demonstrate direct database access.
 * 
 * @author Alex Denny
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
    
    /**
     * Retrieves all users from the database using explicit SQL.
     * 
     * @return List of all UserEntity objects
     */
    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> findAllUsers();
    
    /**
     * Finds a user by username using explicit SQL.
     * 
     * @param username The username to search for
     * @return Optional containing the user if found
     */
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    Optional<UserEntity> findUserByUsername(@Param("username") String username);
    
    /**
     * Awards the sloth badge to a user using explicit SQL UPDATE.
     * 
     * @param username The username of the user to award the badge to
     * @return The number of rows updated
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE UserEntity u SET u.slothBadge = true WHERE u.username = :username")
    int awardSlothBadge(@Param("username") String username);
    
    /**
     * Awards the parrot badge to a user using explicit SQL UPDATE.
     * 
     * @param username The username of the user to award the badge to
     * @return The number of rows updated
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE UserEntity u SET u.parrotBadge = true WHERE u.username = :username")
    int awardParrotBadge(@Param("username") String username);
    
    /**
     * Awards the jaguar badge to a user using explicit SQL UPDATE.
     * 
     * @param username The username of the user to award the badge to
     * @return The number of rows updated
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE UserEntity u SET u.jagBadge = true WHERE u.username = :username")
    int awardJaguarBadge(@Param("username") String username);
}