package com.tanto.model;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class ModelOrganisasi {
	private String id;
	private String logo;
	private String nama;
	private String alamat;
	private String tanggal_berdiri;
	private String pimpinan;
	private String pengurus;
	private String struktur_organisasi;
	private String nomer;
	private String afiliasi;
	private int jumlah_masa;
	private String web_email;
	private String keterangan;
	private String created_at;
	private String updated_at;
	
	public String getId() {
		String idString = this.getNama()+"-"+this.getTanggal_berdiri();
		String idHash = Hashing.sha256()
				.hashString(idString, StandardCharsets.UTF_8)
				.toString();
		
		return idHash;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	public String getTanggal_berdiri() {
		return tanggal_berdiri;
	}
	public void setTanggal_berdiri(String tanggal_berdiri) {
		this.tanggal_berdiri = tanggal_berdiri;
	}
	public String getPimpinan() {
		return pimpinan;
	}
	public void setPimpinan(String pimpinan) {
		this.pimpinan = pimpinan;
	}
	public String getPengurus() {
		return pengurus;
	}
	public void setPengurus(String pengurus) {
		this.pengurus = pengurus;
	}
	public String getStruktur_organisasi() {
		return struktur_organisasi;
	}
	public void setStruktur_organisasi(String struktur_organisasi) {
		this.struktur_organisasi = struktur_organisasi;
	}
	public String getNomer() {
		return nomer;
	}
	public void setNomer(String nomer) {
		this.nomer = nomer;
	}
	public String getAfiliasi() {
		return afiliasi;
	}
	public void setAfiliasi(String afiliasi) {
		this.afiliasi = afiliasi;
	}
	public int getJumlah_masa() {
		return jumlah_masa;
	}
	public void setJumlah_masa(int jumlah_masa) {
		this.jumlah_masa = jumlah_masa;
	}
	public String getWeb_email() {
		return web_email;
	}
	public void setWeb_email(String web_email) {
		this.web_email = web_email;
	}
	public String getKeterangan() {
		return keterangan;
	}
	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
}
