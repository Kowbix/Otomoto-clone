package org.example.otomotoclon.repository;

import lombok.Data;
import org.example.otomotoclon.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query(nativeQuery = true, value = "SELECT count(*) from announcements where is_active is true")
    long countActiveAnnouncement();

    @Query("SELECT a FROM Announcement a JOIN a.user u WHERE a.isActive = :isActive AND u.username = :username")
    List<Announcement> findByIsActiveAndUserUsername(@Param("isActive") boolean isActive, @Param("username")  String username);
    Optional<Announcement> findAnnouncementByCarId(long carId);

    @Query("SELECT a FROM Announcement a WHERE a.isActive = true AND a.addedDate = :date AND a.car.brand.name = :brand AND  a.car.model.name = :model AND a.car.generation.name = :generation")
    List<Announcement> findActiveByAddedDateAndCarInfo(Date date, String brand, String model, String generation);

    @Query("SELECT a FROM Announcement a WHERE a.isActive = true AND a.addedDate = :date AND a.car.brand.name = :brand AND  a.car.model.name = :model")
    List<Announcement> findActiveByAddedDateAndCarInfo(Date date, String brand, String model);
}
