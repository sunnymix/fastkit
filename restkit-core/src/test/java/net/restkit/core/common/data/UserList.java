package net.restkit.core.common.data;

import java.util.List;
import java.util.Objects;

/**
 * @author sunnymix
 */
public class UserList {
    private List<User> users;

    public UserList() {
    }

    public UserList(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserList userList = (UserList) o;
        return Objects.equals(users, userList.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users);
    }

    @Override
    public String toString() {
        return "UserList{" +
                "users=" + users +
                '}';
    }
}
