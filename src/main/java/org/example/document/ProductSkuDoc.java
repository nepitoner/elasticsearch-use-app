package org.example.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductSkuDoc {
    private UUID productId;

    private String productName;

    private String description;

    private String category;

    private double price;

    private boolean active;

    private int skuId;

    private int availableQuantity;
}
