package com.tanto.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.tanto.functions.FileNameReplace;
import com.tanto.functions.JerseyClientFunction;
import com.tanto.model.ModelTokoh;

import io.swagger.util.Json;

@Service
public class ServiceTokoh {
	static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	ServiceUpload servUpload;
	
	public Boolean insertTokoh(ModelTokoh mtokoh, MultipartFile file) {
		
		try {
			FileNameReplace replace = new FileNameReplace();
			if(servUpload.save(file)) {
				mtokoh.setFoto(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
			}
			
			if(mtokoh.getAfiliasi().isEmpty()) {
				return false;
			}
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			String date_now = formatter.format(date);
			String id = System.currentTimeMillis()+"_"+UUID.randomUUID();
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("id", id);
			data.put("foto", mtokoh.getFoto());
			data.put("nama", mtokoh.getNama());
			data.put("jenis_kelamin", mtokoh.getJenis_kelamin());
			data.put("tempat_lahir", mtokoh.getTempat_lahir());
			data.put("tanggal_lahir", mtokoh.getTanggal_lahir());
			data.put("alamat", mtokoh.getAlamat());
			data.put("pendidikan", mtokoh.getPendidikan());
			data.put("pekerjaan", mtokoh.getPekerjaan());
			data.put("nama_orang_tua", mtokoh.getNama_orang_tua());
			data.put("pekerjaan_orang_tua", mtokoh.getPekerjaan_orang_tua());
			data.put("nama_anak", mtokoh.getNama_anak());
			data.put("status_anak", mtokoh.getStatus_anak());
			data.put("nomor_telepon", mtokoh.getNomor_telepon());
			data.put("afiliasi", mtokoh.getAfiliasi());
			data.put("medsos", mtokoh.getMedsos());
			data.put("email", mtokoh.getEmail());
			data.put("keterangan", mtokoh.getKeterangan());
			data.put("created_at", date_now);
			
			JsonNode js = new JerseyClientFunction().clientPost("http://127.0.0.1:9201/datates/_doc/"+id, data);
			
			if(js.get("result").asText().equals("created")) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean deleteTokoh(String id) {
		try {
			JsonNode js = new JerseyClientFunction().clientDelete("http://127.0.0.1:9201/datates/_doc/"+id);
			
			if(js.get("result").asText().equals("deleted")) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return false;
		}
	}
	
	public Boolean updateTokoh(ModelTokoh mtokoh, MultipartFile file) {
		try {
			
			String id = mtokoh.getId();
			
			if(mtokoh.getAfiliasi().isEmpty()) {
				return false;
			}
			
			if(!file.isEmpty()) {
				FileNameReplace replace = new FileNameReplace();
				if(servUpload.save(file)) {
					mtokoh.setFoto(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
				}
			}else {
				JsonNode jsfile = new JerseyClientFunction().clientGet("http://127.0.0.1:9201/datates/_doc/" + id);
				mtokoh.setFoto(jsfile.get("_source").get("foto").asText());
			}
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			String date_now = formatter.format(date);
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			HashMap<String, Object> data_result = new HashMap<String, Object>();
			data.put("foto", mtokoh.getFoto());
			data.put("nama", mtokoh.getNama());
			data.put("jenis_kelamin", mtokoh.getJenis_kelamin());
			data.put("tempat_lahir", mtokoh.getTempat_lahir());
			data.put("tanggal_lahir", mtokoh.getTanggal_lahir());
			data.put("alamat", mtokoh.getAlamat());
			data.put("pendidikan", mtokoh.getPendidikan());
			data.put("pekerjaan", mtokoh.getPekerjaan());
			data.put("nama_orang_tua", mtokoh.getNama_orang_tua());
			data.put("pekerjaan_orang_tua", mtokoh.getPekerjaan_orang_tua());
			data.put("nama_anak", mtokoh.getNama_anak());
			data.put("status_anak", mtokoh.getStatus_anak());
			data.put("nomor_telepon", mtokoh.getNomor_telepon());
			data.put("afiliasi", mtokoh.getAfiliasi());
			data.put("medsos", mtokoh.getMedsos());
			data.put("email", mtokoh.getEmail());
			data.put("keterangan", mtokoh.getKeterangan());
			data.put("updated_at", date_now);
			data_result.put("doc", data);
			
			JsonNode js = new JerseyClientFunction().clientPost("http://127.0.0.1:9201/datates/_doc/"+id+"/_update", data_result);
			if(js.get("result").asText().equals("updated")) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return false;
		}
	}
}
