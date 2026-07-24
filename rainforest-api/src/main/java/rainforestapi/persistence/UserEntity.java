package rainforestapi.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    protected UserEntity() {
    }

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