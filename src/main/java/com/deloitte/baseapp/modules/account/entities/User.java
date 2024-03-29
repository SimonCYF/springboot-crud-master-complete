package com.deloitte.baseapp.modules.account.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User implements GenericEntity<User> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @NotBlank
  @Size(max = 200)
  private String address;
  @NotBlank
  @Size(max = 35)
  private String city;
  @NotBlank
  @Size(max = 35)
  private String state;

  private Integer postcode;

  @Lob
  private byte[] profilePic;

  private Date createdDate;
  private Date profileUpdatedDate;
  private Date passwordUpdatedDate;

  @PrePersist
  private void prePersist() {
    createdDate = new Date();
    passwordUpdatedDate = new Date();
  }

  @PreUpdate
  private void preUpdate() {
    if (isProfileUpdated()) {
      profileUpdatedDate = new Date();
      //passwordUpdatedDate = null; // Reset passwordUpdatedDate if profile is updated
    }

    if (isPasswordUpdated()) {
      passwordUpdatedDate = new Date();
      //profileUpdatedDate = null; // Reset profileUpdatedDate if password is updated
    }
  }

  private boolean isProfileUpdated() {
    if(profileUpdatedDate == null){
      return false;
    }else{
      return true;
    }
  }

  private boolean isPasswordUpdated() {
    if(passwordUpdatedDate == null){
      return false;
    }else{
      return true;
    }
  }




  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public User(String username, String email, String password, String address, String city, String state, Integer postcode) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.address = address;
    this.city = city;
    this.state = state;
    this.postcode = postcode;
  }

  @Override
  public void update(User source) {

  }

  public Long getId() {
    return id;
  }

  @Override
  public User createNewInstance() {
    return null;
  }
//  public void setId(Long id) {
//    this.id = id;
//  }
//
//  public String getUsername() {
//    return username;
//  }
//
//  public void setUsername(String username) {
//    this.username = username;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  public void setEmail(String email) {
//    this.email = email;
//  }
//
//  public String getPassword() {
//    return password;
//  }
//
//  public void setPassword(String password) {
//    this.password = password;
//  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
