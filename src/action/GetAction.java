package action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import util.DBUtil;

public class GetAction {
	public static HashMap<String, Object> dealRequest(HttpServletRequest request) throws Exception {
		String className = request.getParameter("className");
		StringBuilder sql = new StringBuilder();
		String keys = request.getParameter("keys");
		
		Connection conn = null;
		if(null != keys){
			sql.append("select id");
			JSONArray arr = new JSONArray(keys);
			for(int i = 0,n = arr.length(); i < n; i++){
				String key = arr.getString(i);
				sql.append("," + key);
			}
			sql.append(" from " + className + " ");
		}else{
			sql.append("select * from" + className + " ");
		}
		sql.append("where id=" + request.getParameter("objectId"));
		
		conn = DBUtil.getConnect();
		if(conn != null){
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql.toString());
			HashMap<String, Object> obj = new HashMap<String, Object>();
			while(rs.next()){
				obj.put("id", rs.getString(rs.findColumn("id")));
				obj.put("className", className);
				for(int i = 1,n = rs.getMetaData().getColumnCount(); i<= n;i++){
					if(i == rs.findColumn("id")){
						continue;
					}else{
						obj.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
					}
				}
			}
			st.close();
			conn.close();
			return obj;
		}
		return null;
	}
}
