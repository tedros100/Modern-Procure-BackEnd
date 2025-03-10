package org.tedrosberihu.modernprocure.repository;

import org.tedrosberihu.modernprocure.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
