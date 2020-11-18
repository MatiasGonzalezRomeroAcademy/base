package com.academy.base.entity;

import static java.util.stream.Collectors.toSet;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PROJECTS")
public class ProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private ProjectDetailEntity detail;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TaskEntity> tasks = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "project_tags", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<TagEntity> tags = new HashSet<>();

	public void setTasks(final Set<TaskEntity> tasks) {
		if (tasks == null) {
			this.tasks = new HashSet<>();
			return;
		}

		this.tasks = tasks.stream() //
				.filter(Objects::nonNull) //
				.map(task -> {
					task.setProject(this);
					return task;
				}) //
				.collect(toSet());

	}
}
