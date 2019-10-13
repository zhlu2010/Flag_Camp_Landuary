package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class Reservation
 */
@WebServlet("/Reservation")
public class Reservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reservation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int count = Integer.parseInt(request.getParameter("count"));
		int capacity = Integer.parseInt(request.getParameter("capacity"));
		boolean open = Boolean.parseBoolean(request.getParameter("open"));
		
		ReservationHelper r1 = new ReservationHelper(count, capacity, open);
		
		boolean result = r1.confirmReservation();
		if (result == true) {
			extracted(request, response, "Successful!");
		} else {
			extracted(request, response, "Please try again!");
		}
	}

	private void extracted(HttpServletRequest request, HttpServletResponse response, String toUser)
			throws IOException {
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		response.getWriter().append(toUser).append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
