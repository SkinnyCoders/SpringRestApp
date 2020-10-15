package com.tanto.service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.tanto.functions.FileNameReplace;
import com.tanto.functions.JerseyClientFunction;
import com.tanto.model.ModelLaporan;
import com.tanto.model.ModelOrganisasi;
import com.tanto.model.ModelTokoh;

@Service
public class ServiceLaporan {
	
	static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	ServiceUpload servUpload;
	
	@Autowired
	Environment env;
	
	public Boolean insertLaporan(ModelLaporan mlaporan, MultipartFile file) {

		try {
			if(mlaporan.getAfiliasi().isEmpty()) {
				return false;
			}else if(mlaporan.getBidang().isEmpty()){
				return false;
			}
			
			if(!file.isEmpty()) {
				FileNameReplace replace = new FileNameReplace();
				if(servUpload.save(file)) {
					mlaporan.setLampiran(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
				}
			}
			
			//get date time now 
			Instant dateNow = Instant.now();
			ZonedDateTime nowAsiaSingapore = dateNow.atZone(ZoneId.of("Asia/Jakarta"));
		    LocalDateTime utc1 = nowAsiaSingapore.toLocalDateTime();
		    //formated date time
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formatDateTime = utc1.format(formatter);
		    
//		    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//			Date date = new Date(time);
//			String date_now = formatter.format(date);			
			
			//String id = System.currentTimeMillis()+"_"+UUID.randomUUID();
	        
			String idString = mlaporan.getJudul()+"-"+mlaporan.getBidang()+"-"+mlaporan.getAfiliasi();
			String idHash = Hashing.sha256()
					.hashString(idString, StandardCharsets.UTF_8)
					.toString();
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("id", idHash);
			data.put("judul", mlaporan.getJudul());
			data.put("bidang", mlaporan.getBidang());
			data.put("afiliasi", mlaporan.getAfiliasi());
			data.put("informasi", mlaporan.getInformasi());
			data.put("lampiran", mlaporan.getLampiran());
			data.put("created_at", formatDateTime);
			
			JsonNode jc = new JerseyClientFunction().clientPost(env.getProperty("elasticsearch.url")+":"+env.getProperty("elasticsearch.local.port")+"/"+env.getProperty("elasticsearch.index")+"/_doc/" + idHash, data);
			
			if(jc.get("result").asText().equals("created")) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		}
	}
	
	public Boolean deleteLaporan(String id) {
		try {
			JsonNode js = new JerseyClientFunction().clientDelete(env.getProperty("elasticsearch.url")+":"+env.getProperty("elasticsearch.local.port")+"/"+env.getProperty("elasticsearch.index")+"/_doc/" + id);
			if (js.get("result").asText().equals("deleted")) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return false;
		}
	}
	
	public Boolean updateLaporan(ModelLaporan mlaporan, MultipartFile file) {
		try {
			String id = mlaporan.getId();
			
			if(mlaporan.getAfiliasi().isEmpty()) {
				return false;
			}else if(mlaporan.getBidang().isEmpty()){
				return false;
			}
			
			if(!file.isEmpty()) {
				FileNameReplace replace = new FileNameReplace();
				if(servUpload.save(file)) {
					mlaporan.setLampiran(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
				}
			}else {
				JsonNode jsfile = new JerseyClientFunction().clientGet(env.getProperty("elasticsearch.url")+":"+env.getProperty("elasticsearch.local.port")+"/"+env.getProperty("elasticsearch.index")+"/_doc/" + id);
				mlaporan.setLampiran(jsfile.get("_source").get("lampiran").asText());
			}
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			String date_now = formatter.format(date);
			
			HashMap<String, Object> data_result = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("judul", mlaporan.getJudul());
			data.put("bidang", mlaporan.getBidang());
			data.put("afiliasi", mlaporan.getAfiliasi());
			data.put("informasi", mlaporan.getInformasi());
			data.put("lampiran", mlaporan.getLampiran());
			data.put("updated_at", date_now);
			data_result.put("doc", data);
			
			JsonNode jc = new JerseyClientFunction()
					.clientPost(env.getProperty("elasticsearch.url")+":"+env.getProperty("elasticsearch.local.port")+"/"+env.getProperty("elasticsearch.index")+"/_doc/" + id+ "/_update", data_result);
			
			if(jc.get("result").asText().equals("updated")) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
}
