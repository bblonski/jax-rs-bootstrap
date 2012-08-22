package com.bootstrap;

import com.bootstrap.models.User;
import com.bootstrap.persistence.UserPersistence;
import org.junit.Test;

import javax.inject.Inject;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Unit tests for the UserService.
 */
public class UserPersistenceTest extends BasePersistenceTest {

    @Inject
    UserPersistence userPersistence;

    public UserPersistence getUserPersistence() {
        return userPersistence;
    }

    public void setUserPersistence(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    @Test
    public void testCreateUser() {
        User user = new User("Test", "User", "testuser@email.com", "testPassword");
        user = userPersistence.save(user);
        assertNotNull(user);
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("testPassword", user.getPassword());
        assertEquals("testuser@email.com", user.getEmail());
        assertEquals(1, user.getId());
        assertEquals(user, userPersistence.findByEmail("testuser@email.com"));
    }

}
