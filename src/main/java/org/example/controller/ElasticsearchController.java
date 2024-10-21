package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.document.ProductDoc;
import org.example.dto.SearchResponse;
import org.example.dto.SuccessesIndexResponse;
import org.example.facade.ElasticsearchFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/els")
@Tag(name = "Elasticsearch methods", description = "Methods for interaction with elasticsearch")
public class ElasticsearchController {
    private final ElasticsearchFacade elasticsearchFacade;

    @PostMapping(value = "/index/activity/quantity", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Starting indexing for products by activity and skus by available quantity")
    public ResponseEntity<SuccessesIndexResponse> indexProductsByActivityAndSkusAvailableQuantity(
            @RequestParam @NotNull boolean active,
            @RequestParam @NotNull int availableQuantity) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessesIndexResponse(
                        elasticsearchFacade.indexProductsByActivityAndSkusByAvailableQuantity(active, availableQuantity)));
    }

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Searching for products by parameters")
    public ResponseEntity<SearchResponse<ProductDoc>> searchForProducts(@NotBlank String field,
                                                                        @NotBlank String value) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SearchResponse<>(elasticsearchFacade.searchForProductsByParameters(field, value)));
    }
}
