//package net.yuanmomo.test;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import net.yuanmomo.backend.qsbk.client.HandleBackData;
//import net.yuanmomo.backend.qsbk.client.QSBKClient;
//import net.yuanmomo.util.DateConvertUtil;
//import net.yuanmomo.util.PDFWriter;
//import net.yuanmomo.web.bean.Page;
//import net.yuanmomo.web.bean.Record;
//import net.yuanmomo.web.bean.User;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class TestClient {
//
//	private QSBKClient client = null;
//	private BeanFactory beanFactory = null;
//
//	@Before
//	public void setUp() throws Exception {
//		beanFactory = new ClassPathXmlApplicationContext(
//				"com\\comverse\\timesheet\\config\\ApplicationContext.xml");
//		client = (QSBKClient) beanFactory.getBean("qsbkClient");
//	}
//
//	// After是指在运行完这个测试方法过后回执行的一个方法。一般都是回收资源的。
//	@After
//	public void tearDown() throws Exception {
//		client = null;
//		beanFactory = null;
//	}
//
//	@Test
//	public void saveUserTest(){
//		// 测试插入方法
//		User u=new User();
//		u.setName("342398690@qq.com");
//		u.setPassword("harry..9//1");
//		u.setRemember(1);
//		try {
//			this.client.login(u);
//			String login=this.client.login(u);
//			if(login!=null){
//				String html=this.client.forword("http://www.qiushibaike.com/month?slow");
//				Page page=HandleBackData.convertHTMLToPage("http://www.qiushibaike.com",html);
//				this.client.downloadPictures(page);
//				PDFWriter.write("src/main/webapp/generatePDF", "Test1.pdf",page.getRecords());
//			}
////			File file = new File("."); 
////			String path = file.getCanonicalPath(); 
////			System.out.println(path); // D:\workspace\TimeSheet\QSBK
//			//System.err.println(f.exists());
//			//this.client.fetchPicture("http://videokeman.com/image/pics/WestlifesongPics1Ei6u73qrpJtj8M.jpg", "src/main/webapp/download/pic", "test_WestlifesongPics1Ei6u73qrpJtj8M.jpg");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	@Test
//	public void generatePDF(){
//		int pages=10;
//		String hostURL="http://www.qiushibaike.com";
//		List<Record> result=new ArrayList<Record>();
//		try {
//			Page p=HandleBackData.convertHTMLToPage(hostURL,this.client.forword(hostURL));
//			this.client.downloadPictures(p);
//			result.addAll(p.getRecords());
//			if(p.getNextPage()!=null){
//				for(int i=0;i<pages;i++){
//					try {
//						Thread.sleep(3000);
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					p=HandleBackData.convertHTMLToPage(hostURL,this.client.forword(p.getNextPage()));
//					this.client.downloadPictures(p);
//					for(Record r:p.getRecords()){
//						result.add(r);
//					}
//				}
//			}
//			System.out.println(result.size());
//			if(result!=null && result.size()>0){
//				String fileName=DateConvertUtil.dateToString(new Date()).replaceAll(" ", "_").replace(":", "_");
//				PDFWriter.write("src/main/webapp/generatePDF", fileName+".pdf",result);
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
