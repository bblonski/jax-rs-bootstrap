package com.bootstrap;

import com.bootstrap.models.User;
import com.bootstrap.persistence.UserPersistence;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the UserService.
 */
public class UserPersistenceTest extends BasePersistenceTest {

    UserPersistence userPersistence = config.createUserPersistence(factory);

    @Test
    public void testCreateUser() {
        User user = new User("Test", "User", "testuser@email.com", "testPassword");
        user.setSalt(new byte[]{1,2,3,4});
        user = userPersistence.save(user);
        assertNotNull(user);
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("testPassword", user.getPassword());
        assertEquals("testuser@email.com", user.getEmail());
        assertEquals(1, user.getId());
        assertEquals(user, userPersistence.findByEmail("testuser@email.com"));
    }

    @Test
    public void testUserExists() {
        // Does not save database from previous tests
        assertEquals("user not persisted.", 0, userPersistence.findAll().size());
    }

}
