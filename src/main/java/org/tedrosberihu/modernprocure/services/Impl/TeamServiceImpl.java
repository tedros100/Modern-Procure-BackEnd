package org.tedrosberihu.modernprocure.services.Impl;

import org.tedrosberihu.modernprocure.entities.TeamEntity;
import org.tedrosberihu.modernprocure.exceptions.ResourceNotFoundException;
import org.tedrosberihu.modernprocure.models.TeamModel;
import org.tedrosberihu.modernprocure.repository.TeamRepository;
import org.tedrosberihu.modernprocure.security.AuthUserInfo;
import org.tedrosberihu.modernprocure.services.TeamService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static org.tedrosberihu.modernprocure.common.Constants.RECORD_DOES_NOT_EXIST_FOR_ID;
import static org.tedrosberihu.modernprocure.specifications.TeamSpecification.withActive;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    @NonNull
    private TeamRepository teamRepository;

    @Override
    public TeamModel addAUser(AuthUserInfo user, TeamModel userModel) {
        var team = userModel.disassemble();
        team.setUserPassword(userModel.getUserPassword());
        return new TeamModel(teamRepository.save(team));
    }

    @Override
    public Page<TeamModel> getAllUsers(AuthUserInfo user, Pageable pageable) {
        Specification<TeamEntity> teamEntitySpecification = Specification.where(withActive(true));
        return teamRepository.findAll(teamEntitySpecification, pageable).map(TeamModel::new);
    }

    @Override
    public TeamModel getUserWithId(AuthUserInfo user, Long userId) {
        return teamRepository.findById((userId)).map(TeamModel::new).orElseThrow(() -> new ResourceNotFoundException(RECORD_DOES_NOT_EXIST_FOR_ID + userId));
    }

    @Override
    public TeamModel updateUser(AuthUserInfo user, TeamModel teamModel, Long userId) {
        return teamRepository.findById(userId).map(teamEntity -> {
            teamEntity.setFirstName(teamModel.getFirstName());
            teamEntity.setLastName(teamModel.getLastName());
            teamEntity.setPhoneNumber(teamModel.getPhoneNumber());
            teamEntity.setEmail(teamModel.getEmail());
            teamEntity.setRole(teamModel.getRole());
            teamEntity.setUserAddress(teamModel.getUserAddress());
            teamEntity.setUserPassword(teamModel.getUserPassword());
            teamEntity.setGender(teamModel.getGender());
            teamEntity.setActive(teamModel.isActive());
            return new TeamModel(teamRepository.save(teamEntity));
        }).orElseThrow(() -> new ResourceNotFoundException(RECORD_DOES_NOT_EXIST_FOR_ID + userId));
    }

    @Override
    public Boolean deleteUser(AuthUserInfo user, Long userId) {
        return teamRepository.findById(userId).map(teamEntity -> {
            teamRepository.delete(teamEntity);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException(RECORD_DOES_NOT_EXIST_FOR_ID + userId));
    }
}
