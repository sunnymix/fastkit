package net.fastkit.core.common;

import com.fasterxml.jackson.core.type.TypeReference;
import net.fastkit.core.common.data.Gender;
import net.fastkit.core.common.data.Grade;
import net.fastkit.core.common.data.User;
import net.fastkit.core.common.data.UserList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sunnymix
 */
public class JsonTests {
    private static final User user1 = new User("user1", Gender.MALE, Grade.ONE);
    private static final String user1Json = "{\"name\":\"user1\",\"gender\":\"MALE\",\"grade\":1}";

    private static final User user2 = new User("user2", Gender.FEMALE, Grade.TWO);
    private static final String user2Json = "{\"name\":\"user2\",\"gender\":\"FEMALE\",\"grade\":2}";

    private static final List<User> users = new ArrayList<>() {{
        add(user1);
        add(user2);
    }};
    private static final String usersJson = "[" +
            user1Json + "," +
            user2Json +
            "]";

    private static final UserList userList = new UserList(users);
    private static final String userListJson = "{" +
            "\"users\":" + usersJson +
            "}";

    @Test
    void toJson() {
        assertEquals(Json.toJson(user1), user1Json);

        assertEquals(Json.toJson(user2), user2Json);

        assertEquals(Json.toJson(users), usersJson);

        assertEquals(Json.toJson(userList), userListJson);
    }

    @Test
    void fromJsonByTypeReference() {
        assertEquals(user1, Json.fromJson(user1Json, new TypeReference<User>() {
        }));

        assertEquals(user2, Json.fromJson(user2Json, new TypeReference<User>() {
        }));

        assertEquals(users, Json.fromJson(usersJson, new TypeReference<List<User>>() {
        }));

        assertEquals(userList, Json.fromJson(userListJson, new TypeReference<UserList>() {
        }));
    }

    @Test
    void fromJsonByClass() {
        assertEquals(user1, Json.fromJson(user1Json, User.class));

        assertEquals(user2, Json.fromJson(user2Json, User.class));

        assertEquals(userList, Json.fromJson(userListJson, UserList.class));
    }
}
