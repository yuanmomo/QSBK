package net.yuanmomo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import net.yuanmomo.web.bean.Record;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFWriter {
	public static void write(String path, String fileName, List<Record> records) throws Exception {
		String fileNamePath = null;
		if (path.endsWith("/") || path.endsWith("\\")) {
			fileNamePath = path + fileName;
		} else {
			fileNamePath = path + File.separator + fileName;
		}
		// 创建一个新文档
		Document document = new Document();
		// 根据所给的路径创建一个实例
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fileNamePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// 打开创建的文件流
		document.open();
		// 写入数据
		FontSelector selector = new FontSelector();
		selector.addFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		selector.addFont(FontFactory.getFont("STSongStd-Light", "UniGB-UCS2-H",
				BaseFont.NOT_EMBEDDED));
		
		try {
			for (Record r : records) {
				try {
					Paragraph newParagraph=new Paragraph();
					newParagraph.setSpacingBefore(50);
					newParagraph.setAlignment(Paragraph.ALIGN_LEFT);
					
					//添加一张一列多行的表格，有图片为两行，没有图片为一行
					PdfPTable newTable = new PdfPTable(1);
					newTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
					
					//添加分割线
					PdfPCell separatorLineCell=new PdfPCell();
					Phrase separatorLine = selector.process("*************************************" +
							"********************************\n");
					separatorLineCell.addElement(separatorLine);
					separatorLineCell.setBorderWidth(0);
					separatorLineCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					newTable.addCell(separatorLineCell);
					
					// 添加文字内容
					PdfPCell content=new PdfPCell();
					Phrase ph = selector.process(r.getContent());
					content.addElement(ph);
					content.setBorderWidth(0);
					content.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					newTable.addCell(content);
					
					// 如果有图片，加载图片
					if (r.getHasPicture() == 1) {
						/*
						 * 图像的尺寸和旋转
						 * 
						 * 　如果图像在文档中不按原尺寸显示，可以通过下面的函数进行设定：
						 * 
						 * public void scaleAbsolute(int newWidth, int newHeight)
						 * public void scalePercent(int percent) public void
						 * scalePercent(int percentX, int percentY)
						 * 
						 * 　函数public void scaleAbsolute(int newWidth, int
						 * newHeight)直接设定显示尺寸；函数public void scalePercent(int
						 * percent)
						 * 设定显示比例，如scalePercent(50)表示显示的大小为原尺寸的50%；而函数scalePercent
						 * (int percentX, int percentY)则图像高宽的显示比例。
						 * 
						 * 　如果图像需要旋转一定角度之后在文档中显示，可以通过函数public void
						 * setRotation(double r)设定，参数r为弧度，如果旋转角度为30度，则参数r= Math.PI
						 * /6。
						 */
						Image image = Image.getInstance(r.getPictureLocalPath());
						// 设置图片位置为左对齐
						image.setAlignment(Image.ALIGN_LEFT);
						// 设置图片的位置和尺寸
						// image.setAbsolutePosition(0, 0);
						// image.scaleToFit(PageSize.A4.getHeight(),PageSize.A4.getWidth());
						// 设置图片的显示为原始的50%
						image.scalePercent(50);
						//文字绕图形显示
						//image.setAlignment(Image.TEXTWRAP);
						// System.out.println("A4 height "+PageSize.A4.getHeight());
						// System.out.println("A4 width "+PageSize.A4.getWidth());
						// System.out.println("image width "+image.getWidth());
						// System.out.println("image height "+image.getHeight());
						PdfPCell pic=new PdfPCell();
						pic.addElement(image);
						pic.setBorderWidth(0);
						pic.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
						
						newTable.addCell(pic);
					}
					newParagraph.add(newTable);
					document.add(newParagraph);
				} catch (DocumentException e) {
					e.printStackTrace();
					throw e;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					throw e;
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			document.close();
		}
	}
}
