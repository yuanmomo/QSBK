package net.yuanmomo.web.bean;

import java.util.Date;

public class Record {
	private int    id;
	private String title;
	private String content;
	private String url;
	private String commentUrl;
	private int    length;
	private String author;
	private String authorUrl;
	private String MD5;
	private Date publishDate;
	
	private int hasPicture;
	private String pictureUrl;
	private String pictureLocalPath;
	
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCommentUrl() {
		return commentUrl;
	}
	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMD5() {
		return MD5;
	}
	public void setMD5(String mD5) {
		MD5 = mD5;
	}
	public int getHasPicture() {
		return hasPicture;
	}
	public void setHasPicture(int hasPicture) {
		this.hasPicture = hasPicture;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getPictureLocalPath() {
		return pictureLocalPath;
	}
	public void setPictureLocalPath(String pictureLocalPath) {
		this.pictureLocalPath = pictureLocalPath;
	}
	public String getAuthorUrl() {
		return authorUrl;
	}
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((MD5 == null) ? 0 : MD5.hashCode());
		result = prime * result + id;
		result = prime * result + length;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if (MD5 == null) {
			if (other.MD5 != null)
				return false;
		} else if (!MD5.equals(other.MD5))
			return false;
		if (author != other.author)
			return false;
		if (id != other.id)
			return false;
		if (length != other.length)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
