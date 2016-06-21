package action;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import util.DBUtil;

public class UpdateAction {
	public static int dealRequest(HttpServletRequest request) throws Exception {
		String className = request.getParameter("className");
		String objectId = request.getParameter("objectId");
		StringBuilder sql = new StringBuilder();
		
		
		JSONObject terms=new JSONObject(request.getParameter("data"));
		StringBuilder fileds= new StringBuilder("");
		Iterator<String> it=terms.keys();
		//∆¥Ω”≥…sql”Ôæ‰
		sql.append("update " +  className + " ");
		while(it.hasNext()){
			String key = it.next();
			Object value = terms.get(key);
			if(fileds.length() == 0){
				if(value instanceof String){
					fileds.append("set " + key + "=" + "'"+ value +"' ");
				}else{
					fileds.append("set " + key + "=" + value + " ");
				}
				
			}else{
				if(value instanceof String){
					fileds.append(", " + key + "=" + "'"+ value +"' ");
				}else{
					fileds.append(", " + key + "=" + value + " ");
				}
			}
		}
		sql.append(fileds);
		sql.append(" where id=" + objectId);
		
		Connection conn = DBUtil.getConnect();
		if(conn != null){
			Statement st = conn.createStatement();
			int count = st.executeUpdate(sql.toString());
			st.close();
			conn.close();
			return count;
		}
		return 0;	
	}

}
