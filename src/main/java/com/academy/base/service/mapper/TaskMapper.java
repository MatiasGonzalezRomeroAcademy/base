package com.academy.base.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.academy.base.dto.TaskDTO;
import com.academy.base.entity.TaskEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TaskMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "description", target = "description")
	@Mapping(source = "project.id", target = "projectId")
	TaskDTO toDTO(final TaskEntity taskEntity);

	@Mapping(source = "description", target = "description")
	@Mapping(source = "id", target = "id")
	TaskEntity toEntity(final TaskDTO taskDTO);
}