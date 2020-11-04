package com.academy.base.service;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.academy.base.dto.ProjectDTO;
import com.academy.base.entity.ProjectEntity;
import com.academy.base.exception.ResourceNotFoundException;
import com.academy.base.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private TaskService taskService;

	@InjectMocks
	private ProjectService projectService;

	@Test
	void when_save_project_it_should_return_project() {
		// prepare
		final ProjectEntity projectEntity = buildExampleProjectEntity();
		final ProjectDTO projectDTO = buildExampleProjectDTOFromEntity(projectEntity);

		when(projectRepository.save(any(ProjectEntity.class))).thenReturn(projectEntity);
		when(taskService.toEntities(any())).thenReturn(new ArrayList<>(projectEntity.getTasks()));
		when(taskService.toDTOs(any())).thenReturn(new ArrayList<>(projectDTO.getTasks()));

		// action
		final ProjectDTO savedProjectDTO = projectService.save(projectDTO);

		// asserts
		assertNotNull(savedProjectDTO);
		assertNotNull(savedProjectDTO.getId());
		assertEquals(projectEntity.getName(), savedProjectDTO.getName());
		assertEquals(projectEntity.getDescription(), savedProjectDTO.getDescription());
	}

	@Test
	void when_find_all_project_it_should_return_projects() {
		// prepare
		final ProjectEntity projectEntity = buildExampleProjectEntity();
		when(projectRepository.findAll()).thenReturn(singletonList(projectEntity));

		// action
		final List<ProjectDTO> projectDTOs = projectService.findAll();

		// asserts
		assertNotNull(projectDTOs);
	}

	@Test
	void when_find_by_id_project_it_should_return_project() {
		// prepare
		final ProjectEntity projectEntity = buildExampleProjectEntity();
		when(projectRepository.findById(any(Long.class))).thenReturn(Optional.of(projectEntity));

		// action
		final ProjectDTO projectDTO = projectService.findById(projectEntity.getId());

		// asserts
		assertNotNull(projectDTO);
		assertEquals(projectEntity.getId(), projectDTO.getId());
		assertEquals(projectEntity.getName(), projectDTO.getName());
		assertEquals(projectEntity.getDescription(), projectDTO.getDescription());
	}

	@Test
	void when_find_by_id_non_present_project_it_should_return_exception() {
		// prepare
		final ProjectEntity projectEntity = buildExampleProjectEntity();
		final Long id = projectEntity.getId();
		when(projectRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		// action and assert
		assertThrows(ResourceNotFoundException.class, () -> {
			projectService.findById(id);
		});
	}

	@Test
	void when_update_project_it_should_return_project() {
		// prepare
		final ProjectEntity projectEntity = buildExampleProjectEntity();
		final ProjectDTO projectDTO = buildExampleProjectDTOFromEntity(projectEntity);

		when(projectRepository.save(any(ProjectEntity.class))).thenReturn(projectEntity);
		when(projectRepository.existsById(any(Long.class))).thenReturn(true);
		when(taskService.toEntities(any())).thenReturn(new ArrayList<>(projectEntity.getTasks()));
		when(taskService.toDTOs(any())).thenReturn(new ArrayList<>(projectDTO.getTasks()));

		// action
		final ProjectDTO savedProjectDTO = projectService.update(projectDTO.getId(), projectDTO);

		// asserts
		assertNotNull(savedProjectDTO);
		assertNotNull(savedProjectDTO.getId());
		assertEquals(projectEntity.getName(), savedProjectDTO.getName());
		assertEquals(projectEntity.getDescription(), savedProjectDTO.getDescription());
	}

	@Test
	void when_update_non_present_project_it_should_return_exception() {
		// prepare
		final ProjectEntity projectEntity = buildExampleProjectEntity();
		final ProjectDTO projectDTO = buildExampleProjectDTOFromEntity(projectEntity);
		final Long id = projectEntity.getId();
		when(projectRepository.existsById(any(Long.class))).thenReturn(false);

		// asserts
		assertThrows(ResourceNotFoundException.class, () -> {
			projectService.update(id, projectDTO);
		});
	}

	@Test
	void when_delete_non_present_project_it_should_return_exception() {
		// prepare
		final ProjectEntity projectEntity = buildExampleProjectEntity();
		final Long id = projectEntity.getId();
		when(projectRepository.existsById(any(Long.class))).thenReturn(false);

		// asserts
		assertThrows(ResourceNotFoundException.class, () -> {
			projectService.delete(id);
		});
	}

	@Test
	void when_delete_project_it_should_be_executed() {
		// prepare
		final ProjectEntity projectEntity = buildExampleProjectEntity();
		final Long id = projectEntity.getId();
		when(projectRepository.existsById(any(Long.class))).thenReturn(true);

		// action
		projectService.delete(id);

		// assert
		verify(projectRepository, times(1)).deleteById(id);
	}

	private ProjectEntity buildExampleProjectEntity() {
		final ProjectEntity projectEntity = new ProjectEntity();

		projectEntity.setId(1L);
		projectEntity.setName("Project 1");
		projectEntity.setDescription("Project 1 description");

		return projectEntity;
	}

	private ProjectDTO buildExampleProjectDTOFromEntity(final ProjectEntity projectEntity) {
		final ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setId(projectEntity.getId());
		projectDTO.setName(projectEntity.getName());
		projectDTO.setDescription(projectEntity.getDescription());
		return projectDTO;
	}
}
