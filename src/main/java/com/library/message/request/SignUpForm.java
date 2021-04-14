package com.library.message.request;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

public class SignUpForm {

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 50)
    private String username;

    @Size(max = 60)
    @Email
    private String email;

    //private Set<String> role;

    private String[] roles;

    @Size(min = 6, max = 40)
    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   public String[] getRoles() {
        return roles;
   }

   public void setRoles(String[] roles) {
        this.roles = roles;
   }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
