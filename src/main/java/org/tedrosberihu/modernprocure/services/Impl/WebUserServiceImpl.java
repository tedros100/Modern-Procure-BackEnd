package org.tedrosberihu.modernprocure.services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.tedrosberihu.modernprocure.entities.WebUser;
import org.tedrosberihu.modernprocure.exceptions.ResourceNotFoundException;
import org.tedrosberihu.modernprocure.models.PermissionModel;
import org.tedrosberihu.modernprocure.models.WebUserModel;
import org.tedrosberihu.modernprocure.repository.WebUserRepository;
import org.tedrosberihu.modernprocure.security.AuthUserInfo;
import org.tedrosberihu.modernprocure.services.WebUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.openssl.PasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static org.tedrosberihu.modernprocure.common.Constants.RECORD_DOES_NOT_EXIST_FOR_ID;
import static org.tedrosberihu.modernprocure.specifications.WebUserSpecifications.withStatusActive;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebUserServiceImpl implements WebUserService {

    @NonNull
    private final WebUserRepository webUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<WebUserModel> getAllUsers(AuthUserInfo userInfo, Boolean active, Pageable pageable) {
        Specification<WebUser> webUserSpecification = Specification.where(withStatusActive(true));
        return webUserRepository.findAll(webUserSpecification, pageable).map(WebUserModel::new);
    }

    @Override
    public WebUserModel signup(WebUserModel webUserModel) {
        var webUser = new WebUser();
        webUser.setFirstName(webUserModel.getFirstName());
        webUser.setLastName(webUserModel.getLastName());
        webUser.setEmail(webUserModel.getEmail());

        // Fix: Encode the password from the webUserModel, not from webUser
        if (webUserModel.getPassword() == null || webUserModel.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        webUser.setPassword(passwordEncoder.encode(webUserModel.getPassword()));

        webUser.setActive(true);
        webUser.setPhoneNumber(webUserModel.getPhoneNumber());
        webUser.setStatus(WebUser.STATUS_ACTIVE);

        return new WebUserModel(webUserRepository.save(webUser));
    }


/*    @Override
    public WebUserModel signup(WebUserModel webUserModel) {
        var webUser = new WebUser();
        webUser.setFirstName(webUserModel.getFirstName());
        webUser.setLastName(webUserModel.getLastName());
        webUser.setEmail(webUserModel.getEmail());
        webUser.setPassword(passwordEncoder.encode(webUser.getPassword()));
        webUser.setActive(true);
        webUser.setPhoneNumber(webUserModel.getPhoneNumber());
        webUser.setStatus(WebUser.STATUS_ACTIVE);
        return new WebUserModel(webUserRepository.save(webUser));
    }*/

    @Override
    public WebUserModel getUserDetails(AuthUserInfo user) {
        return webUserRepository.findByWebIdAndActiveAndStatus(user.getWebId(), true, WebUser.STATUS_ACTIVE).map(webUser1 -> {
            WebUserModel webUserModel = new WebUserModel(webUser1);
            var permissions = webUserRepository.getPermissionForWebUser(webUser1).stream().map(webUserPermission -> {
                return new PermissionModel(webUserPermission, false);
            }).collect(Collectors.toList());
            webUserModel.setPermissions(permissions);
            return webUserModel;
        }).orElseThrow(() -> new ResourceNotFoundException(RECORD_DOES_NOT_EXIST_FOR_ID + user.getWebId()));
    }

    @Override
    public WebUserModel getUpdateDetails(AuthUserInfo user, WebUserModel webUserModel) {
        return webUserRepository.findByWebIdAndActiveAndStatus(user.getWebId(), true, WebUser.STATUS_ACTIVE).map(webUser -> {
            String[] names = webUserModel.getDescription().split(" ");
            if (names.length == 1) {
                webUser.setFirstName(webUserModel.getDescription());
            } else {
                webUser.setFirstName(names[0]);
                webUser.setLastName(names[1]);
            }
            webUser.setPhoneNumber(webUserModel.getPhoneNumber());
            webUser.setEmail(webUserModel.getEmail());
            var passwordMatched = passwordEncoder.matches(webUserModel.getPassword(), webUser.getPassword());
            if (webUserModel.isChangePassword() && passwordMatched) {
                var password = passwordEncoder.encode(webUserModel.getNewPassword());
                webUser.setPassword(password);
            } else try {
                throw new PasswordException("password not matched");
            } catch (PasswordException e) {
                throw new RuntimeException(e);
            }
            return new WebUserModel(webUserRepository.save(webUser));
        }).orElseThrow(() -> new ResourceNotFoundException(RECORD_DOES_NOT_EXIST_FOR_ID + webUserModel.getWebId()));
    }

}
