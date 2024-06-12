package com.tour_of_heroes.api.apps.shop.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour_of_heroes.api.controllers.ProductController;
import com.tour_of_heroes.api.shop.domain.contracts.services.ProductService;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import com.tour_of_heroes.api.shop.infrastructure.dto.InProductDTO;
import com.tour_of_heroes.api.shop.infrastructure.dto.OutProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService srv;

    private String urlTemplate;

    @BeforeEach
    void setUp() {
        urlTemplate = "/api/shop/products";
    }

    @Test
    void whenGetOneProduct_thenControlCorrectFlow() throws Exception {

        int id = 1;
        Product product = new Product(id, "ProductTest", 200.0, "ProductTestDescription");

        when(srv.getOne(anyInt())).thenReturn(Optional.of(product));

        mockMvc.perform(get(urlTemplate + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andDo(print());

        verify(srv, times(1)).getOne(anyInt());
    }

    @Test
    void whenGetAllProducts_thenControlCorrectFlow() throws Exception {

        List<Product> productList = List.of(

                new Product("Product1", "Product1Description", 100.0),
                new Product("Product2", "Product2Description", 100.0),
                new Product("Product3", "Product3Description", 100.0)
        );

        when(srv.getAll()).thenReturn(productList);

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());

        verify(srv, Mockito.times(1)).getAll();
    }

    @Test
    void whenCreateProduct_thenControlCorrectFlow() throws Exception {

        InProductDTO inProductDTO = new InProductDTO("ProductTest", 100.0, "ProductTestDescription");
        Product product = InProductDTO.from(inProductDTO);
        OutProductDTO outProductDTO = OutProductDTO.from(product);

        when(srv.add(any(Product.class))).thenReturn(product);

        mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inProductDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(outProductDTO.getId()))
                .andExpect(jsonPath("$.name").value(outProductDTO.getName()))
                .andExpect(jsonPath("$.price").value(outProductDTO.getPrice()))
                .andExpect(jsonPath("$.description").value(outProductDTO.getDescription()))
                .andDo(print());

        verify(srv, Mockito.times(1))
                .add(Mockito.any(Product.class));
    }

    @Test
    void whenUpdatingProduct_thenControlCorrectFlow() throws Exception {

        int id = 51;
        InProductDTO inProductDTO = new InProductDTO("ProductTest", 150.0, "ProductTestDescription");
        Product product = new Product(id, "ProductTest", 100.0, "ProductTestDescription");
        OutProductDTO outProductDTO = OutProductDTO.from(product);

        when(srv.modify(any(Product.class))).thenReturn(product);

        mockMvc.perform(patch(urlTemplate + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inProductDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(outProductDTO.getId()))
                .andExpect(jsonPath("$.name").value(outProductDTO.getName()))
                .andExpect(jsonPath("$.price").value(outProductDTO.getPrice()))
                .andExpect(jsonPath("$.description").value(outProductDTO.getDescription()))
                .andDo(print());

        verify(srv, Mockito.times(1))
                .modify(Mockito.any(Product.class));
    }

    @Test
    void whenRemoveProductById_thenControlCorrectFlow() throws Exception {

        int id = 1;
        Product product = new Product(id, "ProductTest", 100.0, "ProductTestDescription");

        when(srv.getOne(id)).thenReturn(Optional.of(product));
        doNothing().when(srv).deleteById(id);

        mockMvc.perform(delete(urlTemplate + "/{id}", id))
                .andExpect(status().isNoContent());

        verify(srv, Mockito.times(1)).getOne(id);
        verify(srv, Mockito.times(1)).deleteById(id);
    }
}