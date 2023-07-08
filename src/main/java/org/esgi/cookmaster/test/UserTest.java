package org.esgi.cookmaster.test;

import org.esgi.cookmaster.database.Subscription;
import org.esgi.cookmaster.database.User;

public class UserTest {
    public static void main(String[] args) {

        User test1 = new User();
        test1.defineUser("1");
        System.out.println(test1);
    }
}
