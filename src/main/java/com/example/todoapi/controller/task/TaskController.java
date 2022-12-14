package com.example.todoapi.controller.task;

import com.example.todoapi.controller.TasksApi;
import com.example.todoapi.model.PageDto;
import com.example.todoapi.model.TaskDto;
import com.example.todoapi.model.TaskForm;
import com.example.todoapi.model.TaskListDto;
import com.example.todoapi.service.task.TaskEntity;
import com.example.todoapi.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskDto> showTask(Long taskId) {
        var entity = taskService.find(taskId);
        var dto = toTaskDto(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDto> createTask(TaskForm form) {
        var entity = taskService.create(form.getTitle());
        var dto = toTaskDto(entity);
        return ResponseEntity
                .created(URI.create("/tasks/" + dto.getId()))
                .body(dto);
    }

    @Override
    public ResponseEntity<TaskListDto> listTask(Integer limit, Long offset) {
        var entityList = taskService.find(limit, offset);
        var dtoList = entityList.stream()
                .map(this::toTaskDto)
                .toList();

        var pageDto = new PageDto();
        pageDto.setLimit(limit);
        pageDto.setOffset(offset);
        pageDto.setSize(dtoList.size());

        var dto = new TaskListDto();
        dto.setPage(pageDto);
        dto.setResults(dtoList);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDto> editTask(Long taskId, TaskForm taskForm) {
        var entity = taskService.update(taskId, taskForm.getTitle());
        var dto = toTaskDto(entity);
        return ResponseEntity.ok(dto);
    }

    private TaskDto toTaskDto(TaskEntity taskEntity) {
        var taskDto = new TaskDto();
        taskDto.setId(taskEntity.getId());
        taskDto.setTitle(taskEntity.getTitle());
        return taskDto;
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.noContent().build();
    }
}
