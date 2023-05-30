package com.deloitte.baseapp.modules.authentication.payloads;

import lombok.Data;

@Data
public class ResetPassword {
    private String email;

    private String password;
}
