package rpc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import db.MySQLDBConnection;
import module.Laundry;

/**
 * Servlet implementation class AllMachineStatus
 */
@WebServlet("/allMachineStatus")
public class AllMachineStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllMachineStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MySQLDBConnection connect = new MySQLDBConnection();
		List<Laundry> list = connect.getAllMachineStatus();
		JSONArray array = new JSONArray();
		for(Laundry landuary: list) {
			array.put(landuary.toJSONObject());
		}
		RpcHelper.writeJsonArray(response, array);
		connect.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
