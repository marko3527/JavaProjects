package hr.fer.zemris.java.voting.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import hr.fer.zemris.java.voting.Option;
import hr.fer.zemris.java.voting.dao.DAOProvider;

/**
 * Servlet that makes an excel file with one sheet and two columns.
 * First column contains the names of options and the second contains
 * the number of votes for that option.
 * 
 * @author Marko
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class XLSGlasanjeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int pollId = (int)req.getServletContext().getAttribute("pollId");
		List<Option> options = DAOProvider.getDAO().getOptionList(pollId);
		
		HSSFWorkbook workBook = new HSSFWorkbook();
		
		HSSFSheet sheet = workBook.createSheet("results");
		
		for(int i = 0; i < options.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			Cell cell = row.createCell(0);
			cell.setCellValue(options.get(i).getName());
			cell = row.createCell(1);
			cell.setCellValue(options.get(i).getNumberOfVotes());
		}
		
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workBook.write(outputStream);
			byte[] outArray = outputStream.toByteArray();
			resp.setContentType("application/ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
			resp.setContentLength(outArray.length);
			OutputStream output = resp.getOutputStream();
			output.write(outArray);
			output.flush();
		} catch (Exception e) {
			
		}
		workBook.close();
		
	}
	
	

}
