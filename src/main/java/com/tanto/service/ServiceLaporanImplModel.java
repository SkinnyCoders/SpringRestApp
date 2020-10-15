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
import com.tanto.model.ModelLaporan;
import com.tanto.model.ModelLaporanUpdate;

@Service
public class ServiceLaporanImplModel {
	
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
	        String dateTimeNow = new GetDateTimeNow().DateTimeNow();
	        //set created at
			mlaporan.setCreated_at(dateTimeNow);
			
			JsonNode jc = new JerseyClientFunction()
					.clientPost(env.getProperty("elasticsearch.url")
							+":"+env.getProperty("elasticsearch.local.port")
							+"/"+env.getProperty("elasticsearch.index.laporan")
							+"/_doc/" + mlaporan.getId(), mlaporan);
			
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
	
	public Boolean updateLaporan(ModelLaporanUpdate mlaporan, MultipartFile file) {
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
	        String dateTimeNow = new GetDateTimeNow().DateTimeNow();
	       //set updated at
	        mlaporan.setUpdate_at(dateTimeNow);
			
			HashMap<String, Object> data_result = new HashMap<String, Object>();
			data_result.put("doc", mlaporan);
			
			JsonNode jc = new JerseyClientFunction()
					.clientPost(env.getProperty("elasticsearch.url")
							+":"+env.getProperty("elasticsearch.local.port")
							+"/"+env.getProperty("elasticsearch.index.laporan")
							+"/_doc/" + mlaporan.getId()+ "/_update", data_result);
			
			if(jc.get("result").asText().equals("updated")) {
				return true;
			}else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean deleteLaporan(String id) {
		try {
			JsonNode js = new JerseyClientFunction()
					.clientDelete(env.getProperty("elasticsearch.url")
							+":"+env.getProperty("elasticsearch.local.port")
							+"/"+env.getProperty("elasticsearch.index.laporan")
							+"/_doc/" + id);
			
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

}
