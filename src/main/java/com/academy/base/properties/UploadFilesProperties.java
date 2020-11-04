package com.academy.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("files.upload")
@Getter
@Setter
public class UploadFilesProperties {
	private String root;
}
