package com.deloitte.baseapp.modules.authentication.payloads;

import lombok.Data;

@Data
public class ResetPasswordEmailCheck {
    private String email;
}
