package net.yuanmomo.web.bean;

import java.util.List;

public class Page {
	private List<Record> records;
	private String nextPage;
	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	public String getNextPage() {
		return nextPage;
	}
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}
}
