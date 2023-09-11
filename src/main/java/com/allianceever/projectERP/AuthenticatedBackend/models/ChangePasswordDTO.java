package com.allianceever.projectERP.AuthenticatedBackend.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;

    public ChangePasswordDTO(){
        super();
    }

    public ChangePasswordDTO(String oldPassword, String newPassword){
        super();
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
