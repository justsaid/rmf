package org.eclipse.rmf.reqif10.pror.genexcel;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.util.CellUtil;

public class ExcelReport {
	private HSSFWorkbook workbook;
	private HSSFFont boldFont;
	private HSSFDataFormat format;


	public ExcelReport(HSSFWorkbook workbook) {
		this.workbook = workbook;
		this.boldFont = workbook.createFont();
		this.boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		this.format = workbook.createDataFormat();
	}
	
	public ExcelReport() {
		this.workbook = new HSSFWorkbook();
		this.boldFont = workbook.createFont();
		this.boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		this.format = workbook.createDataFormat();
	}

	public void addSheet(List<?> data, ExcelReportColumn[] columns, String sheetName) {

		HSSFSheet sheet = workbook.createSheet(sheetName);
		int numCols = columns.length;
		int currentRow = 0;
		HSSFRow row;

		try {

			// create report header at row 0
			row = sheet.createRow(currentRow);
			for (int i = 0; i < numCols; i++) {
				// Get the header text from the bean and write it to the cell
				writeCell(row, i, columns[i].getHeader(), FormatType.TEXT,
						null, this.boldFont);
			}
			currentRow++;

			
			// write report rows
			for (int i = 0; i < data.size(); i++) {
				// create a row in the spreadsheet
				row = sheet.createRow(currentRow++);
				Object bean = data.get(i);
				// For each column object, create a column on the current row
				
				for (int j = 0; j < numCols; j++) {
					Object value = PropertyUtils.getProperty(bean,
							columns[j].getMethod());
					writeCell(row, j, value, columns[j].getType(),
							columns[j].getColor(), columns[j].getFont());
				}
			}

			// auto-size columns
			for (int i = 0; i < numCols; i++) {
				sheet.autoSizeColumn((short) i);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public HSSFFont boldFont() {
		return boldFont;
	}

	public void write(OutputStream outputStream) throws Exception {
		workbook.write(outputStream);
	}

	private void writeCell(HSSFRow row, int col, Object value,
			FormatType formatType, Short bgColor, HSSFFont font)
			throws Exception {

		HSSFCell cell = HSSFCellUtil.createCell(row, col, null);

		if (value == null) {
			return;
		}

		if (font != null) {
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFont(font);
			cell.setCellStyle(style);
		}

		switch (formatType) {
		case TEXT:
			cell.setCellValue(value.toString());
			break;
		case INTEGER:
			cell.setCellValue(((Number) value).intValue());
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					CellUtil.DATA_FORMAT,
					HSSFDataFormat.getBuiltinFormat(("#,##0")));
			break;
		case FLOAT:
			cell.setCellValue(((Number) value).doubleValue());
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					CellUtil.DATA_FORMAT,
					HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
			break;
		case DATE:
			cell.setCellValue((Date) value);
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					CellUtil.DATA_FORMAT,
					HSSFDataFormat.getBuiltinFormat(("m/d/yy")));
			break;
		}

		if (bgColor != null) {
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					CellUtil.FILL_FOREGROUND_COLOR, bgColor);
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
		}

	}

}