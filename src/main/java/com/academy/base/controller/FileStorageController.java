package com.academy.base.controller;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.academy.base.entity.ImageEntity;
import com.academy.base.service.FileStorageService;

@RestController
@RequestMapping("/api/files")
public class FileStorageController {

	private FileStorageService fileUploadService;

	@Autowired
	public FileStorageController(final FileStorageService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String upload(@RequestParam("file") final MultipartFile file) {
		return fileUploadService.save(file);
	}

	@GetMapping(path = "/download/{filename:.+}")
	public ResponseEntity<Resource> download(@PathVariable final String filename) {
		final Resource file = fileUploadService.load(filename);

		return ResponseEntity.ok() //
				.header(CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"") //
				.body(file);
	}

	@PostMapping(path = "/upload/db", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadDB(@RequestParam("file") final MultipartFile file) {
		return fileUploadService.saveDB(file);
	}

	@GetMapping(path = "/download/db/{id}")
	public ResponseEntity<byte[]> downloadDB(@PathVariable final Long id) {
		final ImageEntity image = fileUploadService.findByIdFromDB(id);

		return ResponseEntity.ok() //
				.header(CONTENT_DISPOSITION, "attachment; filename=\"" + image.getId() + "\"") //
				.body(image.getPicture());
	}
}
