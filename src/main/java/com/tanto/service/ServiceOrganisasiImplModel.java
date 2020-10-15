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
import com.tanto.model.ModelOrganisasi;

@Service
public class ServiceOrganisasiImplModel {
	@Autowired
	ServiceUpload servUpload;
	
	@Autowired
	Environment env;
	
	static ObjectMapper mapper = new ObjectMapper();
	
	public Boolean insertOrganisasi(ModelOrganisasi Morg, MultipartFile file) {
		try {
			if(Morg.getAfiliasi().isEmpty()) {
				return false;
			}
			
			if(!file.isEmpty()) {
				if(servUpload.save(file)) {
					FileNameReplace replace = new FileNameReplace();
					Morg.setLogo(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
				}
			}
			
			//get date time now
			String dateTimeNow = new GetDateTimeNow().DateTimeNow();
	        //set created at by date time now
	        Morg.setCreated_at(dateTimeNow);
			
			JsonNode js = new JerseyClientFunction().clientPost(env.getProperty("elasticsearch.url")
					+":"+env.getProperty("elasticsearch.local.port")
					+"/"+env.getProperty("elasticsearch.index.organisasi")
					+"/_doc/"+Morg.getId(), Morg);
			
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
	
	public Boolean deleteOrganisasi(String id) {
		try {
			JsonNode js = new JerseyClientFunction().clientDelete(env.getProperty("elasticsearch.url")
					+":"+env.getProperty("elasticsearch.local.port")
					+"/"+env.getProperty("elasticsearch.index.organisasi")+"/_doc/" + id);
			
			if(js.get("result").asText().equals("deleted")) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean updateOrganisasi(ModelOrganisasi mOrg, MultipartFile file) {
		try {
			if(!file.isEmpty()) {
				FileNameReplace replace = new FileNameReplace();
				if(servUpload.save(file)) {
					mOrg.setLogo(servUpload.root+"/"+replace.replaceName(file.getOriginalFilename()));
				}
			}
			
			//get date time now
			String dateTimeNow = new GetDateTimeNow().DateTimeNow();
			//set updated at
	        mOrg.setUpdated_at(dateTimeNow);
			
			HashMap<String, Object> data_result = new HashMap<String, Object>();
			data_result.put("doc", mOrg);
			
			JsonNode js = new JerseyClientFunction().clientPost(env.getProperty("elasticsearch.url")
					+":"+env.getProperty("elasticsearch.local.port")
					+"/"+env.getProperty("elasticsearch.index.organisasi")+"/_doc/"+mOrg.getId()+"/_update", data_result);
			
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
