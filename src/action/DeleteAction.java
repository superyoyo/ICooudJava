package action;

import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import util.DBUtil;

public class DeleteAction {
	public static int dealRequest(HttpServletRequest request) throws Exception {
		String className = request.getParameter("className");
		
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(className);
		sql.append(" ");
		sql.append("where id = " + request.getParameter("objectId"));
		Connection conn = DBUtil.getConnect();
		Statement st = conn.createStatement();
		int count = st.executeUpdate(sql.toString());
		return count;
	}
}
