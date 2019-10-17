package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import business.Proxy;

/**
 * Servlet implementation class StartWashing
 */
@WebServlet("/startWashing")
public class StartWashing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartWashing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession(false);
			JSONObject obj = new JSONObject();
			if(session == null) {
				response.setStatus(403);
				return;
			}
			Proxy proxy = Proxy.getInstance();
			String userId = session.getAttribute("user_id").toString();
			int machineId = Integer.valueOf(request.getParameter("machineId"));
			if(proxy.start(userId, machineId)) {
				obj.put("status", "OK");
			} else {
				obj.put("status", "Machine occupied");
			}
			RpcHelper.writeJsonObject(response, obj);
		} catch(JSONException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
