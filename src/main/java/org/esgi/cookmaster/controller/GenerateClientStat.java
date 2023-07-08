package org.esgi.cookmaster.controller;

import com.mysql.cj.xdevapi.Client;
import org.esgi.cookmaster.database.ClientStat;
import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;

public class GenerateClientStat {
    public List<ClientStat> generateClients(){
        List<ClientStat> storedClient = new ArrayList<>();

        for (int i = 0; i < 30; i++){
            ClientStat fakeClient = new ClientStat();
            storedClient.add(fakeClient);
        }
        return storedClient;
    }

    public List<ClientStat> getTopExpenses(List<ClientStat> clients){
        List<ClientStat> storedTopClients = new ArrayList<>(clients);
        Collections.sort(storedTopClients,Comparator.comparingInt(ClientStat::getClientExpense).reversed());
        return storedTopClients.subList(0,Math.min(5,storedTopClients.size()));
    }
}
