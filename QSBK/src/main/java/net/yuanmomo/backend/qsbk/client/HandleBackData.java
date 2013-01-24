package net.yuanmomo.backend.qsbk.client;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.util.DateConvertUtil;
import net.yuanmomo.util.MD5;
import net.yuanmomo.web.bean.Page;
import net.yuanmomo.web.bean.Record;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HandleBackData {
	public static Page convertHTMLToPage(String hostURL,String html) throws ParseException{
		Page page=new Page();
		List<Record> records=null;
		if (html != null && !"".equals(html)) {
			records=new ArrayList<Record>();
			Document doc = Jsoup.parse(html);
			Elements linksElements = doc
					.select("div.block,div.ntagged");
			for (Element ele : linksElements) {
				//找到该页中的数据，提取每一条糗事
				if(ele!=null){
					Record r=getOneRecord(ele);
					if(r!=null&&!records.contains(r)){
						records.add(r);
					}
				}
			}
			page.setRecords(records);
			//取得next page
			Elements nextPage=doc.select("div.pagebar>a.next");
			if(nextPage!=null&&nextPage.size()>0){
				page.setNextPage(hostURL+nextPage.get(0).attr("href").replace("/?","?"));
			}
		}
		return page;
	}
	private static Record getOneRecord(Element ele) throws ParseException{
		Record r=new Record();
		//取该糗事的url
		Elements result=ele.select("div.detail>a");
		if(result!=null&&result.size()>0){
			r.setUrl(result.get(0).attr("href"));
		}
		//取该糗事的发表人的url和姓名
		result=ele.select("div.author>a");
		if(result!=null&&result.size()>0){
			r.setAuthorUrl(result.get(0).attr("href"));
			r.setAuthor(result.get(0).ownText());
		}
		//取该糗事的内容以及发布时间
		result=ele.select("div.content");
		if(result!=null&&result.size()>0){
			String content=result.get(0).ownText();
			r.setContent(content);
			r.setLength(content.length());
			r.setMD5(MD5.getMD5(content));
			if(result.get(0).attr("title")!=null &&!"".equals(result.get(0).attr("title"))){
				r.setPublishDate(DateConvertUtil.stringToDate(result.get(0).attr("title")));
			}
		}
		//取得该糗事的评论地址
		result=ele.select("div.comment>a");
		if(result!=null&&result.size()>0){
			r.setCommentUrl(result.get(0).attr("href"));
			r.setAuthor(result.get(0).ownText());
		}
		//判断该糗事是否有图片
		result=ele.select("div.thumb>img");
		if(result!=null&&result.size()>0){
			r.setHasPicture(1);
			r.setPictureUrl(result.get(0).attr("src"));
		}
		return r;
	}
}
