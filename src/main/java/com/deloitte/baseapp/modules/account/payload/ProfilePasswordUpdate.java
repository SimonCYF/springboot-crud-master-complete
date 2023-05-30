package com.deloitte.baseapp.modules.account.payload;

import lombok.Data;

@Data
public class ProfilePasswordUpdate {
    private Long id;
    private String password;
}
