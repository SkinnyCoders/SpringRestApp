package com.tanto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.tanto.model.MessageModel;
import com.tanto.model.ModelLaporan;
import com.tanto.model.ModelOrganisasi;
import com.tanto.service.ServiceOrganisasi;

@Controller
@RequestMapping("/organisasi")
public class ControllerOrganisasi {
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity respon;
	
	@Autowired
	ServiceOrganisasi servOrg;
	
	@PostMapping(path = "/tambah",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<MessageModel> insertOrganisasi(ModelOrganisasi org, @RequestParam("file") MultipartFile file) throws JsonMappingException{
		MessageModel ms = new MessageModel();
		
		Boolean result = servOrg.insertOrganisasi(org, file);
		
		if(result) {
			ms.setData(result);
			ms.setMessage("Berhasil Tambah Data");
			ms.setStatus(200);
			respon = new ResponseEntity(ms, HttpStatus.OK);
		}else {
			ms.setData(result);
			ms.setMessage("Gagal Tambah Data");
			ms.setStatus(400);
			respon = new ResponseEntity(ms, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		return respon;
	}

	@DeleteMapping("/hapus")
	public ResponseEntity<MessageModel> deleteOrganisasi(@RequestParam(value = "id") String id){
		MessageModel ms = new MessageModel();
		
		Boolean result = servOrg.deleteOrganisasi(id);
		
		if(result) {
			ms.setData(result);
			ms.setMessage("Berhasil Dihapus");
			ms.setStatus(200);
			respon = new ResponseEntity(ms, HttpStatus.OK);
		}else {
			ms.setData(result);
			ms.setMessage("Gagal Dihapus");
			ms.setStatus(400);
			respon = new ResponseEntity(ms, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		return respon;
	}
	
	@PostMapping( 
			path ="/ubah",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE}
			)
	public ResponseEntity<MessageModel> updateOrganisasi(ModelOrganisasi mOrg, @RequestParam("file") MultipartFile file){
		MessageModel ms = new MessageModel();
		
		Boolean result = servOrg.updateOrganisasi(mOrg, file);
		
		if(result) {
			ms.setData(result);
			ms.setMessage("Berhasil Diperbarui");
			ms.setStatus(200);
			respon = new ResponseEntity(ms, HttpStatus.OK);
		}else {
			ms.setData(result);
			ms.setMessage("Gagal Diperbarui");
			ms.setStatus(400);
			respon = new ResponseEntity(ms, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		return respon;
	}
}
