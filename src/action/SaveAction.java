package action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import util.DBUtil;

public class SaveAction {
	
	public static int dealRequest(HttpServletRequest request) throws Exception {
		String className = request.getParameter("className");
		StringBuilder sql = new StringBuilder();
		
		JSONObject terms=new JSONObject(request.getParameter("data"));
		StringBuilder fileds= new StringBuilder("");
		StringBuilder values= new StringBuilder("");
		Iterator<String> it=terms.keys();
		while(it.hasNext()){
			String key = it.next();
			Object value = terms.get(key);
			if(fileds.length() == 0){
				fileds.append("(" + key);
			}else{
				fileds.append("," + key);
			}
			if(values.length() == 0){
				if(value instanceof String){
					values.append("'"+ value +"'");
				}else{
					values.append(value);
				}
			}else{
				if(value instanceof String){
					values.append(",'" + value +"'");
				}else{
					values.append("," + value);
				}
			}
		}
		//拼接成sql语句
		sql.append("insert into " +  className + " ");
		sql.append(fileds);
		sql.append(") values (");
		sql.append(values);
		sql.append(")");
		
		Connection conn = DBUtil.getConnect();
		Statement st = conn.createStatement();
		st.executeUpdate(sql.toString(), Statement.RETURN_GENERATED_KEYS);
		int autoIncKeyFromApi = -1;  
		ResultSet rs = st.getGeneratedKeys();                                  // 获取自增主键！  

		if (rs.next()) {  
	        autoIncKeyFromApi = rs.getInt(1);  
	    }  
	    rs.close();  
	    rs = null;  
	    return autoIncKeyFromApi;
	}

}
