package org.tedrosberihu.modernprocure.services;

import org.tedrosberihu.modernprocure.models.PermissionModel;
import org.tedrosberihu.modernprocure.security.AuthUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PermissionServices {
    Page<PermissionModel> getPermissions(AuthUserInfo user, Long branch, boolean active, String search, Pageable pageable);

    PermissionModel createPermission(AuthUserInfo user, PermissionModel permissionModel);

    PermissionModel updatePermission(AuthUserInfo user, Long userId, PermissionModel permissionModel);

    Boolean deletePermission(AuthUserInfo user, Long userId);
}
