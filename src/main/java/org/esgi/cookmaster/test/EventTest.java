package org.esgi.cookmaster.test;

import org.esgi.cookmaster.database.Event;
import org.esgi.cookmaster.database.User;

public class EventTest {
    public static void main(String[] args) {

        Event test1 = new Event();
        test1.defineEvent("1");
        System.out.println(test1);
    }
}
