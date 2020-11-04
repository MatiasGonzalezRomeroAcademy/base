package com.academy.base.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.academy.base.dto.TaskDTO;
import com.academy.base.entity.TaskEntity;
import com.academy.base.exception.ResourceNotFoundException;
import com.academy.base.repository.ProjectRepository;
import com.academy.base.repository.TaskRepository;
import com.academy.base.service.mapper.TaskMapper;

@Service
public class TaskService {

	private TaskRepository taskRepository;

	private ProjectRepository projectRepository;

	private TaskMapper taskMapper;

	@Autowired
	public TaskService( //
			final TaskRepository taskRepository, //
			final ProjectRepository projectRepository, //
			final TaskMapper taskMapper) {
		this.taskRepository = taskRepository;
		this.projectRepository = projectRepository;
		this.taskMapper = taskMapper;
	}

	@Transactional
	public TaskDTO save(final TaskDTO task) {
		validateProjectExistance(task.getProjectId());

		return toDTO(taskRepository.save(toEntity(task)));
	}

	@Transactional
	public Page<TaskDTO> findAll(final String description, final Pageable pageable) {
		return taskRepository.findByDescriptionLikeIgnoreCase(description, pageable)//
				.map(this::toDTO);
	}

	@Transactional
	public TaskDTO findById(final Long id) {
		return taskRepository.findById(id) //
				.map(this::toDTO) //
				.orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
	}

	@Transactional
	public TaskDTO update(final Long id, final TaskDTO task) {
		if (!taskRepository.existsById(id)) {
			throw new ResourceNotFoundException("Task", "id", id);
		}

		validateProjectExistance(task.getProjectId());

		return toDTO(taskRepository.save(toEntity(task)));
	}

	@Transactional
	public void delete(final Long id) {
		if (!taskRepository.existsById(id)) {
			throw new ResourceNotFoundException("Task", "id", id);
		}

		taskRepository.deleteById(id);
	}

	public List<TaskEntity> toEntities(final List<TaskDTO> dtos) {
		return dtos.stream() //
				.map(this::toEntity) //
				.collect(toList());
	}

	public List<TaskDTO> toDTOs(final List<TaskEntity> entities) {
		return entities.stream() //
				.map(this::toDTO) //
				.collect(toList());
	}

	public TaskDTO toDTO(final TaskEntity taskEntity) {
		return taskMapper.toDTO(taskEntity);
	}

	public TaskEntity toEntity(final TaskDTO dto) {
		final TaskEntity taskDTO = new TaskEntity();

		taskDTO.setId(dto.getId());
		taskDTO.setDescription(dto.getDescription());

		return taskDTO;
	}

	private void validateProjectExistance(final Long projectId) {
		if (!projectRepository.existsById(projectId)) {
			throw new ResourceNotFoundException("Project", "projectId", projectId);
		}
	}
}
