package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sort {
    LOW_TO_HIGH("Sort by price: low to high"),
    HIGH_TO_LOW("Sort by price: high to low");

    private final String sortBy;
}
