package net.yuanmomo.backend.qsbk.bean;

public class Result {
	// 200  请求成功,value=html
	// 304 请求被重定向 value=new url
	// 0 Unknow Request
	// 110 登陆错误
	// 119 request error
	private int type;
	private String value;
	
	public Result() {
	}
	public Result(int type, String value) {
		this.type = type;
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
