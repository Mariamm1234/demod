package com.example.demod.services.Cart;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demod.dto.CartDto;
import com.example.demod.entities.Cart;
import com.example.demod.entities.Product;
import com.example.demod.entities.User;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.mapper.CartMapper;
import com.example.demod.repositories.CartRepository;
import com.example.demod.repositories.ProductRepository;
import com.example.demod.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    UserRepository userRepo;

    @Transactional
    @Override
    public CartDto addToCart(String email, Integer productId) {
        try {

            // Get user
            User us = userRepo.findUsersByEmail(email);
            if (us == null)
                throw new EtAuthException("Register now");

            // Get cart associated to this user
            Cart cr = cartRepo.findCartByUserId(us.getUser_id());
            if (cr == null) {
                cr = new Cart();
                cr.setUserId(us.getUser_id());
                Hibernate.initialize(cr.getProducts());

            }

            // Get product that requested to add to cart
            Product pr = productRepo.findProductByID(productId);
            if (pr == null)
                throw new EtAuthException("Modified by owner");

            // Product not in stock
            if (pr.getAmount().equals(0))
                throw new EtAuthException("Not in stock");

            // Add product to cart and manage number of pieces and price
            List<Product> prl = cr.getProducts();

            Double total = 0.0;
            if (prl.contains(pr)) {
                pr.setAmount(pr.getAmount() - 1);
                cr.setNumberOfPieces(cr.getNumberOfPieces() + 1);
                total += pr.getPrice();
                cr.setTotalPrice(cr.getTotalPrice() + total);
                Cart res = cartRepo.save(cr);
                productRepo.save(pr);
                return CartMapper.mapToCartDto(res);
            }
            total += pr.getPrice();
            pr.setAmount(pr.getAmount() - 1);
            prl.add(pr);
            cr.setProducts(prl);
            cr.setNumberOfPieces(cr.getNumberOfPieces() + 1);
            cr.setTotalPrice(cr.getTotalPrice() + total);
            Cart res = cartRepo.save(cr);

            // Convert the response to simpler one
            return CartMapper.mapToCartDto(res);

        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }

    }

    @Transactional
    @Override
    public Cart deleteFromCart(String email, Integer productId) throws EtAuthException {
        try {
            // Get user
            User us = userRepo.findUsersByEmail(email);
            if (us == null)
                throw new EtAuthException("Register now!!");

            // Get cart associated to this user
            Cart cr = cartRepo.findCartByUserId(us.getUser_id());

            if (cr == null)
                throw new EtAuthException("Register now!!");

            // If the cart is empty
            if (cr.getProducts().isEmpty())
                throw new EtAuthException("Nothing to remove");

            // Get product that requested to remove from cart
            Product pr = productRepo.findProductByID(productId);
            if (pr == null)
                throw new EtAuthException("Modified by owner");

            // Remove product and manage number of pieces and price
            pr.setAmount(pr.getAmount() + 1);
            cr.setNumberOfPieces(cr.getNumberOfPieces() - 1);
            cr.setTotalPrice(cr.getTotalPrice() - pr.getPrice());

            // Remove all products in cart
            if (cr.getNumberOfPieces().equals(0))
                cr.getProducts().clear();

            if (cr.getTotalPrice() < 0)
                cr.setTotalPrice(0.0);
            Cart res = cartRepo.save(cr);
            productRepo.save(pr);

            // Response
            return res;

        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }
    }

}
