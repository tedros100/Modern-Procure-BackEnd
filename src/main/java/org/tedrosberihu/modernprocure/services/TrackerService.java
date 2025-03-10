package org.tedrosberihu.modernprocure.services;

import org.tedrosberihu.modernprocure.models.TrackerModel;

import java.util.List;

public interface TrackerService {
    TrackerModel addTracker(TrackerModel trackermodel);

    List<TrackerModel> getAllTrackers();

    TrackerModel getTrackerWithId(Long trackerId);

    TrackerModel updateTracker(TrackerModel trackermodel, Long trackerId);

    TrackerModel deleteTracker(Long trackerId);

}
