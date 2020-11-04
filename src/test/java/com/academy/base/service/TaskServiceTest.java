package com.academy.base.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.academy.base.dto.TaskDTO;
import com.academy.base.entity.TaskEntity;
import com.academy.base.exception.ResourceNotFoundException;
import com.academy.base.repository.ProjectRepository;
import com.academy.base.repository.TaskRepository;
import com.academy.base.service.mapper.TaskMapper;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
	@Spy
	private TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

	@Mock
	private TaskRepository taskRepository;

	@Mock
	private ProjectRepository projectRepository;

	@InjectMocks
	private TaskService taskService;

	@Test
	void when_save_task_it_should_return_saved_task() {
		final TaskEntity taskEntity = buildExampleTaskEntity();
		when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);
		when(projectRepository.existsById(any(Long.class))).thenReturn(true);

		final TaskDTO taskDTO = buildExampleDTO(taskEntity);
		final TaskDTO savedTaskDTO = taskService.save(taskDTO);

		assertNotNull(savedTaskDTO);
		assertNotNull(savedTaskDTO.getId());
		assertEquals(taskEntity.getDescription(), savedTaskDTO.getDescription());
	}

	@Test
	void when_find_all_task_it_should_return_tasks() {
		final TaskEntity taskEntity = buildExampleTaskEntity();
		final Pageable pageRequest = PageRequest.of(0, 5);
		final Page<TaskEntity> pageResult = new PageImpl<>(Collections.singletonList(taskEntity));
		when(taskRepository.findByDescriptionLikeIgnoreCase(any(String.class), eq(pageRequest))).thenReturn(pageResult);

		final Page<TaskDTO> taskDTOs = taskService.findAll("A search condition", pageRequest);

		assertNotNull(taskDTOs);
	}

	@Test
	void when_find_by_id_task_it_should_return_task() {
		final TaskEntity taskEntity = buildExampleTaskEntity();
		when(taskRepository.findById(any(Long.class))).thenReturn(Optional.of(taskEntity));

		final TaskDTO taskDTO = taskService.findById(taskEntity.getId());

		assertNotNull(taskDTO);
		assertEquals(taskEntity.getId(), taskDTO.getId());
		assertEquals(taskEntity.getDescription(), taskDTO.getDescription());
	}

	@Test
	void when_find_by_id_non_present_task_it_should_return_exception() {
		final TaskEntity taskEntity = buildExampleTaskEntity();
		final Long id = taskEntity.getId();
		when(taskRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			taskService.findById(id);
		});
	}

	@Test
	void when_update_task_it_should_return_task() {
		final TaskEntity taskEntity = buildExampleTaskEntity();
		when(projectRepository.existsById(any(Long.class))).thenReturn(true);
		when(taskRepository.existsById(any(Long.class))).thenReturn(true);
		when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

		final TaskDTO taskDTO = buildExampleDTO(taskEntity);
		final TaskDTO savedTaskDTO = taskService.update(taskDTO.getId(), taskDTO);

		assertNotNull(savedTaskDTO);
		assertNotNull(savedTaskDTO.getId());
		assertEquals(taskEntity.getDescription(), savedTaskDTO.getDescription());
	}

	@Test
	void when_update_non_present_task_it_should_return_exception() {
		final TaskEntity taskEntity = buildExampleTaskEntity();
		final Long id = taskEntity.getId();
		when(taskRepository.existsById(any(Long.class))).thenReturn(false);
		final TaskDTO taskDTO = new TaskDTO();
		taskDTO.setId(taskEntity.getId());
		taskDTO.setDescription(taskEntity.getDescription());

		assertThrows(ResourceNotFoundException.class, () -> {
			taskService.update(id, taskDTO);
		});
	}

	@Test
	void when_delete_non_present_task_it_should_return_exception() {
		final TaskEntity taskEntity = buildExampleTaskEntity();
		final Long id = taskEntity.getId();
		when(taskRepository.existsById(any(Long.class))).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> {
			taskService.delete(id);
		});
	}

	@Test
	void when_delete_task_it_should_be_executed() {
		final TaskEntity taskEntity = buildExampleTaskEntity();
		final Long id = taskEntity.getId();
		when(taskRepository.existsById(any(Long.class))).thenReturn(true);

		taskService.delete(id);

		verify(taskRepository, times(1)).deleteById(id);
	}

	private TaskEntity buildExampleTaskEntity() {
		final TaskEntity taskEntity = new TaskEntity();

		taskEntity.setId(1L);
		taskEntity.setDescription("Task 1 description");

		return taskEntity;
	}

	private TaskDTO buildExampleDTO(final TaskEntity taskEntity) {
		final TaskDTO taskDTO = new TaskDTO();

		taskDTO.setId(taskEntity.getId());
		taskDTO.setId(taskEntity.getId());
		taskDTO.setDescription(taskEntity.getDescription());
		taskDTO.setProjectId(1L);

		return taskDTO;
	}

}
