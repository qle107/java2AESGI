package org.esgi.cookmaster.test;

import com.mysql.cj.xdevapi.Client;
import org.esgi.cookmaster.controller.GenerateClientStat;
import org.esgi.cookmaster.database.ClientStat;

import java.util.List;

public class ClientControllerTest {
    public static void main(String[] args) {
        GenerateClientStat test1 = new GenerateClientStat();
       List<ClientStat> test2 = test1.generateClients();
        System.out.println(test1.getTopExpenses(test2));
    }
}
