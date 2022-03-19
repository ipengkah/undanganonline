package com.lovelyday.dto;


public class DahboardDtoDate {
	private Long tanggalAkad;
	private String waktuAkad;
	private Long tanggalResepsi;
	private String waktuResepsi;
	private String koordinatLokasi;
	private String alamatLokasi;

	
	public DahboardDtoDate(Long tanggalAkad,String waktuAkad, Long tanggalResepsi, String waktuResepsi, String koordinatLokasi,
			String alamatLokasi) {
		super();
		this.tanggalAkad = tanggalAkad;
		this.waktuAkad = waktuAkad;
		this.tanggalResepsi = tanggalResepsi;
		this.waktuResepsi = waktuResepsi;
		this.koordinatLokasi = koordinatLokasi;
		this.alamatLokasi = alamatLokasi;
		
	}
	
	@Override
	public String toString() {
		return "DahboardDto [ tanggalAkad=" + tanggalAkad + ", waktuAkad="
				+ waktuAkad + ", tanggalResepsi=" + tanggalResepsi + ", waktuResepsi="
				+ waktuResepsi + ", koordinatLokasi=" + koordinatLokasi + ", alamatLokasi=" + alamatLokasi
				+ "]";
	}


	public String getWaktuAkad() {
		return waktuAkad;
	}

	public void setWaktuAkad(String waktuAkad) {
		this.waktuAkad = waktuAkad;
	}

	public String getWaktuResepsi() {
		return waktuResepsi;
	}

	public void setWaktuResepsi(String waktuResepsi) {
		this.waktuResepsi = waktuResepsi;
	}

	public String getKoordinatLokasi() {
		return koordinatLokasi;
	}

	public void setKoordinatLokasi(String koordinatLokasi) {
		this.koordinatLokasi = koordinatLokasi;
	}

	public String getAlamatLokasi() {
		return alamatLokasi;
	}

	public void setAlamatLokasi(String alamatLokasi) {
		this.alamatLokasi = alamatLokasi;
	}

	public Long getTanggalAkad() {
		return tanggalAkad;
	}

	public void setTanggalAkad(Long tanggalAkad) {
		this.tanggalAkad = tanggalAkad;
	}

	public Long getTanggalResepsi() {
		return tanggalResepsi;
	}

	public void setTanggalResepsi(Long tanggalResepsi) {
		this.tanggalResepsi = tanggalResepsi;
	}
	
}
