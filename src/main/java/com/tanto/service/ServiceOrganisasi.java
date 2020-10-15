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
import com.tanto.functions.FileNameReplace;
import com.tanto.functions.JerseyClientFunction;
import com.tanto.model.ModelOrganisasi;

@Service
public class ServiceOrganisasi {
	
	@Autowired
	ServiceUpload servUpload;
	
	static ObjectMapper mapper = new ObjectMapper();
	
	public Boolean insertOrganisasi(ModelOrganisasi org, MultipartFile file) {
		try {
			
			if(org.getAfiliasi().isEmpty()) {
				return false;
			}
			
			FileNameReplace replace = new FileNameReplace();
			
			if(servUpload.save(file)) {
				org.setLogo(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
			}
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			String date_now = formatter.format(date);
			String id = System.currentTimeMillis()+"_"+UUID.randomUUID();
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("id", id);
			data.put("logo", org.getLogo());
			data.put("nama", org.getNama());
			data.put("alamat", org.getAlamat());
			data.put("tanggal_berdiri", org.getTanggal_berdiri());
			data.put("pimpinan", org.getPimpinan());
			data.put("pengurus", org.getPengurus());
			data.put("struktur_organisasi", org.getStruktur_organisasi());
			data.put("nomor", org.getNomer());
			data.put("afiliasi", org.getAfiliasi());
			data.put("jumlah_masa", org.getJumlah_masa());
			data.put("web_email", org.getWeb_email());
			data.put("keterangan", org.getKeterangan());
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
	
	public Boolean deleteOrganisasi(String id) {
		System.out.println(id);
		try {
			JsonNode js = new JerseyClientFunction().clientDelete("http://127.0.0.1:9201/datates/_doc/" + id);
			
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
	
	public Boolean updateOrganisasi(ModelOrganisasi mOrg, MultipartFile file) {
		try {
			String id = mOrg.getId();
			
			if(!file.isEmpty()) {
				FileNameReplace replace = new FileNameReplace();
				if(servUpload.save(file)) {
					mOrg.setLogo(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
				}
			}else {
				JsonNode jsfile = new JerseyClientFunction().clientGet("http://127.0.0.1:9201/datates/_doc/" + id);
				mOrg.setLogo(jsfile.get("_source").get("logo").asText());
			}
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			String date_now = formatter.format(date);
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			HashMap<String, Object> data_result = new HashMap<String, Object>();
			data.put("logo", mOrg.getLogo());
			data.put("nama", mOrg.getNama());
			data.put("alamat", mOrg.getAlamat());
			data.put("tanggal_berdiri", mOrg.getTanggal_berdiri());
			data.put("pimpinan", mOrg.getPimpinan());
			data.put("pengurus", mOrg.getPengurus());
			data.put("struktur_organisasi", mOrg.getStruktur_organisasi());
			data.put("nomor", mOrg.getNomer());
			data.put("afiliasi", mOrg.getAfiliasi());
			data.put("jumlah_masa", mOrg.getJumlah_masa());
			data.put("web_email", mOrg.getWeb_email());
			data.put("keterangan", mOrg.getKeterangan());
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
