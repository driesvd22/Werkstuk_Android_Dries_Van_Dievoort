package com.example.driesvandievoort.android;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.driesvandievoort.android.Database.AppDatabase;
import com.example.driesvandievoort.android.Entities.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserDaoInstrumentedTest {
    private static final String DATABASE_NAME = "app_test_db";
    public AppDatabase appDatabase;
    @Test
    public void InsertUser() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        appDatabase = Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        User user = new User();
        Random rand = new Random();
        int n = rand.nextInt(500) + 1;
        user.setUsername("testuser" + n);
        user.setPassword("dries");
        user.setPhoneNumber("02020202" + n);
        appDatabase.userDao().insertUser(user);
        User u = appDatabase.userDao().getUser(user.getUsername(), user.getPassword());
        assertEquals(user.getUsername(), u.getUsername());
        assertEquals(user.getPassword(), u.getPassword());
        assertEquals(user.getPhoneNumber(), u.getPhoneNumber());
    }
}
