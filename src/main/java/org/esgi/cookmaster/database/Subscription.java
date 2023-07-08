package org.esgi.cookmaster.database;

public class Subscription {
    private String id;
    private String name;
    private String price;
    private String currency;
    private String frequency;
    private String stripe_api_key;
    private String stripe_product_key;

    @Override
    public String toString() {
        return name;
    }

    public void defineSubscription(String id) {
        ConnectDatabase testDb = new ConnectDatabase();
        testDb.importConfig("src/config.cfg");
        testDb.connect();
        createSubscription(testDb.getSubscription(id));
    }

    public Subscription() {
    }

    protected Subscription(String id, String name, String price, String currency, String frequency, String stripe_api_key, String stripe_product_key) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.frequency = frequency;
        this.stripe_api_key = stripe_api_key;
        this.stripe_product_key = stripe_product_key;
    }

    protected void createSubscription(Subscription newSubscription) {
        this.id = newSubscription.id;
        this.name = newSubscription.name;
        this.price = newSubscription.price;
        this.currency = newSubscription.currency;
        this.frequency = newSubscription.frequency;
        this.stripe_api_key = newSubscription.stripe_api_key;
        this.stripe_product_key = newSubscription.stripe_product_key;
    }
}

