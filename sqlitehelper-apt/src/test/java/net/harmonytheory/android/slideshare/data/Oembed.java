package net.harmonytheory.android.slideshare.data;

import net.harmonytheory.apt.annotation.Column;
import net.harmonytheory.apt.annotation.SqliteBean;

@SqliteBean(helper="net.harmonytheory.android.slideshare.db.SlideShareDatabaseHelper")
public class Oembed {
	public Oembed() {
	}
	
	@Column
	private String providerUrl;
	
	@Column
	private String type;
	
	@Column
	private String slideImageBaseurl;
	
	@Column
	private int thumbnailWidth;

	@Column
	private String thumbnail;

	@Column
	private int conversionVersion;

	@Column
	private int thumbnailHeight;

	@Column
	private String version;

	@Column
	private String versionNo;

	@Column
	private int width;

	@Column
	private String html;

	@Column
	private String authorName;

	@Column
	private String slideImageBaseurlSuffix;

	@Column
	private int totalSlides;

	@Column
	private String authorUrl;

	@Column
	private String title;

	@Column
	private int height;

	@Column
	private String providerName;

	@Column(primary=true, size=10)
	private int slideshowId;

	public String getProviderUrl() {
		return providerUrl;
	}
	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSlideImageBaseurl() {
		return slideImageBaseurl;
	}
	public void setSlideImageBaseurl(String slideImageBaseurl) {
		this.slideImageBaseurl = slideImageBaseurl;
	}
	public int getThumbnailWidth() {
		return thumbnailWidth;
	}
	public void setThumbnailWidth(int thumbnailWidth) {
		this.thumbnailWidth = thumbnailWidth;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getConversionVersion() {
		return conversionVersion;
	}
	public void setConversionVersion(int conversionVersion) {
		this.conversionVersion = conversionVersion;
	}
	public int getThumbnailHeight() {
		return thumbnailHeight;
	}
	public void setThumbnailHeight(int thumbnailHeight) {
		this.thumbnailHeight = thumbnailHeight;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getSlideImageBaseurlSuffix() {
		return slideImageBaseurlSuffix;
	}
	public void setSlideImageBaseurlSuffix(String slideImageBaseurlSuffix) {
		this.slideImageBaseurlSuffix = slideImageBaseurlSuffix;
	}
	public int getTotalSlides() {
		return totalSlides;
	}
	public void setTotalSlides(int totalSlides) {
		this.totalSlides = totalSlides;
	}
	public String getAuthorUrl() {
		return authorUrl;
	}
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public int getSlideshowId() {
		return slideshowId;
	}
	public void setSlideshowId(int slideshowId) {
		this.slideshowId = slideshowId;
	}
	public String getSlideImageUrl(int slideNo) {
		return new StringBuilder("http:").append(getSlideImageBaseurl()).append(slideNo).append(getSlideImageBaseurlSuffix()).toString();
	}
	public String getExt() {
		return getSlideImageBaseurlSuffix().substring(getSlideImageBaseurlSuffix().lastIndexOf("."));
	}
}
