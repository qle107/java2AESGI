package org.esgi.cookmaster.database;
import java.util.Random;

public class ClientStat {
    public enum Type{
        INDIVIDUAL,
        CORPORATE,
        ORGANIZER
    }
    public enum Distribution{
        LOYAL,
        OCCASIONAL,
        NEW
    }
    private Type clientType;
    private Distribution clientDistribution;
    private int clientExpense;

    public ClientStat() {
        Type[] clientTypes = Type.values();
        int randomIndex = new Random().nextInt(clientTypes.length);
        this.clientType = clientTypes[randomIndex];

        Distribution[] clientDist = Distribution.values();
        randomIndex = new Random().nextInt(clientDist.length);
        this.clientDistribution = clientDist[randomIndex];

        randomIndex = new Random().nextInt(900);
        this.clientExpense = randomIndex+100;
    }

    public int getClientExpense() {
        return clientExpense;
    }

    public Distribution getClientDistribution() {
        return clientDistribution;
    }

    public Type getClientType() {
        return clientType;
    }

    @Override
    public String toString() {
        return "ClientStat{" +
                "clientType=" + clientType +
                ", clientDistribution=" + clientDistribution +
                ", clientExpense=" + clientExpense +
                '}';
    }
}
