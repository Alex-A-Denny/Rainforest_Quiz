package rainforestapi.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import rainforestapi.model.User;

@Component
@Transactional
public class UserMySQLDAO implements UserDAO {

    private final UserRepository userRepository;

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
        List<UserEntity> entities = userRepository.findAll();
        List<User> users = new ArrayList<>(entities.size());

        for (UserEntity entity : entities) {
            users.add(toUser(entity));
        }

        return users.toArray(new User[0]);
    }

    @Override
    public User findUser(String username) throws IOException {
        Optional<UserEntity> entity = userRepository.findById(username);
        return entity.map(this::toUser).orElse(null);
    }

    @Override
    public User awardBadge(String username, String badgeName) throws IOException {
        Optional<UserEntity> entityOptional = userRepository.findById(username);
        if (entityOptional.isEmpty()) {
            return null;
        }

        UserEntity entity = entityOptional.get();
        String normalizedBadge = normalizeBadgeName(badgeName);

        switch (normalizedBadge) {
            case "slothbadge":
                entity.setSlothBadge(true);
                break;
            case "parrotbadge":
                entity.setParrotBadge(true);
                break;
            case "jagbadge":
                entity.setJagBadge(true);
                break;
            default:
                return null;
        }

        return toUser(userRepository.save(entity));
    }

    private UserEntity toEntity(User user) {
        return new UserEntity(
            user.getUsername(),
            user.isSlothBadge(),
            user.isParrotBadge(),
            user.isJagBadge()
        );
    }

    private User toUser(UserEntity entity) {
        User user = new User(entity.getUsername());
        user.setSlothBadge(entity.isSlothBadge());
        user.setParrotBadge(entity.isParrotBadge());
        user.setJagBadge(entity.isJagBadge());
        return user;
    }

    private String normalizeBadgeName(String badgeName) {
        if (badgeName == null) {
            return "";
        }

        return badgeName.toLowerCase(Locale.ROOT).replace("_", "");
    }
}