package com.tour_of_heroes.api.apps.shop.context;

import com.tour_of_heroes.api.shop.domain.contracts.repositories.ProductRepository;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    private Product product;

    @BeforeEach
    public void setupTestData() {

        product = new Product(1, "Product", 150.0, "Product Description");
    }

    @Test
    @DisplayName("Test for save product operation")
    public void givenProductObject_whenSave_thenReturnSaveProduct() {

        Product saveProduct = repository.save(product);

        assertThat(saveProduct).isNotNull();
        assertThat(saveProduct.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("test for get Product List")
    public void givenProductList_whenFindAll_thenProductList() {

        Product productOne = new Product(2, "Product1", 150.0, "Product1 Description");
        Product productTwo = new Product(3, "Product2", 200.0, "Product2 Description");

        repository.save(productOne);
        repository.save(productTwo);

        // When : Action of behavious that we are going to test
        List<Product> productList = repository.findAll();

        // Then : Verify the output
        assertThat(productList).isNotEmpty();
        assertThat(productList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("test for get Product By Id")
    public void givenProductObject_whenFindById_thenReturnProductObject() {
        // Given : Setup object or precondition
        product = repository.save(product);

        // When : Action of behavious that we are going to test
        Product getProduct = repository.findById(product.getId()).get();

        // Then : Verify the output
        assertThat(getProduct).isNotNull();
    }

    @Test
    @DisplayName("test for get Product update operation")
    public void givenProductObject_whenUpdate_thenProductObject() {

        // Given: Setup object or precondition
        product = repository.save(product);

        // When: Action or behavior that we are going to test
        Product getProduct = repository.findById(product.getId()).get();

        String updatedName = "Product X";
        getProduct.setName(updatedName);

        Product updatedProduct = repository.save(getProduct);

        // Then: Verify the output or expected result
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo(updatedName);
    }

    @Test
    @DisplayName("test for delete Product operation")
    public void givenProductObject_whenDelete_thenRemoveProduct() {

        product = repository.save(product);

        repository.deleteById(product.getId());
        Optional<Product> deleteProduct = repository.findById(product.getId());

        assertThat(deleteProduct).isEmpty();
    }
}