package net.yuanmomo.backend.qsbk.util;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.yuanmomo.backend.qsbk.bean.QSBKServer;
import net.yuanmomo.backend.qsbk.bean.Result;
import net.yuanmomo.backend.qsbk.client.HandleBackData;
import net.yuanmomo.backend.qsbk.client.QSBKClient;
import net.yuanmomo.util.DateConvertUtil;
import net.yuanmomo.util.FinalString;
import net.yuanmomo.util.PDFWriter;
import net.yuanmomo.web.bean.Page;
import net.yuanmomo.web.bean.Record;

public class DownloadThread implements Runnable{
	private QSBKClient client = null;
	private String url;
	private int pages;
	private String name;
	private QSBKServer server=null;
	private String pdfParentPath;
	private String picSavePath;
	
	public DownloadThread(QSBKClient client, String url, int pages,
			String pdfParentPath, String picSavePath,String name,QSBKServer server) {
		this.client = client;
		this.url = url;
		this.pages = pages;
		this.pdfParentPath = pdfParentPath;
		this.name = name;
		this.server=server;
		this.picSavePath=picSavePath;
	}
	public void run() {
		String newFolderName=DateConvertUtil.
				dateToString(new Date()).replace(" ", "_")
				.replaceAll(":","_"); //保存该线程运行后图片和pdf文件的存放地址
		//比如：/download/pic/${newFolderName}/xxxx
		//比如：/download/pic/${newFolderName}/xxxx
		
		File picSaveFolder=new File(picSavePath+File.separator+newFolderName);
		picSaveFolder.mkdirs();
		File pdfSaveFolder=new File(pdfParentPath+File.separator+newFolderName);
		pdfSaveFolder.mkdirs();
		
		List<Record> result = new ArrayList<Record>();
		try {
			Result res=this.client.request(FinalString.GET_REQUEST,url);
			while(res.getType()!=200){
				res=this.client.request(FinalString.GET_REQUEST,res.getValue());
			}
			Page p = HandleBackData.convertHTMLToPage(this.server.getHomeURL(),res.getValue());
			this.client.downloadPictures(p,picSaveFolder.getPath());
			result.addAll(p.getRecords());
			if (p.getNextPage() != null) {
				for (int i = 0; i < pages; i++) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						throw e;
					}
					res=this.client.request(FinalString.GET_REQUEST,p.getNextPage());
					while(res.getType()!=200){
						res=this.client.request(FinalString.GET_REQUEST,res.getValue());
					}
					p = HandleBackData.convertHTMLToPage(this.server.getHomeURL(),res.getValue());
					this.client.downloadPictures(p,picSaveFolder.getPath());
					for (Record r : p.getRecords()) {
						if(!result.contains(r)){
							result.add(r);
						}
					}
				}
			}
			if (result != null && result.size() > 0) {
				String fileName = DateConvertUtil.dateToString(new Date())
						.replaceAll(" ", "_").replace(":", "_");
				PDFWriter.write(pdfParentPath+File.separator+newFolderName, fileName +"_"+name+ ".pdf", result);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
	}
}
