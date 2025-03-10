package org.tedrosberihu.modernprocure.services;


import org.tedrosberihu.modernprocure.models.RoleModel;
import org.tedrosberihu.modernprocure.models.RolePermissionModel;
import org.tedrosberihu.modernprocure.security.AuthUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleServices {
    Page<RoleModel> getRoles(AuthUserInfo user, Long branch, boolean active, String search, Pageable pageable);

    List<RolePermissionModel> getRolesAndPermission(AuthUserInfo user, Long webId);

    RolePermissionModel addOrDeleteRolePermissions(AuthUserInfo user, Long webId, RolePermissionModel rolePermissionModel);

    RoleModel createRole(AuthUserInfo user, RoleModel roleModel);

    RoleModel updateRole(AuthUserInfo user, Long userId, RoleModel roleModel);

    Boolean deleteRole(AuthUserInfo user, Long userId);
}
