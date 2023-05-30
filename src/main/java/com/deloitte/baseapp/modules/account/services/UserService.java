package com.deloitte.baseapp.modules.account.services;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.commons.GenericService;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.exceptions.UserNotFoundException;
import com.deloitte.baseapp.modules.account.payload.ProfilePasswordUpdate;
import com.deloitte.baseapp.modules.account.payload.ProfileUpdate;
import com.deloitte.baseapp.modules.account.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserService extends GenericService<User> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public UserService(GenericRepository<User> repository) {
        super(repository);
    }

    /**
     *
     * @param email
     * @return
     * @throws UserNotFoundException
     */
    public User getProfileByEmail(final String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException();

        return optionalUser.get();
    }

    public User getProfileById(final Long id) throws UserNotFoundException{
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new UserNotFoundException();

        return optionalUser.get();
    }

    public User updateProfile(ProfileUpdate profileUpdate) throws UserNotFoundException{
        Optional<User> optionalUser = userRepository.findById(profileUpdate.getId());

        if (optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }else{
            User user = optionalUser.get();
            user.setUsername(profileUpdate.getUsername());
            user.setEmail(profileUpdate.getEmail());
            user.setAddress(profileUpdate.getAddress());
            user.setState(profileUpdate.getState());
            user.setCity(profileUpdate.getState());
            user.setPostcode(profileUpdate.getPostcode());
            user.setProfileUpdatedDate(new Date());

            return userRepository.save(user);
        }

    }


    public User updateProfilePic(final Long id, MultipartFile file) throws UserNotFoundException, IOException {
        Optional<User> optionalUser = userRepository.findById(id);

        byte[] fileContent = file.getBytes();
        String base64Encoded = Base64.encodeBase64String(fileContent);
        System.out.println("File"+fileContent);
        System.out.println("base64Encoded"+base64Encoded);

        if (optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }else{
            User user = optionalUser.get();
            user.setProfilePic(file.getBytes());
            user.setProfileUpdatedDate(new Date());
            return userRepository.save(user);
        }
    }

    public User updateProfilePassword(ProfilePasswordUpdate profilePasswordUpdate) throws UserNotFoundException {

        Optional<User> optionalUser = userRepository.findById(profilePasswordUpdate.getId());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        } else {
            User user = optionalUser.get();
            user.setPasswordUpdatedDate(new Date());
            user.setPassword(encoder.encode(profilePasswordUpdate.getPassword()));

            return userRepository.save(user);
        }
    }


    }
