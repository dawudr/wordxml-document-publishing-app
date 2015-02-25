package com.pearson.app.form;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUploadForm {

	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
}
