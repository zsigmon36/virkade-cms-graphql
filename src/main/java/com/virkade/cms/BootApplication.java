package com.virkade.cms;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

import com.virkade.cms.hibernate.dao.ConstantsDAO;
import com.virkade.cms.hibernate.dao.LocationDAO;
import com.virkade.cms.hibernate.dao.OperatingHoursDAO;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.Audit;
import com.virkade.cms.model.Location;
import com.virkade.cms.model.OperatingHours;
import com.virkade.cms.model.User;
import com.virkade.cms.model.VirkadeModel;

@ComponentScan("com.virkade.cms")
@SpringBootApplication
public class BootApplication {

	private static final Logger LOG = Logger.getLogger(BootApplication.class);
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BootApplication.class, args);
		ConfigurableEnvironment env = ctx.getEnvironment();
	}

	public static void startWorkDay(Location location) {
		LOG.info("starting the work day...");
		if (location == null) {
			location = LocationDAO.getDefault();
		}
		if (OperatingHoursDAO.getTodayOperation(location) == null) {
			OperatingHours opHours = new OperatingHours();
			Calendar cal = Calendar.getInstance();
			User user = UserDAO.fetchByUsername(ConstantsDAO.OWNER_USER_NAME);
			Audit audit = VirkadeModel.addAuditToModel(user, null);

			opHours.setAudit(audit);
			opHours.setStartAt(new Timestamp(cal.getTimeInMillis()));
			opHours.setOperatingDate(new Date(cal.getTimeInMillis()));

			int endHour = Integer.valueOf(PropsUtil.getDefaultClosingTimeHour());
			int endMin = Integer.valueOf(PropsUtil.getDefaultClosingTimeMin());

			cal.clear(Calendar.MILLISECOND);
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), endHour, endMin, 0);

			opHours.setEndAt(new Timestamp(cal.getTimeInMillis()));
			opHours.setLocation(location);

			try {
				OperatingHoursDAO.create(opHours);
			} catch (Exception e) {
				LOG.error(e);
			}
		}
	}
}
