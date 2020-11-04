package com.academy.base.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academy.base.dto.ProjectDTO;
import com.academy.base.entity.ProjectEntity;
import com.academy.base.entity.TaskEntity;
import com.academy.base.exception.ResourceNotFoundException;
import com.academy.base.repository.ProjectRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectService {

	private ProjectRepository projectRepository;

	private TaskService taskService;

	@Autowired
	public ProjectService(final ProjectRepository projectRepository, final TaskService taskService) {
		this.projectRepository = projectRepository;
		this.taskService = taskService;
	}

	@Transactional
	public List<ProjectDTO> findAll() {
		return entitiesToDTO(projectRepository.findAll());
	}

	@Transactional
	@Cacheable(cacheNames = "projects", key = "#id", unless = "#id.equals(11L)")
	public ProjectDTO findById(final Long id) {
		log.info("Not cached data for project(id={}), caching...", id);

		return projectRepository.findById(id) //
				.map(this::toDTO) //
				.orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
	}

	@Transactional
	@CachePut(cacheNames = "projects", key = "#id")
	public ProjectDTO update(final Long id, final ProjectDTO projectDTO) {
		if (!projectRepository.existsById(id)) {
			throw new ResourceNotFoundException("Project", "id", id);
		}

		final ProjectEntity projectEntity = toEntity(projectDTO);

		return toDTO(projectRepository.save(projectEntity));
	}

	@Transactional
	@CacheEvict(cacheNames = "projects", key = "#id")
	public void delete(final Long id) {
		if (!projectRepository.existsById(id)) {
			throw new ResourceNotFoundException("Project", "id", id);
		}

		projectRepository.deleteById(id);
	}

	@Transactional
	@CachePut(cacheNames = "projects", key = "#result.id")
	public ProjectDTO save(final ProjectDTO project) {
		final ProjectEntity entity = toEntity(project);

		return toDTO(projectRepository.save(entity));
	}

	private List<ProjectDTO> entitiesToDTO(final List<ProjectEntity> entities) {
		return entities.stream() //
				.map(this::toDTO) //
				.collect(toList());
	}

	private ProjectEntity toEntity(final ProjectDTO dto) {
		final ProjectEntity entity = new ProjectEntity();

		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());

		final List<TaskEntity> taskEntities = taskService.toEntities(dto.getTasks());
		entity.setTasks(new HashSet<>(taskEntities));

		return entity;
	}

	private ProjectDTO toDTO(final ProjectEntity entity) {
		final ProjectDTO dto = new ProjectDTO();

		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());

		final List<TaskEntity> taskDTOs = new ArrayList<>(entity.getTasks());
		dto.setTasks(taskService.toDTOs(taskDTOs));

		return dto;
	}
}
