package org.tedrosberihu.modernprocure.services;

import org.tedrosberihu.modernprocure.models.TeamModel;
import org.tedrosberihu.modernprocure.security.AuthUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {
    TeamModel addAUser(AuthUserInfo user, TeamModel userModel);

    Page<TeamModel> getAllUsers(AuthUserInfo user, Pageable pageable);

    TeamModel getUserWithId(AuthUserInfo user, Long userId);

    TeamModel updateUser(AuthUserInfo user, TeamModel userModel, Long userId);

    Boolean deleteUser(AuthUserInfo user, Long userId);

}
