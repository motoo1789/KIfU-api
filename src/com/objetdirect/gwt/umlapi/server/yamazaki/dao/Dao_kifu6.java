package com.objetdirect.gwt.umlapi.server.yamazaki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umlapi.server.yamazaki.elements.Class_IElemetns;
import com.objetdirect.gwt.umlapi.server.yamazaki.elements.Field_IElements;
import com.objetdirect.gwt.umlapi.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umlapi.server.yamazaki.elements.Method_IElements;
import com.objetdirect.gwt.umlapi.server.yamazaki.elements.SplitElement;





public class Dao_kifu6 extends DatabaseAccessorYamazaki
{
	private Connection con;
	private Statement stm;

	private String sql;
	private ResultSet rs;

	final private String STUDENT = "a";

	SplitElement splitObject = SplitElement.getInstance();

//	private ArrayList<String> umlClassList= new ArrayList<>(); //UMLのクラスのリスト
//	private HashMap<String, List<String>> umlFieldMap = new HashMap<>(); //UMLのフィールドのリスト key=クラス名, value=keyクラスのフィールドのリスト
//	private HashMap<String, List<String>> umlMethodMap = new HashMap<>(); //UMLのメソッドのリスト key=クラス目, value=keyクラスのメソッドのリスト

	private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu6?Unicode=true&characterEncoding=UTF8";
	//final private String DATABASE_NAME = "kifu6";

	public Dao_kifu6()
	{
		con = createHikariConnection(DRIVER_URL);
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


	public String getDatabaseElements()
	{

		String codeDB = "";
		System.out.println("getCanvasURL:HikariCPでコネクション生成");

		try {
			stm = con.createStatement();
			sql = "select canvas_url from edit_event where student_id='" + STUDENT + "' and canvas_url is not null order by edit_datetime desc limit 1;";

			rs = stm.executeQuery(sql);

			while(rs.next())
			{
				codeDB = new String(Base64.getDecoder().decode(rs.getString(1)));
			}


		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return codeDB;
	}

	boolean checkNote(String[] splitCodeDB, int index)
	{
		int start = splitCodeDB[index].indexOf("]") + 1;
		int end = splitCodeDB[index].indexOf("$");
		String umltype = splitCodeDB[index].substring(start,end);
		System.out.println("umltype：" + umltype);
		if(umltype.equals("Note"))
			return true;
		else
			return false;
	}

	public int getArtifactId(String[] splitCodeDB, int index)
	{
		int start = splitCodeDB[index].indexOf("<") + 1;
		int end = splitCodeDB[index].indexOf(">");

		int artifactId = Integer.parseInt(splitCodeDB[index].substring(start,end));
		System.out.println("ArtifactId：" + artifactId);

		return artifactId;
	}

	public IElements splitClassName(String[] splitCodeDB,int index)
	{

		// クラス名
		int x = splitCodeDB[index].indexOf("!");
		int y = splitCodeDB[index].indexOf("!",x+1);
		String tmpClassName = "";
		IElements classElemetns = null;
		HashMap<String, IElements> classObjectMap = new HashMap<String, IElements>();

		if(x!=-1 && y!=-1 && !(splitCodeDB[index].substring(x+1, y).contains("<")))
		{
			// umlClassList.add(splitCodeDB[index].substring(x+1, y));
			tmpClassName = splitCodeDB[index].substring(x+1, y).trim();
			System.err.println("クラス追加"+splitCodeDB[index].substring(x+1, y));

			// KIfUのabstractクラスとかの仕様がわからないから取り合えず0を入れておく
			classObjectMap.put(tmpClassName, new Class_IElemetns(0,tmpClassName));
			classElemetns = new Class_IElemetns(0,tmpClassName);
		}

		return classElemetns;
	}

	public List<IElements> splitField(String className,String[] patternURLSplit2)
	{
		// ↓1つのクラスの中の変数の繰り返し
		int start = 0,end = 0;
		boolean add = false;
		List<IElements> fieldList = new ArrayList<IElements>();

		for(int j=0;j<patternURLSplit2.length;j++)
		{
			if(!(patternURLSplit2[j].contains("%") || !(patternURLSplit2[j].contains(":"))))
			{
				continue;
			}else
			{

			}
			if(patternURLSplit2[j].contains("("))
			{
				continue;
			}
			end = patternURLSplit2[j].lastIndexOf("%");
			//System.out.println("変数追加"+index2);
			if(start != -1 && end != -1)
			{
				String[] fields = patternURLSplit2[j].substring(start, end).split("%", 0);


				for(String field : fields)
				{
					field = field.replaceAll(" ","");
					System.out.println("フィールド" + field);

					//フィールドのsplit
					String[] splitField = splitObject.returnSplitField(field);
					int access = splitObject.returnSplitAccess(splitField[0]);

					String fieldName = splitField[0].substring(1,splitField[0].length()).replaceAll(" ","");
					String fieldType = splitField[1].trim();

					fieldList.add(new Field_IElements(access,fieldName,fieldType));
				}
				add = true;
			}
		}
		if(add = false) {
			// 何も書いてなかったら
			//umlFieldMap.put(className, new ArrayList<String>());
		}

		return fieldList;
	}

	public List<IElements> splitMethod(String className, String[] patternURLSplit2)
	{
		// クラスごとの関数一覧を取得
		boolean add = false;
		int index1=0 ,index2=0;
		List<IElements> methodList = new ArrayList<IElements>();

		for(int j = 0;j < patternURLSplit2.length; j++)
		{
			if(!(patternURLSplit2[j].contains("%") || !(patternURLSplit2[j].contains(":"))))
			{
				continue;
			}else {

			}
			if(!(patternURLSplit2[j].contains("(")))
			{
				continue;
			}

			index2 = patternURLSplit2[j].lastIndexOf("%");
			if(index1!=-1 && index2!=-1)
			{
				String[] kifu_method = patternURLSplit2[j].substring(index1, index2).split("%", 0);
				for(String method : kifu_method)
				{
					method = method.replaceAll(" ","");


					//()のインデックス
					int parenthesesStart = method.indexOf("(") + 1;
					int parenthesesEnd = method.indexOf(")");

					//　accecc_and_methodName　(parametar) : returnType
					String parametar = method.substring(parenthesesStart,parenthesesEnd);


					String accecc_and_methodName = method.substring(0,parenthesesStart - 1);
					String returnType = method.substring(parenthesesEnd + 2,method.length());


					int access = splitObject.returnSplitAccess(accecc_and_methodName);
					String methodName = accecc_and_methodName.substring(1,parenthesesStart - 1);

					Map<String,List<String>> parametarMap = splitObject.splitParametarNameType(parametar);

					IElements MethodObject = new Method_IElements(access,methodName,returnType,parametarMap.get("Name"),parametarMap.get("Type"));

					methodList.add(MethodObject);
					add = true;
				}

				add = true;
			}
		}
		if(add = false) {
			// 何も書いてなかったら
			// methodObjectMap.put(className, new ArrayList<String>());
		}

		return methodList;
	}



}
