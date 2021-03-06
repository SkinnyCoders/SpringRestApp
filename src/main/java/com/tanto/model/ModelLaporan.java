package com.tanto.model;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.common.hash.Hashing;

public class ModelLaporan {
	private String id;
	private String judul;
	private String bidang;
	private String afiliasi;
	private String informasi;
	private String lampiran;
	private String created_at;
	private String update_at;
	
	public String getId() {
		String idString = this.getCreated_at();
		String idHash = Hashing.sha256()
				.hashString(idString, StandardCharsets.UTF_8)
				.toString();
		
		return idHash;
	}
	
	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	public String getJudul() {
		return judul;
	}
	public void setJudul(String judul) {
		this.judul = judul;
	}
	public String getBidang() {
		return bidang;
	}
	public void setBidang(String bidang) {
		this.bidang = bidang;
	}
	public String getAfiliasi() {
		return afiliasi;
	}
	public void setAfiliasi(String afiliasi) {
		this.afiliasi = afiliasi;
	}
	public String getInformasi() {
		return informasi;
	}
	public void setInformasi(String informasi) {
		this.informasi = informasi;
	}
	public String getLampiran() {
		return lampiran;
	}
	public void setLampiran(String lampiran) {
		this.lampiran = lampiran;
	}
}
