package com.bezkoder.spring.jpa.h2.repository;

import com.bezkoder.spring.jpa.h2.model.TutorialEntity;
import com.bezkoder.spring.jpa.h2.model.TutorialNonEntityPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TutorialRepository extends JpaRepository<TutorialEntity, Long> {
  List<TutorialEntity> findByPublished(boolean published);

  List<TutorialEntity> findByTitleContaining(String title);

  @Query(nativeQuery = true)
  TutorialNonEntityPOJO getTutorialFromTitleForPOJO(@Param("title") String title);
}
