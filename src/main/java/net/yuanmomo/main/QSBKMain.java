package net.yuanmomo.main;

import net.yuanmomo.backend.qsbk.bean.DownloadProperties;
import net.yuanmomo.backend.qsbk.bean.QSBKServer;
import net.yuanmomo.backend.qsbk.client.QSBKClient;
import net.yuanmomo.backend.qsbk.util.DownloadThread;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QSBKMain {
	private BeanFactory beanFactory = null;
	private DownloadProperties downloadProperties = null;
	private QSBKServer server = null;

	public static void main(String[] args) {
		QSBKMain instance = new QSBKMain();
		try {
			instance.generate("com\\comverse\\timesheet\\config\\ApplicationContext.xml");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instance.beanFactory = null;
			instance.downloadProperties = null;
		}
	}

	public void generate(String springConfigLocation) throws Exception {
		beanFactory = new ClassPathXmlApplicationContext(springConfigLocation);
		downloadProperties = (DownloadProperties) beanFactory
				.getBean("downloadProperties");
		server = (QSBKServer) beanFactory.getBean("qsbkServer");
		String pdfParentPath = downloadProperties.getPdfSavePath();

//		new DownloadManager((QSBKClient) beanFactory.getBean("qsbkClient"),
//				server.getHomeURL(), downloadProperties.getHot8HoursPages(),
//				pdfParentPath, "8hours", server).generate();
//		new DownloadManager((QSBKClient) beanFactory.getBean("qsbkClient"),
//				server.getHot24Hours(),
//				downloadProperties.getHot24HoursPages(), pdfParentPath,
//				"24hours", server).generate();
//		new DownloadManager((QSBKClient) beanFactory.getBean("qsbkClient"),
//				server.getHotWeek(), downloadProperties.getHotWeekPages(),
//				pdfParentPath, "week", server).generate();
//		new DownloadManager((QSBKClient) beanFactory.getBean("qsbkClient"),
//				server.getHotMonth(), downloadProperties.getHotMonthPages(),
//				pdfParentPath, "moth", server).generate();
		
		
		new Thread(new DownloadThread((QSBKClient) beanFactory.getBean("qsbkClient"),
				server.getHomeURL(), downloadProperties.getHot8HoursPages(),
				pdfParentPath, "8hours", server)).run();
		new Thread(new DownloadThread((QSBKClient) beanFactory.getBean("qsbkClient"),
				server.getHot24Hours(),
				downloadProperties.getHot24HoursPages(), pdfParentPath,
				"24hours", server)).run();
		new Thread(new DownloadThread((QSBKClient) beanFactory.getBean("qsbkClient"),
				server.getHotWeek(), downloadProperties.getHotWeekPages(),
				pdfParentPath, "week", server)).run();
		new Thread(new DownloadThread((QSBKClient) beanFactory.getBean("qsbkClient"),
				server.getHotMonth(), downloadProperties.getHotMonthPages(),
				pdfParentPath, "moth", server)).run();
	}
}
