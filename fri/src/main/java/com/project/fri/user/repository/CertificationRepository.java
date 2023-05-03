package com.project.fri.user.repository;

import com.project.fri.user.entity.Certification;
import com.project.fri.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.fri.user.repository fileName       : CertificationRepository date
 * : 2023-05-02 description    :
 */
public interface CertificationRepository extends JpaRepository<Certification, Long> {

  Optional<Certification> findByEmail(String email);
}
