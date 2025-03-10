package org.tedrosberihu.modernprocure.specifications;

import org.tedrosberihu.modernprocure.entities.TeamEntity;
import org.springframework.data.jpa.domain.Specification;


public interface TeamSpecification {

    static Specification<TeamEntity> withActive(Boolean active) {
        return (root, query, cb) -> cb.equal(root.get("active"), active);
    }

}
