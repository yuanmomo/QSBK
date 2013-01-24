package net.yuanmomo.backend.qsbk.quartz;

import java.util.Date;

import javax.annotation.Resource;

import net.yuanmomo.backend.qsbk.bean.DownloadProperties;
import net.yuanmomo.backend.qsbk.bean.QSBKServer;
import net.yuanmomo.backend.qsbk.client.QSBKClient;
import net.yuanmomo.backend.qsbk.util.DownloadThread;
import net.yuanmomo.util.DateConvertUtil;

import org.apache.log4j.Logger;

public class DownloadQuartz {
	private static Logger logger = Logger.getLogger(DownloadQuartz.class);

	@Resource(name = "downloadProperties")
	private DownloadProperties downloadProperties = null;
	@Resource(name = "qsbkServer")
	private QSBKServer server = null;
	@Resource(name = "qsbkClient")
	private QSBKClient client = null;

	public void work() {
		logger.debug("Start to update DB info by LDAP, start time is"
				+ DateConvertUtil.dateToString(new Date()));
		String pdfSavePath=this.downloadProperties.getPdfSavePath();
		String picSavePath=this.downloadProperties.getPicSaveRootPath();
		
		try {
			new Thread(new DownloadThread(client, server.getHomeURL(),
					downloadProperties.getHot8HoursPages(),
					pdfSavePath,picSavePath,"8hours", server)).start();
			new Thread(new DownloadThread(client, server.getHot24Hours(),
					downloadProperties.getHot24HoursPages(),
					pdfSavePath,picSavePath,"24hours", server)).start();
			new Thread(new DownloadThread(client, server.getHotWeek(),
					downloadProperties.getHotWeekPages(),
					pdfSavePath,picSavePath, "week", server)).start();
			new Thread(new DownloadThread(client, server.getHotMonth(),
					downloadProperties.getHotMonthPages(),
					pdfSavePath,picSavePath,"moth", server)).start();
			logger.debug("End update DB, end time is"
					+ DateConvertUtil.dateToString(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Exception throwed when update DB" + e.toString());
		}
	}

	public void setDownloadProperties(DownloadProperties downloadProperties) {
		this.downloadProperties = downloadProperties;
	}

	public void setClient(QSBKClient client) {
		this.client = client;
	}

	public void setServer(QSBKServer server) {
		this.server = server;
	}
}
