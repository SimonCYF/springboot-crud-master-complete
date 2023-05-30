package com.deloitte.baseapp.modules.account.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.configs.swagger.ReadApiResponse;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.exceptions.UserNotFoundException;
import com.deloitte.baseapp.modules.account.payload.ProfilePasswordUpdate;
import com.deloitte.baseapp.modules.account.payload.ProfileUpdate;
import com.deloitte.baseapp.modules.account.services.UserService;
import com.deloitte.baseapp.configs.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class MyAccountController {

    @Autowired
    UserService userService;

    @Operation(tags = "Account", summary = "Get Account Details", description = "Get Account Complete Details Using Email")
    @GetMapping("/getprofile/{email}")
    @ReadApiResponse
    @Transactional
    public MessageResponse<?> getMyProfile(@PathVariable final String email ) {
        try {
            //final UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final User user = userService.getProfileByEmail(email);

            return new MessageResponse<>(user, "Success");
        } catch (final UserNotFoundException ex) {
            return new MessageResponse<>(null, ex.getMessage());
        }
    }

    @ReadApiResponse
    @Operation(tags = "Account", summary = "Update Profile", description = "Update Profile Details")
    @PostMapping("/updateprofile")
    public MessageResponse<?> updateProfile(@RequestBody ProfileUpdate profileUpdate){
        try{
            final User user = userService.updateProfile(profileUpdate);
            return new MessageResponse<>(user, "Success");

        }catch (UserNotFoundException e){
            return new MessageResponse<>(e.getMessage());
        }
    }

    @ReadApiResponse
    @PostMapping("/updateprofilepic/{id}")
    @Operation(tags = "Account", summary = "Update Profile", description = "Update Profile Picture")

    public MessageResponse<?> updateProfilePic(@PathVariable final Long id, @RequestParam("file") MultipartFile file){
        try{
            final User user = userService.updateProfilePic(id, file);
            return new MessageResponse<>(user, "Successfully Uploaded");

        }catch (Exception e){
            e.printStackTrace();
            return new MessageResponse<>(e.getMessage());
        }
    }

    @ReadApiResponse
    @PostMapping("/updateprofile/password")
    @Operation(tags = "Account", summary = "Update Profile Password", description = "Update Profile Password")
    public MessageResponse<?> updateProfilePassword(@Valid @RequestBody ProfilePasswordUpdate profilePasswordUpdate){
        try{

            final User user = userService.updateProfilePassword(profilePasswordUpdate);
            return new MessageResponse<>(user, "Password Successfully Updated");

        }catch (Exception e){
            return new MessageResponse<>(e.getMessage());
        }


    }

}
