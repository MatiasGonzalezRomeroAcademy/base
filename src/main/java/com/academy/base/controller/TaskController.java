package com.academy.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.academy.base.dto.TaskDTO;
import com.academy.base.service.TaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api")
@Api(value = "A controller to manage tasks")
public class TaskController {

	private TaskService taskService;

	@Autowired
	public TaskController(final TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping("/tasks")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Page<TaskDTO> findAll( //
			@RequestParam(required = false, defaultValue = "%") String description, //
			final Pageable pageable //
	) {
		return taskService.findAll(description, pageable);
	}

	@GetMapping("/tasks/{id}")
	@ApiOperation(value = "Get specific task by Id", authorizations = { @Authorization(value = "Bearer") })
	public TaskDTO findById(@PathVariable final Long id) {
		return taskService.findById(id);
	}

	@PostMapping("/tasks")
	public TaskDTO create(@RequestBody final TaskDTO task) {
		return taskService.save(task);
	}

	@PutMapping("/tasks/{id}")
	public TaskDTO create(@PathVariable final Long id, @RequestBody final TaskDTO task) {
		return taskService.update(id, task);
	}

	@DeleteMapping("/tasks/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable final Long id) {
		taskService.delete(id);
	}
}
