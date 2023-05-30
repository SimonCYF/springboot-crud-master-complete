package com.deloitte.baseapp.modules.authentication.controllers;

import com.deloitte.baseapp.commons.JwtResponse;
import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.authentication.exception.BadCredentialException;
import com.deloitte.baseapp.modules.authentication.exception.EmailHasBeenUsedException;
import com.deloitte.baseapp.modules.authentication.payloads.ResetPassword;
import com.deloitte.baseapp.modules.authentication.payloads.ResetPasswordEmailCheck;
import com.deloitte.baseapp.modules.authentication.payloads.SigninRequest;
import com.deloitte.baseapp.modules.authentication.payloads.SignupRequest;
import com.deloitte.baseapp.modules.authentication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public MessageResponse<?> signup(@Valid @RequestBody SignupRequest payload) {
        try {
            User user = authenticationService.signup(payload);
            return new MessageResponse<>(user);
        } catch (final EmailHasBeenUsedException ex) {
            return new MessageResponse<>(ex.getMessage());
        }
    }

    @PostMapping("/signin")
    public MessageResponse<?> signin(@Valid @RequestBody SigninRequest payload) {
        try {
            final JwtResponse jwt = authenticationService.signin(payload);
            return new MessageResponse<>(jwt);
        } catch (final BadCredentialException ex) {
            return new MessageResponse<>(ex.getMessage());
        }
    }

    @PostMapping("/forgetpassemailcheck")
    public MessageResponse<?> forgetPassword(@Valid @RequestBody ResetPasswordEmailCheck payload) {
        try {

            return new MessageResponse<>(authenticationService.resetPasswordEmailCheck(payload.getEmail()), "Proceeding To Next Page ");
        } catch (Exception ex) {
            return new MessageResponse<>(ex.getMessage());
        }
    }

    @PostMapping("/resetpass")
    public MessageResponse<?> forgetPassword(@Valid @RequestBody ResetPassword payload) {
        try {
            boolean checkExitsByEmail = authenticationService.resetPasswordEmailCheck(payload.getEmail());

            if(checkExitsByEmail == true){
                return new MessageResponse<>(authenticationService.resetPassword(payload), "Proceeding To Login ");
            }else{
                return new MessageResponse<>(authenticationService.resetPassword(payload), "Error ");
            }
        } catch (Exception ex) {
            return new MessageResponse<>(ex.getMessage());
        }
    }

}
