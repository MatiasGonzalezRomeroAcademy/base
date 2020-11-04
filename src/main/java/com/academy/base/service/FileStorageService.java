package com.academy.base.service;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.academy.base.entity.ImageEntity;
import com.academy.base.exception.FileStorageException;
import com.academy.base.exception.ResourceNotFoundException;
import com.academy.base.properties.UploadFilesProperties;
import com.academy.base.repository.ImageRepository;

import lombok.SneakyThrows;

@Service
public class FileStorageService {

	private MessageSource messageSource;
	private UploadFilesProperties uploadFilesProperties;
	private ImageRepository imageRepository;

	@Autowired
	public FileStorageService( //
			final MessageSource messageSource, //
			final UploadFilesProperties uploadFilesProperties, //
			final ImageRepository imageRepository //
	) {
		this.messageSource = messageSource;
		this.uploadFilesProperties = uploadFilesProperties;
		this.imageRepository = imageRepository;
	}

	@SneakyThrows
	public String save(final MultipartFile file) {
		if (file.getSize() <= 0) {
			throw new IllegalArgumentException(
					messageSource.getMessage("api.messages.error.empty.file", null, getLocale()));
		}

		final Path newFilePath = Paths.get(uploadFilesProperties.getRoot(), file.getOriginalFilename()).normalize();
		final File newFile = new File(newFilePath.toUri());
		final boolean isFileCreated = newFile.createNewFile();

		if (!isFileCreated) {
			throw new FileStorageException(
					messageSource.getMessage("api.messages.error.upload-file", null, getLocale()));
		}

		try (final FileOutputStream fout = new FileOutputStream(newFile)) {

			fout.write(file.getBytes());
		} catch (IOException ex) {
			throw new FileStorageException(
					messageSource.getMessage("api.messages.error.upload-file", null, getLocale()), ex);
		}

		return messageSource.getMessage("api.messages.success.upload-file", null, getLocale());
	}

	@SneakyThrows
	public Resource load(final String filename) {
		final Path path = Paths.get(uploadFilesProperties.getRoot(), filename).normalize();

		if (!Files.exists(path)) {
			throw new FileNotFoundException(filename);
		}

		try {
			return new UrlResource(path.toUri());
		} catch (final MalformedURLException ex) {
			throw new FileStorageException(
					messageSource.getMessage("api.messages.success.download-file", null, getLocale()), ex);
		}
	}

	@SneakyThrows
	public String saveDB(final MultipartFile file) {
		if (file.getSize() <= 0) {
			throw new IllegalArgumentException(
					messageSource.getMessage("api.messages.error.empty.file", null, getLocale()));
		}

		final ImageEntity image = new ImageEntity();
		image.setPicture(file.getBytes());

		imageRepository.save(image);

		return messageSource.getMessage("api.messages.success.upload-file", null, getLocale());
	}

	public ImageEntity findByIdFromDB(final Long id) {
		return imageRepository.findById(id) //
				.orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
	}
}
