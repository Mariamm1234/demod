package com.example.demod.services.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demod.common.role;
import com.example.demod.dto.ProductDto;
import com.example.demod.dto.ProductRequestDto;
import com.example.demod.entities.Product;
import com.example.demod.entities.User;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.mapper.ProductMapper;
import com.example.demod.repositories.ProductRepository;
import com.example.demod.repositories.UserRepository;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepo;
    UserRepository userRepo;

    @Override
    public ArrayList<ProductDto> getProducts() throws EtAuthException {
        try {

            // Featch all products
            List<Product> pr = productRepo.findAll();
            ArrayList<ProductDto> prd = new ArrayList<>();
            for (int i = 0; i < pr.size(); i++) {
                prd.add(ProductMapper.mapToProductDto(pr.get(i)));
            }
            if (prd.isEmpty())
                throw new EtAuthException("No products");
            return prd;
        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public ProductDto addProduct(Product product, String email) throws EtAuthException {
        try {

            Product pr = product;
            // if no product entered
            if (pr == null)
                throw new EtAuthException("No product entered");

            // Get user --> VENDOR one
            User us = userRepo.findUsersByEmail(email);
            // if user not authorized
            if (!us.getUserRole().equals(role.VENDOR))
                throw new EtAuthException("unotherized");

            /*
             * If the product is existed --> no addition
             * if the product not added --> add it with its vendor id
             */

            Product sp = productRepo.findProductByID(pr.getProductId());
            ProductDto res = ProductMapper.mapToProductDto(pr);
            if (sp == null) {
                pr.setVendorId(us.getUser_id());
                Product np = productRepo.save(pr);
                res = ProductMapper.mapToProductDto(np);
                return res;
            }
            res.setMessage("Already added");

            return res;
        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }

    }

    @Override
    public String deleteProduct(String email, Integer productId) throws EtAuthException {
        // Get user
        User us = userRepo.findUsersByEmail(email);
        if (us == null)
            throw new EtAuthException("Not exist, Register now!!");

        // Get product
        Product pr = productRepo.findProductByID(productId);
        if (pr == null)
            throw new EtAuthException("Not existed product");
        // If the user [VENDOR] is the same owner of the product --> delete product
        if (pr.getVendorId().equals(us.getUser_id())) {
            productRepo.delete(pr);
            return "Product is deleted";
        }

        return "you're not selling this product any more";
    }

    @Override
    public Product editProduct(Product proudct, Integer productId) throws EtAuthException {
        try {
            // Get product
            Product pr = productRepo.findProductByID(productId);
            if (pr == null)
                throw new EtAuthException("this product not exist");

            // Edit it
            pr.setAmount(proudct.getAmount());
            pr.setDescription(proudct.getDescription());
            pr.setPrice(proudct.getPrice());
            pr.setProductName(proudct.getProductName());
            pr.setProductType(proudct.getProductType());

            return productRepo.save(pr);

        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }

    }

}
