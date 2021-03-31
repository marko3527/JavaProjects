package hr.fer.zemris.java.servleti.xls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;


/**
 * Servlet that takes 3 arguments. First argument is one integer(a) that
 * should be within [-100, 100], second argument is also one integer(b) that
 * should be within [-100, 100] and the third argument is integer that 
 * should be within [1,5]. If all of this if satisfied then it makes
 * a excel file for download. Excel file contains n sheets 
 * in each sheet is stored each integer between a and b and in second column
 * the powers of that numbers.
 * 
 * @author Marko
 *
 */
@WebServlet("/powers")
public class ExcelCreatorServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer aValue = req.getParameter("a") == null ? null :
						 Integer.parseInt((String) req.getParameter("a"));
		Integer bValue = req.getParameter("b") == null ? null :
						 Integer.parseInt((String) req.getParameter("b"));
		Integer nValue = req.getParameter("n") == null ? null :
						 Integer.parseInt((String) req.getParameter("n"));
		if(aValue == null) {
			badRequest(req, resp);
		}
		else if(aValue > 100 || aValue < -100) {
			badRequest(req, resp);
		}
		if(bValue == null) {
			badRequest(req, resp);
		}
		else if(bValue > 100 || bValue < -100) {
			badRequest(req, resp);
		}
		if(nValue == null) {
			badRequest(req, resp);
		}
		else if(nValue < 1 || nValue > 5) {
			badRequest(req, resp);
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		for(int i = 0; i < nValue; i++) {
			HSSFSheet sheet = hwb.createSheet("sheet" + i);
			for(int j = aValue, k = 0; j < bValue; j++, k++) {
				HSSFRow row = sheet.createRow(k);
				Cell numberCell = row.createCell(0);
				Cell squaredCell = row.createCell(1);
				numberCell.setCellValue(j);
				squaredCell.setCellValue(Math.pow(j, 2));
			}
		}
		
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			hwb.write(outputStream);
			byte[] outArray = outputStream.toByteArray();
			resp.setContentType("application/ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
			resp.setContentLength(outArray.length);
			OutputStream output = resp.getOutputStream();
			output.write(outArray);
			output.flush();
		} catch (Exception e) {
			
		}
		hwb.close();
		
	}
	
	private void badRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/badRequest.jsp").forward(req, resp);
	}

}
