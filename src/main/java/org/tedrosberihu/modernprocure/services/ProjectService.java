package org.tedrosberihu.modernprocure.services;

import org.tedrosberihu.modernprocure.models.ProjectModel;

import java.util.List;

public interface ProjectService {
    ProjectModel addProject(ProjectModel projectmodel);

    List<ProjectModel> getAllProjects();

    ProjectModel getProjectWithId(Long projectId);

    ProjectModel updateProject(ProjectModel projectmodel, Long projectId);

    ProjectModel deleteProject(Long projectId);

}
