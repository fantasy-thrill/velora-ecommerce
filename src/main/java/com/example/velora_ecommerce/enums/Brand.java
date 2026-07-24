package com.example.velora_ecommerce.enums;

public enum Brand {
    VERTEX("Vertex"),
    NIMBUS("Nimbus"),
    IRONCORE("IronCore"),
    ARCTIK("Arctik"),
    GALAGEAR("GalaGear"),
    GREENPEAK("GreenPeak"),
    NOVABYTE("Novabyte");

    private final String displayName;

    Brand(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}