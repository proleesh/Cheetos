package org.proleesh.cheetos.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;

    @GetMapping
    public List<TaskItem> getTasks(){
        return taskRepository.findAll();
    }

    @PostMapping("/add")
    public TaskItem addTask(@Valid @RequestBody TaskItem taskItem) {
        return taskRepository.save(taskItem);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTask(@PathVariable Long id) {
        boolean exist = taskRepository.existsById(id);
        if(exist) {
            TaskItem taskItem = taskRepository.getReferenceById(id);
            boolean done = taskItem.isDone();
            taskItem.setDone(!done);
            taskRepository.save(taskItem);
            return new ResponseEntity<>( "수정 성공", HttpStatus.OK);
        }
        return new ResponseEntity<>( "수정 실패", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        boolean exists = taskRepository.existsById(id);
        if(exists) {
            taskRepository.deleteById(id);
            return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        }
        return new ResponseEntity<>( "삭제 실패", HttpStatus.BAD_REQUEST);
    }
}
