package com.example.driesvandievoort.android;

import com.example.driesvandievoort.android.Entities.Category;
import com.example.driesvandievoort.android.Entities.User;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserAndCategoryUnitTest {
    @Test
    public void UserGetterSetter() {
        User user = new User();
        user.setUser_id(1);
        user.setUsername("dries");
        user.setPassword("driez");
        user.setPhoneNumber("02313121");
        assertEquals(1, user.getUser_id());
        assertEquals("dries", user.getUsername());
        assertEquals("driez", user.getPassword());
        assertEquals("02313121", user.getPhoneNumber());
    }

    @Test
    public void CategoryGetterSetter() {
        Category category = new Category();
        category.setName("Dries");
        category.setImage(R.drawable.chinese);
        assertEquals("Dries", category.getName());
        assertEquals(R.drawable.chinese, category.getImage());
    }
}