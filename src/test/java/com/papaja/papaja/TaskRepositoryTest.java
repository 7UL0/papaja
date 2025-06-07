package com.papaja.papaja;

import com.papaja.papaja.model.Task;
import com.papaja.papaja.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository repo;

    @Test
    void saveAndFind() {
        Task t = new Task();
        t.setTitle("Test");
        t.setDone(false);
        Task saved = repo.save(t);

        assertThat(repo.findById(saved.getId())).isPresent();
    }
}
