package org.esgi.cookmaster.test;

import org.esgi.cookmaster.controller.SubscriptionController;
import org.esgi.cookmaster.database.ConnectDatabase;
import org.esgi.cookmaster.database.Subscription;

public class SubscriptionTest {
    public static void main(String[] args) {

        Subscription test1 = new Subscription();
        test1.defineSubscription("1");
        System.out.println(test1);
    }

}
