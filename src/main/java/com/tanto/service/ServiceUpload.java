package com.tanto.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tanto.functions.FileNameReplace;

@Service
public class ServiceUpload {
	public final Path root = Paths.get("/home/baskara/uploads");
	
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			throw new RuntimeException("Tidak dapat membuat folde");
			// TODO: handle exception
		}
	}

	public Boolean save(MultipartFile file) {
		try {
			FileNameReplace replace = new FileNameReplace();
			Files.copy(file.getInputStream(), this.root.resolve(replace.replaceName(file.getOriginalFilename())));
			return true;
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
		
	}

}
