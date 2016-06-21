package action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import util.DBUtil;

public class FindAction {
	public static List<HashMap<String, Object>> dealRequest(HttpServletRequest request) throws Exception {
		List<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
		String className = request.getParameter("className");
		StringBuilder sql = new StringBuilder();
		String keys = request.getParameter("keys");
		if(null != keys){
			sql.append("select ");
			boolean haveAll = false;
			JSONArray arr = new JSONArray(keys);
			for(int i = 0,n = arr.length(); i < n; i++){
				String key = arr.getString(i);
				if(key.equals("*")){
					haveAll = true;
				}
				if(i == 0){
					sql.append(key);
				}else{
					sql.append("," + key);
				}
			}
			if(!haveAll){
				sql.append(",id ");
			}
			sql.append(" from " + className + " ");
		}else{
			sql.append("select * ");
			sql.append("from " + className + " ");
		}
		JSONArray arr = new JSONArray(request.getParameter("filters"));
		int length = arr.length();
		if(length > 0){
			sql.append(" where ");
		}
		for(int i = 0; i < length; i++){
			JSONObject obj = arr.getJSONObject(i);
			String tag = obj.getString("tag");
			String key = obj.getString("key");
			Object value = obj.get("value");
			if(i == 0){
				sql.append(key + " " + tag + " ");
			}else{
				sql.append(" and " + key + " " + tag + " ");
			}
			if(value instanceof String && !tag.equals("between")){
				sql.append("'"+ value +"'");
			}else{
				sql.append(value);
			}  
		}
		//orderByÓï¾ä
		if(null != request.getParameter("orderBy")){
			sql.append(" " + request.getParameter("orderBy"));
		}
		//limitÓï¾ä
		if(null != request.getParameter("limit")){
			sql.append(" limit " + request.getParameter("limit"));
		}
		Connection conn = DBUtil.getConnect();
		if(conn != null){
			Statement st = conn.createStatement();
			System.out.println("sql:" + sql.toString());
			ResultSet rs = st.executeQuery(sql.toString());
			while(rs.next()){
				HashMap<String, Object> obj = new HashMap<String, Object>();
				obj.put("id", rs.getString(rs.findColumn("id")));
				obj.put("className", className);
				for(int i = 1,n = rs.getMetaData().getColumnCount(); i<= n;i++){
					if(i == rs.findColumn("id")){
						continue;
					}else{
						obj.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
					}
				}
				res.add(obj);
			}
			st.close();
			conn.close();
		}
		return res;
	}
}
