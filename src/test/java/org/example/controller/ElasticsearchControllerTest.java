package org.example.controller;

import org.example.facade.ElasticsearchFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.utils.TestUtils.productDocs;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ElasticsearchController.class)
public class ElasticsearchControllerTest {
    @MockBean
    ElasticsearchFacade elasticsearchFacade;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test indexing docs by activity and quantity")
    void indexProductsByActivityAndSkusAvailableQuantityTest() throws Exception {
        boolean activity = true;
        int availableQuantity = 100;
        String successfulMessage = "Successfully indexed";

        when(elasticsearchFacade.indexProductsByActivityAndSkusByAvailableQuantity(activity, availableQuantity))
                .thenReturn(successfulMessage);

        mockMvc.perform(post("/api/els/index/activity/quantity")
                        .param("active", Boolean.toString(activity))
                        .param("availableQuantity", Integer.toString(availableQuantity)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.message").value(successfulMessage));
    }

    @Test
    @DisplayName("Test searching for products by product name")
    void searchForProductsTest() throws Exception {
        String field = "productName";
        String value = "Wireless mouse";

        when(elasticsearchFacade.searchForProductsByParameters(field, value))
                .thenReturn(productDocs);

        mockMvc.perform(get("/api/els/search")
                        .param("field", field)
                        .param("value", value))
                .andExpectAll(status().isOk(),
                        jsonPath("$.tList[0].productName").value(productDocs.get(0).getProductName()));
    }
}
