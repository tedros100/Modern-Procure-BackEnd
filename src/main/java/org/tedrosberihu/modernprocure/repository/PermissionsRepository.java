package org.tedrosberihu.modernprocure.repository;


import org.tedrosberihu.modernprocure.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionsRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
}
