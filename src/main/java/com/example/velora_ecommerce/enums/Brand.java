package com.example.velora_ecommerce.enums;

import lombok.*;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Brand {
    VERTEX("Vertex"),
    NIMBUS("Nimbus"),
    IRONCORE("IronCore"),
    ARCTIK("Arctik"),
    GALAGEAR("GalaGear"),
    GREENPEAK("GreenPeak"),
    NOVABYTE("Novabyte"),
    RESONA("Resona"),
    AURALIS("Auralis");

    private final String displayName;

    public static Optional<Brand> findBySearchQuery(String query) {
        String normalizedQuery = query.trim().toLowerCase();

        return Arrays.stream(Brand.values())
                .filter(brand -> normalizedQuery.contains(brand.getDisplayName().toLowerCase()))
                .findFirst();
    }
}