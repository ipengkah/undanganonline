package com.lovelyday.dto;

public class OrdersDto {
	
	/*{
	    "userName":"Retantyo102@gmail.com", "websiteName":"satu-wibu", "fotoPria": "base64", "fotoWanita": "base64",
	    "namaMempelaiPria": "John Mayer", "namaMempelaiWanita": "Jessica Aupburn", "namaPanggilanPria": "John", "namaPanggilanWanita": "Jess",
	    "orangtuaPria": "Bill Mayer & Mary Mayer", "orangtuaWanita": "Jack Aupburn & Anna Aupburn",
	    "tanggalAkad": 1626934759000, "tanggalResepsi": 1626934759000, "waktuAkad": "09:00 - 10:00", "waktuResepsi": "12:00 - 15:00",
	    "alamatLokasi": "Gedung Pernikahan Bpk Gua, Jl. Doang Jadian kaga blok. kasian amat", "koordinatLokasi": "-6.243673965794055, 106.85126440723337",
	    "paket":"P1", "kodeTemplate":"LDS01"
	}*/

	private String userName;
	private String websiteName;
	private String fotoPria;
	private String fotoWanita;
	private String namaMempelaiPria;
	private String namaMempelaiWanita;
	private String namaPanggilanPria;
	private String namaPanggilanWanita;
	private String orangtuaPria;
	private String orangtuaWanita;
	private Long tanggalAkad;
	private Long tanggalResepsi;
	private String waktuAkad;
	private String waktuResepsi;
	private String alamatLokasi;
	private String koordinatLokasi;
	private String paket;
	private String kodeTemplate;
	private int paging;
	private String orderType;
	private String searchName;
	
	public OrdersDto(String userName, String websiteName, String fotoPria, String fotoWanita, String namaMempelaiPria,
			String namaMempelaiWanita, String namaPanggilPria, String namaPanggilWanita, String orangtuaPria,
			String orangtuaWanita, Long tanggalAkad, Long tanggalResepsi, String waktuAkad, String waktuResepsi,
			String alamatLokasi, String koordinatLokasi, String paket, String kodeTemplate, int paging, String orderType, String searchName) {
		super();
		this.userName = userName;
		this.websiteName = websiteName;
		this.fotoPria = fotoPria;
		this.fotoWanita = fotoWanita;
		this.namaMempelaiPria = namaMempelaiPria;
		this.namaMempelaiWanita = namaMempelaiWanita;
		this.namaPanggilanPria = namaPanggilPria;
		this.namaPanggilanWanita = namaPanggilWanita;
		this.orangtuaPria = orangtuaPria;
		this.orangtuaWanita = orangtuaWanita;
		this.tanggalAkad = tanggalAkad;
		this.tanggalResepsi = tanggalResepsi;
		this.waktuAkad = waktuAkad;
		this.waktuResepsi = waktuResepsi;
		this.alamatLokasi = alamatLokasi;
		this.koordinatLokasi = koordinatLokasi;
		this.paket = paket;
		this.kodeTemplate = kodeTemplate;
		this.paging = paging;
		this.orderType = orderType;
		this.searchName = searchName;
	}
	

	@Override
	public String toString() {
		return "OrdersDto [userName=" + userName + ", websiteName=" + websiteName + ", fotoPria=" + fotoPria
				+ ", fotoWanita=" + fotoWanita + ", namaMempelaiPria=" + namaMempelaiPria + ", namaMempelaiWanita="
				+ namaMempelaiWanita + ", namaPanggilanPria=" + namaPanggilanPria + ", namaPanggilanWanita="
				+ namaPanggilanWanita + ", orangtuaPria=" + orangtuaPria + ", orangtuaWanita=" + orangtuaWanita
				+ ", tanggalAkad=" + tanggalAkad + ", tanggalResepsi=" + tanggalResepsi + ", waktuAkad=" + waktuAkad
				+ ", waktuResepsi=" + waktuResepsi + ", alamatLokasi=" + alamatLokasi + ", koordinatLokasi="
				+ koordinatLokasi + ", paket=" + paket + ", kodeTemplate=" + kodeTemplate + ", paging=" + paging + ", orderType=" + orderType
				+ ", searchName=" + searchName + "]";
	}


	//GETTER SETTER
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWebsiteName() {
		return websiteName;
	}
	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}
	public String getFotoPria() {
		return fotoPria;
	}
	public void setFotoPria(String fotoPria) {
		this.fotoPria = fotoPria;
	}
	public String getFotoWanita() {
		return fotoWanita;
	}
	public void setFotoWanita(String fotoWanita) {
		this.fotoWanita = fotoWanita;
	}
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
	public String getOrangtuaPria() {
		return orangtuaPria;
	}
	public void setOrangtuaPria(String orangtuaPria) {
		this.orangtuaPria = orangtuaPria;
	}
	public String getOrangtuaWanita() {
		return orangtuaWanita;
	}
	public void setOrangtuaWanita(String orangtuaWanita) {
		this.orangtuaWanita = orangtuaWanita;
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
	public String getAlamatLokasi() {
		return alamatLokasi;
	}
	public void setAlamatLokasi(String alamatLokasi) {
		this.alamatLokasi = alamatLokasi;
	}
	public String getKoordinatLokasi() {
		return koordinatLokasi;
	}
	public void setKoordinatLokasi(String koordinatLokasi) {
		this.koordinatLokasi = koordinatLokasi;
	}
	public String getPaket() {
		return paket;
	}
	public void setPaket(String paket) {
		this.paket = paket;
	}
	public String getKodeTemplate() {
		return kodeTemplate;
	}
	public void setKodeTemplate(String kodeTemplate) {
		this.kodeTemplate = kodeTemplate;
	}

	public int getPaging() {
		return paging;
	}

	public void setPaging(int paging) {
		this.paging = paging;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSearchName() {
		return searchName;
	}


	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
}
