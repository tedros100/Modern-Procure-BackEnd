package org.tedrosberihu.modernprocure.specifications;

import org.tedrosberihu.modernprocure.entities.WebUser;
import org.springframework.data.jpa.domain.Specification;

public interface WebUserSpecifications {
    static Specification<WebUser> withStatusActive(boolean statusActive) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"), statusActive);
    }
}
