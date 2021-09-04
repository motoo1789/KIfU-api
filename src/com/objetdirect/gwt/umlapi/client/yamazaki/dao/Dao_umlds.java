package com.objetdirect.gwt.umlapi.client.yamazaki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.yamazaki.elements.Class_IElemetns;
import com.objetdirect.gwt.umlapi.client.yamazaki.elements.Field_IElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.elements.IElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.elements.Method_IElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.elements.TranseString;
import com.zaxxer.hikari.HikariDataSource;


public class Dao_umlds extends DatabaseAccesser {

	private HikariDataSource hikari;
	private Connection con;
	private Statement stm;

	private String sql;

	private String codeDB;
	private final static String DRIVER_URL="jdbc:mysql://localhost:3306/umlds?Unicode=true&characterEncoding=UTF8";

	private TranseString transestring = TranseString.getInstance();


//	private ArrayList<String> umlClassList= new ArrayList<>(); //UMLのクラスのリスト
//	private HashMap<String, List<String>> umlFieldNameMap = new HashMap<>(); //UMLのフィールドのリスト key=クラス名, value=keyクラスのフィールドのリスト
//	private HashMap<String, List<String>> umlFieldTypeMap = new HashMap<>(); //UMLのフィールドのリスト key=クラス名, value=keyクラスのフィールドのリスト
//	private HashMap<String, List<String>> umlFieldAccessMap = new HashMap<>(); //UMLのフィールドのリスト key=クラス名, value=keyクラスのフィールドのリスト
//
//	private HashMap<String, List<String>> umlMethodNameMap = new HashMap<>(); //UMLのメソッドのリスト key=クラス目, value=keyクラスのメソッドのリスト


	public Dao_umlds()
	{
		con = createHikariConnection(DRIVER_URL);
		System.out.println("getCanvasURL:HikariCPでコネクション生成");
	}
	@Override
	public String getDatabaseElements()
	{
		// TODO 自動生成されたメソッド・スタブ
		getClasstable();
		return codeDB;

	}

	public void showDatabaseData()
	{
//		System.out.println("");
//		for(String key : classObjectMap.keySet())
//		{
//			System.out.println(key);
//			System.out.println("--Class--");
//			System.out.println("クラス名 " + classObjectMap.get(key).getElementName());
//			System.out.println("クラス修飾子 " + classObjectMap.get(key).getAccess());
//			System.out.println("");
//
//			System.out.println("--Field--");
//			List<IElements> fields = fieldObjectMap.get(key);
//			for(IElements field : fields)
//			{
//				System.out.println("フィールド名 " + field.getElementName());
//				System.out.println("型 " + field.getElementType());
//				System.out.println("フィールド修飾子 " + field.getAccess());
//				System.out.println("");
//			}
//
//			System.out.println("--Method--");
//			List<IElements> methods = methodObjectMap.get(key);
//			for(IElements method : methods)
//			{
//				System.out.println("メソッド名 " + method.getElementName());
//				System.out.println("戻り値型 " + method.getElementType());
//				System.out.println("メソッド修飾子 " + method.getAccess());
//				System.out.println("");
//
//				method.showParameter();
//			}
//		}
	}

	public HashMap<String, IElements> getClasstable()
	{
		HashMap<String, IElements> classObjectMap = new HashMap<String, IElements>();
		try
		{
			stm = con.createStatement();
			sql = "select * from class;";

			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				int class_num = rs.getInt("class_num");
				String class_name = rs.getString("class_name");
				int class_access = rs.getInt("class_access");
				// int project_num = rs.getInt("project_num");

				// クラスのマップに入れる
				classObjectMap.put(class_name, new Class_IElemetns(class_access,class_name));
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return classObjectMap;
	}

	public List<IElements> getFieldtable(String className)
	{
		//select * from field,class where class.class_num = field.class_num and class.class_name = "Test2";
		//select * from field where class_num = ( select class_num from class where class_name = "Test2"); selectを中に入れるやつ
		List<IElements> fieldList = new ArrayList<IElements>();
		HashMap<String, List<IElements>> fieldObjectMap = new HashMap<String, List<IElements>>();

		try {
			stm = con.createStatement();
			// sql = "select * from field where class_num = " + String.valueOf(classNum) + ";";
			sql = "select * from field where class_num = ( select class_num from class where class_name = " + "'" + className + "'" + ");";

			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				int field_num = rs.getInt("class_num");
				// int class_num = rs.getInt("class_num");
				int field_access = rs.getInt("field_access");
				String field_name = rs.getString("field_name");
				String field_type = rs.getString("field_type");

				//　型がIとかVとかだから変換
				field_type = transestring.returnTranseType(field_type);

				fieldList.add(new Field_IElements(field_access,field_name,field_type));
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}



		return fieldList;
	}

	public List<IElements> getMethodtable(String className)
	{
		List<IElements> methodList = new ArrayList<IElements>();
		HashMap<String, List<IElements>> methodObjectMap = new HashMap<String, List<IElements>>();

		try {
			stm = con.createStatement();
			//sql = "select * from method where class_num = " + String.valueOf(classNum) + ";";
			sql =  "select * from method where class_num = ( select class_num from class where class_name = " + "'" + className + "'" + ");";
			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				int method_num = rs.getInt("method_num");
				// int class_num = rs.getInt("class_num");
				int method_access = rs.getInt("method_access");
				String method_name = rs.getString("method_name");
				String returnvalue = rs.getString("returnvalue");

				//　型がIとかVとかだから戻り値を変換
				returnvalue = transestring.returnTranseType(returnvalue);

				List<String> paraNameList = getParametaName(method_num);
				List<String> paraTypeList = getParametaType(method_num);

				methodList.add(new Method_IElements(method_access,method_name,returnvalue,paraNameList,paraTypeList));

			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return methodList;

	}

	private List<String> getParametaName(int methodNum)
	{
		List<String> parametarName = new ArrayList<String>();

		try {
			stm = con.createStatement();
			sql = "select * from m_parameta where method_num = " + String.valueOf(methodNum) + ";";
			// パラメタは変えなくても行けそう　sql = "select * from method where class_num = ( select class_num from class where class_name = " + className + ");";

			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				String para_name = rs.getString("para_name");

				parametarName.add(para_name);

			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return parametarName;
	}

	private List<String> getParametaType(int methodNum)
	{
		List<String> parametarType = new ArrayList<String>();

		try {
			stm = con.createStatement();
			sql = "select * from m_parameta where method_num = " + String.valueOf(methodNum) + ";";

			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				String para_type = rs.getString("para_type");

				//　型がIとかVとかだからパラメータの型を変換
				para_type = transestring.returnTranseType(para_type);

				parametarType.add(para_type);

			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return parametarType;
	}

}
