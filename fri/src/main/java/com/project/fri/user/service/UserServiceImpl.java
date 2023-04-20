package com.project.fri.user.service;

import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.project.fri.user.service fileName       : UserServiceImpl date           :
 * 2023-04-19 description    :
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User findById(long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
  }
}
