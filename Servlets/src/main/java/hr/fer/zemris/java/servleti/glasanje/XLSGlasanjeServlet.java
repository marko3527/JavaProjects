package hr.fer.zemris.java.servleti.glasanje;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

/**
 * Servlet that makes an excel file with one sheet and two columns.
 * First column contains the names of bands and the second contains
 * the number of votes for that band.
 * 
 * @author Marko
 *
 */
@WebServlet("/glasanje-xls")
public class XLSGlasanjeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String bandsFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		BandsUtil loader = new BandsUtil(Files.readAllLines(Paths.get(bandsFile)));
		
		String resultFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		loader.readVotes(resultFile);
		List<Band> bands = loader.getBands();
		
		HSSFWorkbook workBook = new HSSFWorkbook();
		
		HSSFSheet sheet = workBook.createSheet("results");
		
		for(int i = 0; i < bands.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			Cell cell = row.createCell(0);
			cell.setCellValue(bands.get(i).getName());
			cell = row.createCell(1);
			cell.setCellValue(bands.get(i).getNumberOfVotes());
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
