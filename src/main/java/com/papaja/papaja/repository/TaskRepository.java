package com.papaja.papaja.repository;

import com.papaja.papaja.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
