package org.example.otomotoclon.repository;

import org.example.otomotoclon.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query(nativeQuery = true, value = "SELECT count(*) from announcements where is_active is true")
    long countActiveAnnouncement();
    List<Announcement> findAnnouncementsByUserId(long userId);
    Optional<Announcement> findAnnouncementByCarId(long carId);
}
