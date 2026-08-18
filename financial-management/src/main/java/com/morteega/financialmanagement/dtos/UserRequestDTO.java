package com.morteega.financialmanagement.dtos;

public class UserRequestDTO {
    private String email;

    public UserRequestDTO(){
        this.email=null;
    }
    public UserRequestDTO(String email){
        this.email=email;
    }
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
