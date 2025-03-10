package org.tedrosberihu.modernprocure.repository;

import org.tedrosberihu.modernprocure.entities.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackerRepository extends JpaRepository<Tracker, Long> {

}
