package net.harmonytheory.android.slideshare.data;

import net.harmonytheory.android.slideshare.db.SlideShareDatabaseHelper_;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OembedSqliteHelper extends SlideShareDatabaseHelper_<Oembed> {
	private static final String TABLE = "OEMBED";
	private static final String[] COLUMNS = new String[]{"AUTHOR_NAME", "AUTHOR_URL", "CONVERSION_VERSION", "HEIGHT", "HTML", "PROVIDER_NAME", "PROVIDER_URL", "SLIDE_IMAGE_BASEURL", "SLIDE_IMAGE_BASEURL_SUFFIX", "SLIDESHOW_ID", "THUMBNAIL", "THUMBNAIL_HEIGHT", "THUMBNAIL_WIDTH", "TITLE", "TOTAL_SLIDES", "TYPE", "VERSION", "VERSION_NO", "WIDTH"};
	public OembedSqliteHelper() {
		super();
	}

	@Override
	public String getTable() {
		return TABLE;
	}
	@Override
	public String[] getColumns() {
		return COLUMNS;
	}
	@Override
	public ContentValues beanToContentValues(Oembed bean) {
		return null;
	}
	
	public static void execCreateSql(SQLiteDatabase db) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(TABLE).append(" (");

		sql.append(" AUTHOR_NAME TEXT,");

		sql.append(" AUTHOR_URL TEXT,");

		sql.append(" CONVERSION_VERSION INTEGER,");

		sql.append(" HEIGHT INTEGER,");

		sql.append(" HTML TEXT,");

		sql.append(" PROVIDER_NAME TEXT,");

		sql.append(" PROVIDER_URL TEXT,");

		sql.append(" SLIDE_IMAGE_BASEURL TEXT,");

		sql.append(" SLIDE_IMAGE_BASEURL_SUFFIX TEXT,");

		sql.append(" SLIDESHOW_ID INTEGER,");

		sql.append(" THUMBNAIL TEXT,");

		sql.append(" THUMBNAIL_HEIGHT INTEGER,");

		sql.append(" THUMBNAIL_WIDTH INTEGER,");

		sql.append(" TITLE TEXT,");

		sql.append(" TOTAL_SLIDES INTEGER,");

		sql.append(" TYPE TEXT,");

		sql.append(" VERSION TEXT,");

		sql.append(" VERSION_NO TEXT,");

		sql.append(" WIDTH INTEGER,");

		sql.append(" PRIMARY KEY(");
		
		sql.append("  SLIDESHOW_ID");
		
		sql.append(" )");
		sql.append(");");
		db.execSQL(sql.toString());
	}

	public final Oembed cursorToBean(Cursor c) {
		Oembed bean = new Oembed();

		bean.setAuthorName(c.getString(c.getColumnIndex("AUTHOR_NAME")));

		bean.setAuthorUrl(c.getString(c.getColumnIndex("AUTHOR_URL")));

		bean.setConversionVersion(c.getInt(c.getColumnIndex("CONVERSION_VERSION")));

		bean.setHeight(c.getInt(c.getColumnIndex("HEIGHT")));

		bean.setHtml(c.getString(c.getColumnIndex("HTML")));

		bean.setProviderName(c.getString(c.getColumnIndex("PROVIDER_NAME")));

		bean.setProviderUrl(c.getString(c.getColumnIndex("PROVIDER_URL")));

		bean.setSlideImageBaseurl(c.getString(c.getColumnIndex("SLIDE_IMAGE_BASEURL")));

		bean.setSlideImageBaseurlSuffix(c.getString(c.getColumnIndex("SLIDE_IMAGE_BASEURL_SUFFIX")));

		bean.setSlideshowId(c.getInt(c.getColumnIndex("SLIDESHOW_ID")));

		bean.setThumbnail(c.getString(c.getColumnIndex("THUMBNAIL")));

		bean.setThumbnailHeight(c.getInt(c.getColumnIndex("THUMBNAIL_HEIGHT")));

		bean.setThumbnailWidth(c.getInt(c.getColumnIndex("THUMBNAIL_WIDTH")));

		bean.setTitle(c.getString(c.getColumnIndex("TITLE")));

		bean.setTotalSlides(c.getInt(c.getColumnIndex("TOTAL_SLIDES")));

		bean.setType(c.getString(c.getColumnIndex("TYPE")));

		bean.setVersion(c.getString(c.getColumnIndex("VERSION")));

		bean.setVersionNo(c.getString(c.getColumnIndex("VERSION_NO")));

		bean.setWidth(c.getInt(c.getColumnIndex("WIDTH")));

		return bean;
	}
}
