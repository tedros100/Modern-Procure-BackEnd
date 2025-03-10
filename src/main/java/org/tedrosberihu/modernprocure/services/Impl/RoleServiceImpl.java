package org.tedrosberihu.modernprocure.services.Impl;


import org.tedrosberihu.modernprocure.entities.Permission;
import org.tedrosberihu.modernprocure.entities.Role;
import org.tedrosberihu.modernprocure.entities.RolePermission;
import org.tedrosberihu.modernprocure.exceptions.ResourceNotFoundException;
import org.tedrosberihu.modernprocure.models.RoleModel;
import org.tedrosberihu.modernprocure.models.RolePermissionModel;
import org.tedrosberihu.modernprocure.repository.PermissionsRepository;
import org.tedrosberihu.modernprocure.repository.RolePermissionRepository;
import org.tedrosberihu.modernprocure.repository.RoleRepository;
import org.tedrosberihu.modernprocure.security.AuthUserInfo;
import org.tedrosberihu.modernprocure.services.RoleServices;
import org.tedrosberihu.modernprocure.specifications.PermissionsSpecification;
import org.tedrosberihu.modernprocure.specifications.RoleSpecification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.tedrosberihu.modernprocure.common.Constants.*;
import static org.tedrosberihu.modernprocure.specifications.RoleSpecification.withSearch;


@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleServices {
    @NonNull RoleRepository roleRepository;
    @NonNull PermissionsRepository permissionsRepository;
    @NonNull RolePermissionRepository rolePermissionRepository;

    @Override
    public Page<RoleModel> getRoles(AuthUserInfo user, Long branch, boolean active, String search, Pageable pageable) {
        Specification<Role> roleSpecification = Specification.where(RoleSpecification.withActive(active));
        if (!StringUtils.isEmpty(search)) {
            roleSpecification = roleSpecification.and(withSearch("title", search)).or(withSearch("roleCode", search)).or(withSearch("externalKey", search)).or(withSearch("description", search));
        }
        return roleRepository.findAll(roleSpecification, pageable).map(RoleModel::new);
    }

    @Override
    public List<RolePermissionModel> getRolesAndPermission(AuthUserInfo user, Long webId) {
        Specification<Permission> permissionSpecification = Specification.where(PermissionsSpecification.withActive(true));
        var role = roleRepository.getById(webId);
        var permissions = permissionsRepository.findAll(permissionSpecification);
        return permissions.stream().map(permission -> {
            var newRolePermission = new RolePermissionModel();
            var doRoleExist = role.getPermissions().stream().anyMatch(rolePermission -> rolePermission.getWebId().equals(permission.getWebId()));
            newRolePermission.setPermission(permission.getWebId());
            newRolePermission.setPermissionCode(permission.getPermissionCode());
            newRolePermission.setPermissionTitle(permission.getTitle());
            newRolePermission.setPermissionUse(permission.getUse().toString());
            newRolePermission.setNotRolePermission(doRoleExist);
            var rolesPermission = role.getPermissions().stream().filter(permission1 -> permission1.getWebId().equals(permission.getWebId())).findFirst();
            if (doRoleExist && rolesPermission.isPresent()) {
                newRolePermission.setWebId(rolesPermission.get().getWebId());
            }
            return newRolePermission;
        }).collect(Collectors.toList());
    }

    @Override
    public RolePermissionModel addOrDeleteRolePermissions(AuthUserInfo user, Long webId, RolePermissionModel rolePermissionModel) {
        return Objects.equals(rolePermissionModel.getAddDelete(), OPERATION_ADD.toUpperCase()) ? addRolePermission(user, webId, rolePermissionModel) : Objects.equals(rolePermissionModel.getAddDelete(), OPERATION_DELETE.toUpperCase()) ? deleteRolePermission(user, webId, rolePermissionModel) : null;
    }

    private RolePermissionModel deleteRolePermission(AuthUserInfo user, Long webId, RolePermissionModel rolePermissionModel) {
        var rolePermissions = rolePermissionRepository.findAll();
        rolePermissions.forEach(rolePermission -> {
            if (rolePermission.getRole().getWebId().equals(webId) && rolePermission.getPermission().getWebId().equals(rolePermissionModel.getPermission())) {
                rolePermissionRepository.delete(rolePermission);
            }
        });
        return rolePermissionModel;
    }

    private RolePermissionModel addRolePermission(AuthUserInfo user, Long webId, RolePermissionModel rolePermissionModel) {
        return roleRepository.findById(webId).map(role -> {
            return permissionsRepository.findById(rolePermissionModel.getPermission()).map(permission -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permission);
                return new RolePermissionModel(rolePermissionRepository.save(rolePermission));
            }).orElseThrow();
        }).orElseThrow();
    }

    @Transactional
    @Override
    public RoleModel createRole(AuthUserInfo user, RoleModel roleModel) {
        var savedRole = roleRepository.save(roleModel.disassemble());
        roleModel.getPermissions().forEach(permission -> {
            permissionsRepository.findById(permission.getWebId()).map(savedPermission -> {
                var rolePermission = new RolePermission();
                rolePermission.setRole(savedRole);
                rolePermission.setPermission(savedPermission);
                return rolePermissionRepository.save(rolePermission);
            }).orElseThrow(() -> new ResourceNotFoundException(RECORD_DOES_NOT_EXIST_FOR_ID + permission.getWebId()));
        });
        return new RoleModel(savedRole);
    }

    @Transactional
    @Override
    public RoleModel updateRole(AuthUserInfo user, Long webId, RoleModel roleModel) {
        return roleRepository.findById(webId).map(role -> {
            role.setExternalKey(roleModel.getExternalKey());
            role.setRoleCode(roleModel.getRoleCode());
            role.setDescription(roleModel.getDescription());
            role.setTitle(roleModel.getTitle());
            role.setUse(roleModel.getUse());
            roleRepository.save(role);
            return roleModel;
        }).orElseThrow(() -> new ResourceNotFoundException(RECORD_DOES_NOT_EXIST_FOR_ID + webId));
    }

    @Override
    public Boolean deleteRole(AuthUserInfo user, Long webId) {
        return roleRepository.findById(webId).map(role -> {
            roleRepository.delete(role);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException(RECORD_DOES_NOT_EXIST_FOR_ID + webId));
    }
}
