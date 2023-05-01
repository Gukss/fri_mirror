package com.project.fri.room.repository;

import com.project.fri.room.entity.Category;
import com.project.fri.room.entity.Room;
import com.project.fri.room.entity.RoomCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.fri.room.repository fileName       : RoomCategoryRepository author
 *       : SSAFY date           : 2023-04-19 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-04-19        SSAFY       최초 생성
 */
public interface RoomCategoryRepository extends JpaRepository<RoomCategory, Long> {
  Optional<RoomCategory> findByCategory(Category category);  // category 변수로 roomCategory 엔티티에서 조회

}
