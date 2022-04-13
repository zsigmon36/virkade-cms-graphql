package com.virkade.cms.data.manipulator;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.virkade.cms.hibernate.dao.LegalDAO;
import com.virkade.cms.model.Legal;

public class LegalDocAuditJob {

	private static final Logger LOG = Logger.getLogger(LegalDocAuditJob.class);
	private static boolean started = false;
	private static ScheduledExecutorService threadExec = null;
	
	private LegalDocAuditJob() {
	}
	
	public static void startAudit() {
		LOG.info("starting the legal audit sub-routine");
		if (!started) {
			if (threadExec == null) {
				threadExec = Executors.newScheduledThreadPool(1);
			}
			threadExec.scheduleAtFixedRate(new LegalDocExpireThread(), 30, 60, TimeUnit.MINUTES);
			started = true;
		}
	}
	
	public static void clear() {
		LOG.info("crearing legal audit job");
		threadExec.shutdown();
		started = false;
	}
	
}

class LegalDocExpireThread implements Runnable {
	private static final Logger LOG = Logger.getLogger(LegalDocExpireThread.class);
	private static final int BATCH_SIZE = 1000;

	@Override
	public void run() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, (cal.get(Calendar.YEAR)+5));
		Timestamp deleteDate = new Timestamp(cal.getTimeInMillis());
		
		List<Legal> expiredLegals = LegalDAO.fetchEnabledExpired(BATCH_SIZE);
		List<Legal> disabledLegals = LegalDAO.fetchDisabled(BATCH_SIZE);
		for (Legal legal : expiredLegals) {
			try {
				legal.setEnabled(false);
				LegalDAO.update(legal);
			} catch (Exception e) {
				LOG.error("error disabling expired legal document with id: "+legal.getLegalDocId());
			}
		}
		for (Legal legal : disabledLegals) {
			try {
				if (legal.getExpireDate().after(deleteDate)) {
					LegalDAO.delete(legal);
				}
			} catch (Exception e) {
				LOG.error("error deleting disabled legal document with id: "+legal.getLegalDocId());
			}
		}
		
	}
}
