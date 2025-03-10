package org.tedrosberihu.modernprocure.services;


import org.tedrosberihu.modernprocure.models.WebUserModel;
import org.tedrosberihu.modernprocure.security.AuthUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WebUserService {
    WebUserModel signup(WebUserModel webUser);

    WebUserModel getUserDetails(AuthUserInfo user);

    WebUserModel getUpdateDetails(AuthUserInfo user, WebUserModel webUserModel);

    Page<WebUserModel> getAllUsers(AuthUserInfo userInfo, Boolean active, Pageable pageable);
}
