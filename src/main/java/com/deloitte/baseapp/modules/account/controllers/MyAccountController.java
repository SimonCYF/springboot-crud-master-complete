package com.deloitte.baseapp.modules.account.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.exceptions.UserNotFoundException;
import com.deloitte.baseapp.modules.account.payload.ProfileUpdate;
import com.deloitte.baseapp.modules.account.services.UserService;
import com.deloitte.baseapp.configs.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/my-account")
public class MyAccountController {

    @Autowired
    UserService userService;

    @GetMapping("/getprofile/{email}")
    public MessageResponse<?> getMyProfile(@PathVariable final String email ) {
        try {
            //final UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final User user = userService.getProfileByEmail(email);

            return new MessageResponse<>(user, "Success");
        } catch (final UserNotFoundException ex) {
            return new MessageResponse<>(null, ex.getMessage());
        }
    }

    @PostMapping("/updateprofile")
    public MessageResponse<?> updateProfile(@RequestBody ProfileUpdate profileUpdate){
        try{
            final User user = userService.updateProfile(profileUpdate);
            return new MessageResponse<>(user, "Success");

        }catch (UserNotFoundException e){
            return new MessageResponse<>(e.getMessage());
        }
    }

    @PostMapping("/updateprofilepic/{id}")
    public MessageResponse<?> updateProfilePic(@PathVariable final Long id, @RequestParam("file") MultipartFile file){
        try{
            final User user = userService.updateProfilePic(id, file);
            return new MessageResponse<>(user, "Successfully Uploaded");

        }catch (Exception e){
            e.printStackTrace();
            return new MessageResponse<>(e.getMessage());
        }
    }

}
