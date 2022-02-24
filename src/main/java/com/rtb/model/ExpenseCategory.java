package com.rtb.model;

public class ExpenseCategory {

	// ------------------- Columns / Properties -------------------------

	private long id;

	private long created;

	private long modified;

	private String uid;

	private String categoryName;

	private String categoryDescription;

	private String imageUrl;
	
	private String key;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "ExpenseCategory [id=" + id + ", created=" + created + ", modified=" + modified + ", uid=" + uid
				+ ", categoryName=" + categoryName + ", categoryDescription=" + categoryDescription + ", imageUrl="
				+ imageUrl + ", key=" + key + "]";
	}
	
	
}
