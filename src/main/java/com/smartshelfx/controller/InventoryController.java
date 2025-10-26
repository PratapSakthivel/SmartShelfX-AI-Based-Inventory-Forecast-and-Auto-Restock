/* 
package com.smartshelfx.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshelfx.model.Product;
import com.smartshelfx.repository.ProductRepository;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    // Add a product
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Update a product by id
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product newProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    product.setQuantity(newProduct.getQuantity());
                    return productRepository.save(product);
                })
                .orElse(null);
    }

    // Delete a product by id
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return "Product deleted!";
        }
        return "Product not found!";
    }
}
*/
package com.smartshelfx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshelfx.model.Product;
import com.smartshelfx.repository.ProductRepository;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    // Create (Add) a product
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // Read (Get) all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Update a product by id
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product newProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    product.setQuantity(newProduct.getQuantity());
                    Product savedProduct = productRepository.save(product);
                    return ResponseEntity.ok(savedProduct);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted!");
        }
        return ResponseEntity.status(404).body("Product not found!");
    }
}
