package com.example.demod.shopify;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demod.entities.Product;
import com.example.demod.entities.User;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.repositories.ProductRepository;
import com.example.demod.repositories.UserRepository;
import com.example.demod.shopify.ShopifyProduct.Products;
import com.example.demod.shopify.ShopifyProduct.Products.Variant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ShopifyServiceImpl implements ShopifyService {
    private static final String SHOPIFY_API_URL = "https://vw190g-xz.myshopify.com/admin/api/2023-04/";
    private static final String ACCESS_TOKEN="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";
    // private static final String API_KEY="6331492cdc3ea5329d95c0291ecede89";
    // private static final String API_SECRET_KEY="8e3625386bd272244def1b42309c155a";
    // @Value("${shopify.access.token}")
    // private String accessToken;

    // @Value("${shopify.api.base-url}")
    // private String baseUrl;

    // @Value("${shopify.api.key}")
    // private String api_key;
    @Autowired
    UserRepository userRepo;

    @Autowired
    ProductRepository productRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    // Featch products
    @Override
    public String getProducts() {
        String url = SHOPIFY_API_URL + "products.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Shopify-Access-Token", ACCESS_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }

    // Product addition with json body
    @Override
    public String createProduct(String productJson) {
        String url = SHOPIFY_API_URL + "products.json";

        // Prepare headers for authentication
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Shopify-Access-Token", ACCESS_TOKEN);
        headers.set("Content-Type", "application/json");

        // Create HttpEntity with the request body and headers
        HttpEntity<String> entity = new HttpEntity<>(productJson, headers);

        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    }

    // Product addition with product body and userId in url
    @Override
    public ResponseEntity<String> addProduct(Product product, Integer userId) {
        try {
            String url = SHOPIFY_API_URL + "products.json";

            User user = userRepo.findUserById(userId); // Your own method to fetch user (vendor)

            // Prepare headers for authentication
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Shopify-Access-Token", ACCESS_TOKEN);

            // Data for response object
            ShopifyProduct.Products.Variant variant = new ShopifyProduct.Products.Variant();
            variant.setOption1("Default Title");
            variant.setPrice(product.getPrice().toString());
            variant.setSku("123456");

            ShopifyProduct.Products productt = new ShopifyProduct.Products();
            productt.setTitle(product.getProductName());
            productt.setBodyHtml(product.getDescription());
            productt.setVendor(user.getUserEmail());
            productt.setProductType(product.getProductType());
            productt.setTags(Arrays.asList("Custom", "Spring Boot"));
            productt.setVariants(Arrays.asList(variant)); // Set the list of variants
            // Reponse object
            ShopifyProduct shopify = new ShopifyProduct();
            shopify.setProduct(productt);

            // Convert object to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String productJson = objectMapper.writeValueAsString(shopify);

            // Create HttpEntity with the request body and headers
            HttpEntity<String> request = new HttpEntity<>(productJson, headers);

            // Create RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Send the POST request
            return restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        } catch (Exception e) {
            throw new EtAuthException("Error while adding product: " + e.getMessage());
        }
    }

    // Delete products
    @Override
    public ResponseEntity<String> deleteProduct(Long productId) {
        try {
            // Construct the delete URL
            String url = SHOPIFY_API_URL + "products/" + productId + ".json";

            // Set up the headers with the authentication token
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Shopify-Access-Token", ACCESS_TOKEN);

            // Create an empty HttpEntity with headers
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            // Make the DELETE request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity,
                    String.class);

            // Return the response from Shopify
            return response;

        } catch (Exception e) {
            throw new EtAuthException("Error deleting product: " + e.getMessage());
        }
    }

}
