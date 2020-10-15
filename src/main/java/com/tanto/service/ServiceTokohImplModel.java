package com.tanto.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanto.functions.FileNameReplace;
import com.tanto.functions.GetDateTimeNow;
import com.tanto.functions.JerseyClientFunction;
import com.tanto.model.ModelTokoh;

@Service
public class ServiceTokohImplModel {
	static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	ServiceUpload servUpload;
	
	@Autowired
	Environment env;
	
	public Boolean insertTokoh(ModelTokoh mtokoh, MultipartFile file) {
		
		try {
			
			if(!file.isEmpty()) {
				if(servUpload.save(file)) {
					FileNameReplace replace = new FileNameReplace();
					mtokoh.setFoto(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
				}
			}
			
			if(mtokoh.getAfiliasi().isEmpty()) {
				return false;
			}
			
			//get date time now
			String dateTimeNow = new GetDateTimeNow().DateTimeNow();
	        //set created at base date time on create
	        mtokoh.setCreated_at(dateTimeNow);
			
			JsonNode js = new JerseyClientFunction().clientPost(env.getProperty("elasticsearch.url")
					+":"+env.getProperty("elasticsearch.local.port")
					+"/"+env.getProperty("elasticsearch.index.tokoh")
					+"/_doc/"+mtokoh.getId(), mtokoh);
			
			if(js.get("result").asText().equals("created")) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean deleteTokoh(String id) {
		try {
			JsonNode js = new JerseyClientFunction().clientDelete(env.getProperty("elasticsearch.url")
					+":"+env.getProperty("elasticsearch.local.port")
					+"/"+env.getProperty("elasticsearch.index.tokoh")
					+"/_doc/"+id);
			
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
			
			if(mtokoh.getAfiliasi().isEmpty()) {
				return false;
			}
			
			if(!file.isEmpty()) {
				FileNameReplace replace = new FileNameReplace();
				if(servUpload.save(file)) {
					mtokoh.setFoto(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
				}
			}
			
			//get date time now
			String dateTimeNow = new GetDateTimeNow().DateTimeNow();
	        //set update at base date time on update
	        mtokoh.setUpdated_at(dateTimeNow);
			
			HashMap<String, Object> data_result = new HashMap<String, Object>();
			data_result.put("doc", mtokoh);
			
			JsonNode js = new JerseyClientFunction().clientPost("http://127.0.0.1:9201/datates/_doc/"+mtokoh.getId()+"/_update", data_result);
			if(js.get("result").asText().equals("updated")) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
