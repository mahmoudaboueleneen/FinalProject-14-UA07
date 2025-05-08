package com.ua07.users.services;

import com.ua07.users.models.User;
import com.ua07.users.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    public User updateUser(UUID id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setShippingAddress(updatedUser.getShippingAddress());
        existingUser.setBillingAddress(updatedUser.getBillingAddress());
        existingUser.setBusinessName(updatedUser.getBusinessName());
        existingUser.setBusinessEmail(updatedUser.getBusinessEmail());
        existingUser.setBusinessPhone(updatedUser.getBusinessPhone());
        existingUser.setTaxId(updatedUser.getTaxId());
        existingUser.setBusinessAddress(updatedUser.getBusinessAddress());
        existingUser.setWebsiteUrl(updatedUser.getWebsiteUrl());
        existingUser.setSupportContact(updatedUser.getSupportContact());
        existingUser.setDepartment(updatedUser.getDepartment());

        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}
