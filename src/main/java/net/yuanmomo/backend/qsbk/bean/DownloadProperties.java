package net.yuanmomo.backend.qsbk.bean;

public class DownloadProperties {
	private int hot8HoursPages;
	private int hot24HoursPages;
	private int hotWeekPages;
	private int hotMonthPages;
	private String pdfSavePath;
	private String picSaveRootPath;
	public int getHot8HoursPages() {
		return hot8HoursPages;
	}
	public void setHot8HoursPages(int hot8HoursPages) {
		this.hot8HoursPages = hot8HoursPages;
	}
	public int getHot24HoursPages() {
		return hot24HoursPages;
	}
	public void setHot24HoursPages(int hot24HoursPages) {
		this.hot24HoursPages = hot24HoursPages;
	}
	public int getHotWeekPages() {
		return hotWeekPages;
	}
	public void setHotWeekPages(int hotWeekPages) {
		this.hotWeekPages = hotWeekPages;
	}
	public int getHotMonthPages() {
		return hotMonthPages;
	}
	public void setHotMonthPages(int hotMonthPages) {
		this.hotMonthPages = hotMonthPages;
	}
	public String getPdfSavePath() {
		return pdfSavePath;
	}
	public void setPdfSavePath(String pdfSavePath) {
		this.pdfSavePath = pdfSavePath;
	}
	public String getPicSaveRootPath() {
		return picSaveRootPath;
	}
	public void setPicSaveRootPath(String picSaveRootPath) {
		this.picSaveRootPath = picSaveRootPath;
	}
}
