package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import action.DeleteAction;
import action.FindAction;
import action.GetAction;
import action.SaveAction;
import action.UpdateAction;


/**
 * Servlet implementation class RequestManager
 */
public class RequestManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RequestManager() {
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String action = request.getParameter("action");
		if(action.equals("save")){
			try {
				int id = SaveAction.dealRequest(request);
				if(id == -1){
					pw.write("save failed");
				}else{
					pw.write(id + "");
				}
			} catch (Exception e) {
				e.printStackTrace();
				pw.write(new Gson().toJson(e.getMessage()));
			}
		}
		else if(action.equals("delete")){
			try {
				int count = DeleteAction.dealRequest(request);
				pw.write(new Gson().toJson(count));
			} catch (Exception e) {
				e.printStackTrace();
				pw.write(new Gson().toJson(e.getMessage()));
			}
		}
		else if(action.equals("update")){
			try {
				int count = UpdateAction.dealRequest(request);
				pw.write(new Gson().toJson(count));
			} catch (Exception e) {
				e.printStackTrace();
				pw.write(new Gson().toJson(e.getMessage()));
			}
			
		}
		else if(action.equals("get")){
			try {
				HashMap<String, Object> obj = GetAction.dealRequest(request);
				pw.write(new Gson().toJson(obj));
			} catch (Exception e) {
				e.printStackTrace();
				pw.write(new Gson().toJson(e.getMessage()));
			}
			
		}
		else if(action.equals("find")){
			System.out.println("find");
			try {
				List<HashMap<String, Object>> res = FindAction.dealRequest(request);		
				pw.write(new Gson().toJson(res));
			} catch (Exception e) {
				pw.write(new Gson().toJson(e.getMessage()));
			}
		}
		
		pw.flush();
		pw.close();
	}

}
