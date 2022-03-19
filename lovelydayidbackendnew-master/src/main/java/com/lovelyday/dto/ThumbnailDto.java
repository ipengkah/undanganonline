package com.lovelyday.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.lovelyday.model.UserOrders;

public class ThumbnailDto {
	private String titleUndangan;
	private String descUndangan;
	private String photoUndangan;
	
	public ThumbnailDto(UserOrders userOrders) {
		super();
		if(userOrders!=null) {
			this.titleUndangan = "The Wedding of "+userOrders.getGroomShortName()+" & "+userOrders.getBrideShortName();
			this.photoUndangan = "";
			
			if(userOrders.getReceptionDate()!=null || userOrders.getAkadDate()!=null) {
				String[] splitDate = (userOrders.getReceptionDate()!=null?
						(new SimpleDateFormat("EEEE, dd M yyyy").format(userOrders.getReceptionDate())):
							(new SimpleDateFormat("EEEE, dd M yyyy").format(userOrders.getAkadDate())))
						.split(", ");
				if(splitDate.length==2) {
					if(splitDate[0]!=null && splitDate[0].trim().length()>0) {
						if(splitDate[0].equals("Monday")) splitDate[0]="Senin";
						else if(splitDate[0].equals("Tuesday")) splitDate[0]="Selasa";
						else if(splitDate[0].equals("Wednesday")) splitDate[0]="Rabu";
						else if(splitDate[0].equals("Thursday")) splitDate[0]="Kamis";
						else if(splitDate[0].equals("Friday")) splitDate[0]="Jumat";
						else if(splitDate[0].equals("Saturday")) splitDate[0]="Sabtu";
						else if(splitDate[0].equals("Sunday")) splitDate[0]="Minggu";
						
						this.descUndangan = splitDate[0];
					}
					if(splitDate[1]!=null && splitDate[1].trim().length()>1) {
						splitDate = splitDate[1].split(" ");
						if(splitDate.length>1) {
							String[] strMonths = new String[]{
				                      "Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus",
				                      "September","oktober","November","Desember"};
							this.descUndangan = this.descUndangan+", "+splitDate[0]+" "+strMonths[(Integer.parseInt(splitDate[1]))-1]+(splitDate.length>2?(" "+splitDate[2]):"");
						}
					}
				}
			} else this.descUndangan = "";
		}
	}
	
	protected static String tampilkanTanggalDanWaktu(Date tanggalDanWaktu,
            String pola, Locale lokal) {
        String tanggalStr = null;
        SimpleDateFormat formatter = null;
        if (lokal == null) {
            formatter = new SimpleDateFormat(pola);
        } else {
            formatter = new SimpleDateFormat(pola, lokal);
        }
 
        tanggalStr = formatter.format(tanggalDanWaktu);
        return tanggalStr;
    }
	
	@Override
	public String toString() {
		return "QuoteDto [ titleUndangan=" + titleUndangan + ", descUndangan="
				+ descUndangan + ", photoUndangan=" + photoUndangan +"]";
	}

	//GETTER SETTER
	public String getTitleUndangan() {
		return titleUndangan;
	}

	public void setTitleUndangan(String titleUndangan) {
		this.titleUndangan = titleUndangan;
	}

	public String getDescUndangan() {
		return descUndangan;
	}

	public void setDescUndangan(String descUndangan) {
		this.descUndangan = descUndangan;
	}

	public String getPhotoUndangan() {
		return photoUndangan;
	}

	public void setPhotoUndangan(String photoUndangan) {
		this.photoUndangan = photoUndangan;
	}
}
