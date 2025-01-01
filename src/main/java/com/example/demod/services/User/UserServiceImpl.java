package com.example.demod.services.User;

import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demod.common.TokenUtil;
import com.example.demod.common.role;
import com.example.demod.dto.LoginDto;
import com.example.demod.dto.TokenDto;
import com.example.demod.dto.UserDto;
import com.example.demod.entities.Cart;
import com.example.demod.entities.Checkout;
import com.example.demod.entities.User;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.mapper.UserMapper;
import com.example.demod.repositories.CartRepository;
import com.example.demod.repositories.CheckoutRepository;
import com.example.demod.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    CheckoutRepository checkoutRepo;

    @Override
    public UserDto registerUser(UserDto user) throws EtAuthException {
        // Featch entered data
        User us = UserMapper.maptoToUser(user);
        // Input Validation
        Pattern ps = Pattern.compile("^(.+)@(.+)$");
        String em = us.getUserEmail();
        if (em != null)
            us.setUserEmail(em.toLowerCase());
        if (!ps.matcher(em).matches())
            throw new EtAuthException("Invalid email");

        // Unique email for each user
        Long count = userRepo.countUsersByEmail(us.getUserEmail());
        if (count > 0.0)
            throw new EtAuthException("This Email Already Exist");

        // Token for each user
        String token = TokenUtil.generateToken();
        us.setToken(token);
        User sv = userRepo.save(us);

        // Cart for each user
        Cart cart = new Cart();
        cart.setUserId(sv.getUser_id());
        cart.setNumberOfPieces(0);
        cart.setTotalPrice(0.0);
        cartRepo.save(cart);
        Hibernate.initialize(cart.getProducts());

        return UserMapper.mapToUserDto(sv);

    }

    @Transactional
    @Override
    public User loginUser(TokenDto user) throws EtAuthException {
        try {
            String em = user.getEmail();
            em = em.toLowerCase();
            User us = userRepo.findUsersByEmail(em);
            if (us == null)
                throw new EtAuthException("This email not exist, Register now!!");
            return us;
        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }

    }

    @Override
    public TokenDto getToken(LoginDto user) throws EtAuthException {
        try {
            // Get user and check if his/her email and password are correct
            User us = userRepo.findUsersByEmail(user.getEmail());
            if (us == null)
                throw new EtAuthException("This email not exist, Register now!!");
            if (!us.getUserPassword().equals(user.getPassword()))
                throw new EtAuthException("Email or Password is wrong!!");
            if (us.getUserRole().equals(role.valueOf("VENDOR"))) {
                if (!us.getFullName().equals(user.getBusinessName()))
                    throw new EtAuthException("Email or Password is wrong");
            }

            // Retrive token and email to log in with email
            TokenDto td = new TokenDto(
                    us.getToken(),
                    us.getUserEmail());
            return td;
        } catch (Exception e) {
            throw new EtAuthException("Api failed!!");

        }
    }

}
