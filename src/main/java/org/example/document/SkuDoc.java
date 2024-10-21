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
public class SkuDoc {
    private int skuId;

    private UUID productId;

    private int availableQuantity;
}
