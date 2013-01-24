package net.yuanmomo.backend.qsbk.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Resource;

import net.yuanmomo.backend.qsbk.bean.QSBKServer;
import net.yuanmomo.backend.qsbk.bean.Result;
import net.yuanmomo.util.FinalString;
import net.yuanmomo.web.bean.Page;
import net.yuanmomo.web.bean.Record;
import net.yuanmomo.web.bean.User;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QSBKClient {
	private Log logger = LogFactory.getLog(QSBKClient.class);

	@Resource(name = "qsbkServer")
	private QSBKServer qsbkServer;

	private boolean sendData = false;
	private boolean isLogin = false;
	private User user = null;

	private HttpMethodBase method=null;
	
	public Result home(String requestType,String url) throws Exception {
		return this.forward(requestType, this.qsbkServer.getHomeURL());
	}

	public Result forward(String requestType,String url) throws Exception {
		Result result=this.forward(requestType, url);
		while(result.getType()==304){
			result=this.forward(requestType, result.getValue());
		}
		return this.forward(requestType, url);
	}
	public Result request(String requestType,String url) throws Exception{
		try {
			logger.debug("请求得uri 是" + url);
			if(FinalString.GET_REQUEST.equals(requestType)){
				//传递参数
				
				this.method = new GetMethod(url);
			}else if(FinalString.POST_REQUEST.equals(requestType)){
				//传递参数
				
				this.method = new PostMethod(url);
			}else{
				return new Result(0,"Unknow Request");
			}
			HttpClient client = new HttpClient();
			try {
				this.method.setFollowRedirects(false);
				client.executeMethod(this.method);
				//处理此次请求的状态
				return this.handleReturnfStatus(this.method);
			} catch (HttpException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(this.method!=null){
				this.method.releaseConnection();
			}
		}
	}
	private Result handleReturnfStatus(HttpMethodBase method) throws Exception{
		// 打印服务器返回的状态
		int status=method.getStatusCode();
		logger.debug("返回的code是" + status);
		// 检查是否重定向
		if (status == HttpStatus.SC_OK) {
			// 得到返回数据
			String html=null;
			try {
				html = this.method.getResponseBodyAsString();
			} catch (IOException e) {
				throw e;
			}
			if (html != null && !"".equals(html)) {
				return new Result(200,html);
			}
		}else if((status == HttpStatus.SC_MOVED_TEMPORARILY)
				|| (status == HttpStatus.SC_MOVED_PERMANENTLY)
				|| (status == HttpStatus.SC_SEE_OTHER)
				|| (status == HttpStatus.SC_TEMPORARY_REDIRECT)){
			// 读取新的 URL 地址
			Header header = this.method.getResponseHeader("location");
			if (header != null) {
				String newUrl = header.getValue();
				if ("\\login".equals(newUrl)) {
					logger.debug("redirection URL is "
							+ newUrl
							+ ", login failed, cause username and password is incorrect！！");
					return new Result(110,"ogin failed, cause username and password is incorrect！！");
				}
				if ((newUrl == null) || ("".equals(newUrl))) {
					newUrl = "/";
				}
				logger.debug("返回的response body's location 是" + newUrl);
				return new Result(304,this.qsbkServer.getHomeURL()+newUrl);
			}
		}
		return new Result(119,"request error");
	}
	
	
	public int downloadPictures(Page page,String picSavePath) throws Exception {
		int downloadCount = 0;
		if (page != null && page.getRecords() != null
				&& page.getRecords().size() > 0) {
			for (Record r : page.getRecords()) {
				if (r != null && r.getPictureUrl() != null
						&& r.getHasPicture() == 1) {
					// 含有图片，下载，图片的保存路径为/WEB-ROOT/download/pic
					// 图片名是该糗事的MD5码+本来的文件名
					String picUrl = r.getPictureUrl();
					String oldName = picUrl
							.substring(picUrl.lastIndexOf('/') + 1);
					String newName = r.getMD5() + "_" + oldName;
					r.setPictureLocalPath(picSavePath+ File.separator + newName);
					if (!new File(r.getPictureLocalPath()).exists()) {
						try {
							this.logger.debug("Start to download pic "+picUrl);
							fetchPicture(picUrl, picSavePath,
									newName);
						} catch (Exception e) {
							throw e;
						}
					}
				}
			}
		}
		return downloadCount;
	}

	public void fetchPicture(String picUrl, String savePath, String saveName) throws Exception {
		File storeFile = null;
		FileOutputStream output = null;
		GetMethod get = null;
		try {
			HttpClient client = new HttpClient();
			this.logger.debug("Start to connect the url "+picUrl);
			get = new GetMethod(picUrl);
			try {
				client.executeMethod(get);
				if (get.getResponseBody() != null
						&& get.getResponseBody().length > 0) {
					storeFile = new File(savePath + File.separator + saveName);
					output = new FileOutputStream(storeFile);
					output.write(get.getResponseBody());
					this.logger.debug(picUrl+" Pic downloaded!!!!");
				}
			} catch (HttpException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			get.releaseConnection();
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}

	// setter and getter
	public void setQsbkServer(QSBKServer qsbkServer) {
		this.qsbkServer = qsbkServer;
	}

	// 组装发送的数据，用户名，密码
	private NameValuePair[] buildParameters() {
		User u=new User();
		u.setName("342398690@qq.com");
		u.setPassword("harry..9//1");
		u.setRemember(1);
		if (this.isLogin) {
			NameValuePair name = new NameValuePair("login", user.getName());
			NameValuePair password = new NameValuePair("password",
					user.getPassword());
			NameValuePair remember_me = new NameValuePair("remember_me",
					user.getRemember() + "");
			NameValuePair submit = new NameValuePair("submit", "");
			return new NameValuePair[] { name, password, remember_me, submit };
		}
		return null;
	}
}

/*
 * PostMethod post = null; try { client = new HttpClient(); // 设置代理服务器地址和端口 //
 * client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port); // 使用
 * POST 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https post = new
 * PostMethod(qsbkServer.getLoginURL()); // 使用GET方法 // HttpMethod method = new
 * GetMethod("http://java.sun.com");
 * 
 * // 组装发送的数据，用户名，密码 NameValuePair name = new NameValuePair("login",
 * user.getName()); NameValuePair password = new NameValuePair("password",
 * user.getPassword()); NameValuePair remember_me = new
 * NameValuePair("remember_me", user.getRemember() + ""); NameValuePair submit =
 * new NameValuePair("submit", ""); post.setRequestBody(new NameValuePair[] {
 * name, password, remember_me, submit }); int status = 0; try { status =
 * client.executeMethod(post); } catch (HttpException e) { throw e; } catch
 * (IOException e) { throw e; } // 打印服务器返回的状态 logger.debug("返回的code是" + status +
 * "，服务器返回的状态" + post.getStatusLine()); // 检查是否重定向 int statusCode =
 * post.getStatusCode(); if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY) ||
 * (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) || (statusCode ==
 * HttpStatus.SC_SEE_OTHER) || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT))
 * { // 读取新的 URL 地址 Header header = post.getResponseHeader("location"); if
 * (header != null) { String newUri = header.getValue(); if ((newUri == null) ||
 * ("".equals(newUri))) { newUri = "/"; } if ("\\login".equals(newUri)) {
 * logger.debug("redirection URL is " + newUri +
 * ", login failed, cause username and password is incorrect！！"); return null; }
 * logger.debug("返回的response body's location 是" + newUri); return
 * (qsbkServer.getHomeURL() + newUri); } else {// no redirection, then handle
 * the response data logger.error("Invalid redirect"); return null; } } } catch
 * (Exception e) { e.printStackTrace(); logger.error(e.toString()); } finally {
 * // 释放连接 post.releaseConnection(); } return null;
 */
