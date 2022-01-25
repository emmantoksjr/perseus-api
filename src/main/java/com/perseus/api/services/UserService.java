package com.perseus.api.services;

import com.perseus.api.dto.requests.AddEmailOrPhoneRequest;
import com.perseus.api.dto.response.CustomResponse;
import com.perseus.api.models.Email;
import com.perseus.api.models.PhoneNumber;
import com.perseus.api.models.User;
import com.perseus.api.repositories.EmailRepository;
import com.perseus.api.repositories.PhoneNumberRepository;
import com.perseus.api.repositories.UserRepository;
import com.perseus.api.dto.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private final EmailRepository emailRepository;

    //TODO: More method validations
    //TODO: Include proper logs for all methods

    public ResponseEntity<Object> createUser(RegisterRequest request) {
        log.info("User started registration flow");

        User user = User.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .build();

        User result = this.saveUserDetails(user, request.getPhoneNumbers(), request.getEmails());

        CustomResponse customResponse = new CustomResponse("Success","User Created Successfully", null, result);

        return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getUser(
            @Nullable Long id,
            @Nullable String name
    ) {
        if (id == null && name == null) {
            CustomResponse customResponse = new CustomResponse("Error","User Id or Name (Firstname or Lastname) is required", null, null);

            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }

        User user = (id != null)
                ? userRepository.findById(id).get()
                : userRepository.findByName(name);

        CustomResponse customResponse = new CustomResponse("Success","User Fetched Successfully", null, user);

        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    public ResponseEntity<Object> addEmailOrPhoneNumber(AddEmailOrPhoneRequest request) {
        User user = userRepository.findById(request.getUserId()).get();

        User result = this.saveUserDetails(user, request.getPhoneNumbers(), request.getEmails());

        CustomResponse customResponse = new CustomResponse("Success","User Updated Successfully", null, result);

        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            CustomResponse customResponse = new CustomResponse("Error","An error occurred while deleting user record!", null, null);
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);

        CustomResponse customResponse = new CustomResponse("Success","Successfully deleted user record.", null, null);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateUserPhone(Long userId, Long phoneId, String number) {
        PhoneNumber phone = phoneNumberRepository.findById(phoneId).get();

        if (phone.getUser().getId() != userId ) {
            CustomResponse customResponse = new CustomResponse("Error", "Resource not found", null, null);
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }

        phone.setNumber(number);
        PhoneNumber savedNumber = phoneNumberRepository.save(phone);

        CustomResponse customResponse = new CustomResponse("Success","Successfully updated user phone", null, savedNumber);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateUserEmail(Long userId, Long emailId, String mail) {
        Email email = emailRepository.findById(emailId).get();

        if (email.getUser().getId() != userId ) {
            CustomResponse customResponse = new CustomResponse("Error","Resource not found.", null, null);
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }

        email.setMail(mail);
        Email savedEmail = emailRepository.save(email);

        CustomResponse customResponse = new CustomResponse("Succeess","Successfully updated user email", null, savedEmail);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    /**
     * This is used to set user relationships for PhoneNumber and Emails
     *
     * @param user
     * @param phoneNumbers
     * @param emails
     * @return User
     */
    private User saveUserDetails(
            User user,
            List<PhoneNumber> phoneNumbers,
            List<Email> emails
    ) {
        user.setPhoneNumbers(phoneNumbers);
        user.setEmails(emails);

        for (PhoneNumber number : user.getPhoneNumbers()) {
            number.setUser(user);
        }

        for (Email email : user.getEmails()) {
            email.setUser(user);
        }

       return userRepository.save(user);
    }
}
