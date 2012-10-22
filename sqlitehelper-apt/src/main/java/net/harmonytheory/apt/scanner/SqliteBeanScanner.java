package net.harmonytheory.apt.scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import net.harmonytheory.apt.annotation.Column;
import net.harmonytheory.apt.annotation.SqliteBean;

/**
 * SqliteBean用スキャナクラス。
 * @author SPEEDY
 */
public class SqliteBeanScanner extends ScannerBase {
	public static final String POST_FIX = "SqliteHelper";
	private BeanSqliteHelperTemplate template;
	/***
	 * コンストラクタ。
	 * @param element クラスエレメント
	 */
	public SqliteBeanScanner(Element element) {
		super(element);
		template = new BeanSqliteHelperTemplate();
	}
	@Override
	protected TemplateBase getTemplate() {
		return template;
	}
	@Override
	public void generate() {
		scan(classElement);
	}
	
    @Override
    public Void visitType(TypeElement e, Void p) {
    	SqliteBean sqliteBean = e.getAnnotation(SqliteBean.class);
    	String packageName = elementUtils.getPackageOf(classElement).getQualifiedName().toString();
    	
		String helperBaseName = sqliteBean.helper();
		// Helper名末尾にアンダースコアをつけたものを親クラスとする（BeanSqliteHelperBaseScannerで生成）
		int packageIndex = helperBaseName.lastIndexOf(".");
		String helperName = helperBaseName.substring(packageIndex + 1).concat("_");
		String helperPackageName = helperBaseName.substring(0, packageIndex);
		
    	String beanName = e.getSimpleName().toString();
    	String tableName = sqliteBean.table().isEmpty() ? decamelize(beanName) : sqliteBean.table();
    	
    	template.setSourceName(beanName + POST_FIX);
    	template.setSourcePackageName(helperPackageName);
    	template.setBeanPackageName(packageName);
    	template.setHelperName(helperName);
    	template.setHelperPackageName(helperPackageName);
    	template.setBeanName(beanName);
    	template.setTableName(tableName);
    	return super.visitType(e, p);
    }
    @Override
    public Void visitVariable(VariableElement e, Void p) {
    	Column column = e.getAnnotation(Column.class);
    	if (column != null) {
    		String fldType = e.asType().toString();
    		String fldName = e.getSimpleName().toString();
    		String columnName = column.name().isEmpty() ? decamelize(fldName) : column.name();
    		String columnType = column.type();
    		if (columnType.isEmpty()) {
    			if ("int".equals(fldType) 
					|| "java.lang.Integer".equals(fldType)
    				|| "long".equals(fldType)
    				|| "java.lang.Long".equals(fldType)) {
    				columnType = "INTEGER";
    			} else if ("double".equals(fldType) 
        				|| "java.lang.Double".equals(fldType)) {
    				columnType = "REAL";
    			} else if ("byte".equals(fldType)) {
    				columnType = "BLOB";
    			} else {
    				columnType = "TEXT";
    			}
    		}
    		int columnSize = column.size();
    		boolean primary = column.primary();
    		template.addColumn(columnName, columnType, columnSize, fldName, fldType, primary);
    	}

        return super.visitVariable(e, p);
    }
    
    private class BeanSqliteHelperTemplate extends TemplateBase {
    	@Override
    	public String getTemplateName() {
    		return "SqliteBeanHelper.java.mvel";
    	}
    	@Override
    	public void setSourceName(String sourceName) {
    		super.setSourceName(sourceName);
    		putTemplateArg("sourceName", sourceName);
    	}
    	@Override
    	public void setSourcePackageName(String sourcePackageName) {
    		super.setSourcePackageName(sourcePackageName);
    		putTemplateArg("sourcePackageName", sourcePackageName);
    	}
    	public void setBeanPackageName(String beanPackageName) {
    		putTemplateArg("beanPackageName", beanPackageName);
    	}
    	public void setHelperPackageName(String helperPackageName) {
    		putTemplateArg("helperPackageName", helperPackageName);
    	}
    	public void setHelperName(String helperName) {
    		putTemplateArg("helperName", helperName);
    	}
    	public void setBeanName(String beanName) {
    		putTemplateArg("beanName", beanName);
    	}
    	public void setTableName(String tableName) {
    		putTemplateArg("tableName", tableName);
    	}
    	public void addColumn(String name, String type, int size, String fldName, String fldType, boolean primary) {
    		List<Map<String, String>> columnList = (List<Map<String, String>>) getTemplateArg("columnList");
    		if (columnList == null) {
    			columnList = new ArrayList<Map<String,String>>();
    			putTemplateArg("columnList", columnList);
    		}
    		Map<String, String> columnMap = new HashMap<String, String>();
    		columnList.add(columnMap);
    		
    		columnMap.put("name", name);
    		columnMap.put("type", type);
    		columnMap.put("size", size > 0 ? Integer.toString(size) : "");

    		// Templateでset@{fldName}とするため、先頭文字を大文字にする
    		columnMap.put("fldName", fldName.substring(0, 1).toUpperCase() + fldName.substring(1));
    		if ("byte[]".equals(fldType)) {
    			columnMap.put("fldType", "Blob");
    		} else {
        		// パッケージ名削除
    			String csrGetterName = fldType;
    			if (csrGetterName.lastIndexOf(".") != -1) {
    				csrGetterName = csrGetterName.substring(csrGetterName.lastIndexOf(".") + 1);
    			}
        		// Templateでc.get@{fldType}とするため、先頭文字を大文字にする
        		columnMap.put("fldType", csrGetterName.substring(0, 1).toUpperCase() + csrGetterName.substring(1));
    		}
    		
    		// PRIMARY KEY、selectメソッド作成用
    		List<Map<String, String>> primaryList = (List<Map<String, String>>) getTemplateArg("primaryList");
    		if (primaryList == null) {
    			primaryList = new ArrayList<Map<String,String>>();
    			putTemplateArg("primaryList", primaryList);
    		}
    		if (primary) {
    			Map<String, String> primaryMap = new HashMap<String, String>();
    			primaryList.add(primaryMap);
    			// メソッドパラメータに使用
    			primaryMap.put("name", name);
    			primaryMap.put("type", fldType);
    		}
    	}
    }
}
