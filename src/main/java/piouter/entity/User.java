package piouter.entity;

import javax.persistence.*;
import java.util.*;

@Entity
public class User {

    @Id
    private String id;

    @ManyToMany
    private Set<User> following;

    protected User(){}

    public User(String id) {
        this.id = id;
        this.following = new HashSet<>();
    }

    public void addFollowing(User following){
        this.following.add(following);
    }

    public void removeFollowing(User following) {
        this.following.remove(following);
    }

    public String getId() {
        return id;
    }

    public Set<User> getFollowing() {
        return Collections.unmodifiableSet(following);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override

    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }

}
