package org.tedrosberihu.modernprocure.models;

import org.tedrosberihu.modernprocure.entities.WebUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long webId;
    private String description;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String newPassword;
    private String phoneNumber;
    private boolean changePassword;
    private Integer status;
    private List<String> featureGated = new ArrayList<>();
    private List<PermissionModel> permissions;

    public WebUserModel(WebUser webUser) {
        this.webId = webUser.getWebId();
        this.description = webUser.getFirstName() + " " + webUser.getLastName();
        this.firstName = webUser.getFirstName();
        this.lastName = webUser.getLastName();
        this.status = webUser.getStatus();
        this.email = webUser.getEmail();
        this.phoneNumber = webUser.getPhoneNumber();
        this.password = webUser.getPassword();
    }

}
