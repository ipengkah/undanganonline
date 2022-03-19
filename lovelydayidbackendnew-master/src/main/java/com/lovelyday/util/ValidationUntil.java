package com.lovelyday.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class ValidationUntil {
	
	private Log log = LogFactory.getLog(getClass());

	public String validationName(String name) {
		try {
			if (name!=null && !name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) 
				return "Nama tidak boleh ada angka dan tanda baca selain pada gelar.";
			
			return null;
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return "Error validation name!";
		}
	}
	
	public String validationTimeFromTo(String time) {
		try {
			String[] times = time.split(" - ");
			if(times.length==2) {
				String from = times[0];
				String to = times[1];
				try {
					String[] froms = from.split(":");
					String[] tos = to.split(":");
					DateFormat dateFormat = new SimpleDateFormat("HH:mm");
					if(new Long(froms[0])>23 || new Long(tos[0])>23 || new Long(froms[1])>59 || new Long(tos[1])>59) 
						return "Jam '00' s/d '23' dan Menit '00' s/d '59'.";
					if(new Time(dateFormat.parse(from).getTime()).after(new Time(dateFormat.parse(to).getTime()))) 
						return "Periode mulai tidak boleh melebihi periode selesai.";
				} catch (Exception e) {
					log.debug(e.getMessage(), e);
					return "Format waktu 'HH:mm - HH:mm' ,contoh : '08:30 - 12:00'.";
				}
			}else return "Format waktu 'HH:mm - HH:mm' ,contoh : '08:30 - 12:00'.";
			return null;
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return "Error validation time!";
		}
	}
	
}
