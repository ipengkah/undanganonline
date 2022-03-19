package com.lovelyday.dto;

public class DahboardDto {
	
	private String namaMempelaiPria;
	private String namaMempelaiWanita;
	private String namaPanggilanPria;
	private String namaPanggilanWanita;
	private String namaOrangtuaPria;
	private String namaOrangtuaWanita;
	
	public DahboardDto(String namaMempelaiPria,String namaMempelaiWanita, String namaPanggilPria, String namaPanggilWanita, String namaOrangtuaPria,
			String namaOrangtuaWanita) {
		super();
		this.namaMempelaiPria = namaMempelaiPria;
		this.namaMempelaiWanita = namaMempelaiWanita;
		this.namaPanggilanPria = namaPanggilPria;
		this.namaPanggilanWanita = namaPanggilWanita;
		this.namaOrangtuaPria = namaOrangtuaPria;
		this.namaOrangtuaWanita = namaOrangtuaWanita;
		

	}
	
	@Override
	public String toString() {
		return "DahboardDto [ namaMempelaiPria=" + namaMempelaiPria + ", namaMempelaiWanita="
				+ namaMempelaiWanita + ", namaPanggilanPria=" + namaPanggilanPria + ", namaPanggilanWanita="
				+ namaPanggilanWanita + ", namaOrangtuaPria=" + namaOrangtuaPria + ", namaOrangtuaWanita=" + namaOrangtuaWanita
				+ "]";
	}



	//GETTER SETTER

	public String getNamaMempelaiPria() {
		return namaMempelaiPria;
	}
	public void setNamaMempelaiPria(String namaMempelaiPria) {
		this.namaMempelaiPria = namaMempelaiPria;
	}
	public String getNamaMempelaiWanita() {
		return namaMempelaiWanita;
	}
	public void setNamaMempelaiWanita(String namaMempelaiWanita) {
		this.namaMempelaiWanita = namaMempelaiWanita;
	}
	public String getNamaPanggilanPria() {
		return namaPanggilanPria;
	}
	public void setNamaPanggilanPria(String namaPanggilanPria) {
		this.namaPanggilanPria = namaPanggilanPria;
	}
	public String getNamaPanggilanWanita() {
		return namaPanggilanWanita;
	}
	public void setNamaPanggilanWanita(String namaPanggilanWanita) {
		this.namaPanggilanWanita = namaPanggilanWanita;
	}
	public String getnamaOrangtuaPria() {
		return namaOrangtuaPria;
	}
	public void setnamaOrangtuaPria(String namaOrangtuaPria) {
		this.namaOrangtuaPria = namaOrangtuaPria;
	}
	public String getnamaOrangtuaWanita() {
		return namaOrangtuaWanita;
	}
	public void setnamaOrangtuaWanita(String namaOrangtuaWanita) {
		this.namaOrangtuaWanita = namaOrangtuaWanita;
	}
}
