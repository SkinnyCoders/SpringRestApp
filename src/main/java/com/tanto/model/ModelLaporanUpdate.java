package com.tanto.model;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class ModelLaporanUpdate {
	private String id;
	private String judul;
	private String bidang;
	private String afiliasi;
	private String informasi;
	private String lampiran;
	private String update_at;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}
}
