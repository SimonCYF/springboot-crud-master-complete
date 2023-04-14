package com.deloitte.baseapp.modules.account.payload;

import lombok.Data;

@Data
public class ProfileUpdate {

    private Long id;

    private String username;

    private String email;

    private String address;

    private String city;

    private String state;

    private Integer postcode;
}
