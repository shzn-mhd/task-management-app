package com.xrontech.task.domain.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findAllByUserId(Long id);

    Optional<Task> findByIdAndUserId(Long id, Long userId);
}
