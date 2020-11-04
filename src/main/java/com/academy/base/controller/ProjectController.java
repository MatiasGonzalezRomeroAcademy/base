package com.academy.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.academy.base.dto.ProjectDTO;
import com.academy.base.service.ProjectService;

@RestController
@RequestMapping("/api")
public class ProjectController {
	private ProjectService projectService;

	@Autowired
	public ProjectController(final ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostMapping("/projects")
	public ProjectDTO create(@Validated @RequestBody final ProjectDTO project) {
		return projectService.save(project);
	}

	@GetMapping("/projects")
	public List<ProjectDTO> index() {
		return projectService.findAll();
	}

	@GetMapping("/projects/{id}")
	public ProjectDTO find(@PathVariable final Long id) {
		return projectService.findById(id);
	}

	@PutMapping("/projects/{id}")
	public ProjectDTO update(@PathVariable final Long id, @RequestBody final ProjectDTO project) {
		return projectService.update(id, project);
	}

	@DeleteMapping("/projects/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable final Long id) {
		projectService.delete(id);
	}
}
