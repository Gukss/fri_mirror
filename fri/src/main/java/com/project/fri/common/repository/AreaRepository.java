package com.project.fri.common.repository;

import com.project.fri.common.entity.Area;
import com.project.fri.common.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.fri.common.repository fileName       : AreaRepository author
 * : hagnoykmik date           : 2023-04-19 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-04-19        hagnoykmik       최초 생성
 */
public interface AreaRepository extends JpaRepository<Area, Long> {
  //todo: optional로 감싸기
  Optional<Area> findByCategory(Category category);  // category 변수로 areaCategory 엔티티에서 조회
}
