package com.example.velora_ecommerce.enums;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Category {
    DESKTOPS(
        "Desktop PCs",
        List.of(
            "desktop",
            "desktop pc",
            "desktop computer",
            "computer tower",
            "pc tower"
        )
    ),
    LAPTOPS(
        "Laptops",
        List.of(
            "laptop",
            "notebook",
            "portable computer"
        )
    ),
    MONITORS(
        "Monitors",
        List.of(
            "monitor",
            "computer monitor",
            "display",
            "screen"
        )
    ),
    KEYBOARDS(
        "Keyboards",
        List.of(
            "keyboard",
            "mechanical keyboard",
            "gaming keyboard"
        )
    ),
    MICE(
        "Mice",
        List.of(
            "mouse",
            "computer mouse",
            "gaming mouse"
        )
    ),
    TABLETS(
        "Tablets",
        List.of(
            "tablet",
            "pad"
        )
    ),
    ACCESSORIES(
        "Accessories",
        List.of(
            "headphones",
            "earbuds",
            "earphones",
            "wired headphones",
            "hdmi",
            "hdmi cables",
            "speakers",
            "computer speakers",
            "wired speakers"
        )
    );

    private final String displayName;
    private final List<String> searchKeywords;

    public boolean matchesSearchQuery(String query) {
        String normalizedQuery = query
                .trim()
                .toLowerCase();

        return searchKeywords.stream()
                .anyMatch(keyword -> normalizedQuery.contains(keyword.toLowerCase()));
    }

    public static List<Category> findBySearchQuery(String query) {
        return Arrays.stream(Category.values())
                .filter(category -> category.matchesSearchQuery(query))
                .toList();
    }
}
