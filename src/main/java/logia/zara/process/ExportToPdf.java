package logia.zara.process;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The Class ExportToPdf.
 *
 * @author Paul Mai
 */
public class ExportToPdf implements Closeable {

	/** The pdf document. */
	private Document pdfDocument;

	/**
	 * Instantiates a new export to pdf.
	 *
	 * @param __pdfDocument the __pdf document
	 * @throws FileNotFoundException the file not found exception
	 * @throws DocumentException the document exception
	 */
	public ExportToPdf(File __pdfDocument) throws FileNotFoundException, DocumentException {
		this.pdfDocument = new Document();
		PdfWriter.getInstance(this.pdfDocument, new FileOutputStream(__pdfDocument));
		this.pdfDocument.open();
		this.pdfDocument.addTitle(__pdfDocument.getName());
	}

	/**
	 * Adds the image.
	 *
	 * @param __image the __image
	 * @throws DocumentException the document exception
	 */
	public void addImage(Image __image) throws DocumentException {
		Paragraph _paragraph = new Paragraph();
		_paragraph.add(__image);
		this.pdfDocument.add(_paragraph);
	}

	/**
	 * Adds the image.
	 *
	 * @param __link the __link
	 * @throws Exception the exception
	 */
	public void addImage(URL __link) throws Exception {
		// File _tempSource = File.createTempFile("zaraTempSource", null);
		// File _tempTarget = File.createTempFile("zaraTempTarget", null);
		// FileUtils.copyURLToFile(__link, _tempSource);
		//
		// try {
		// ScaleImage.doScale(_tempSource, _tempTarget, 100);
		//
		// Paragraph _paragraph = new Paragraph();
		// Image _image = Image.getInstance(_tempTarget.getAbsolutePath());
		// _paragraph.add(_image);
		// this.pdfDocument.add(_paragraph);
		// }
		// catch (Exception _e) {
		// throw _e;
		// }
		// finally {
		// _tempSource.delete();
		// _tempTarget.delete();
		// }

		Paragraph _paragraph = new Paragraph();
		Image _image = Image.getInstance(__link);
		_image.scalePercent(5);
		_paragraph.add(_image);
		this.pdfDocument.add(_paragraph);
	}

	/**
	 * Adds the link.
	 *
	 * @param __link the __link
	 * @throws DocumentException the document exception
	 */
	public void addLink(URL __link) throws DocumentException {
		Paragraph _paragraph = new Paragraph();
		Anchor _anchor = new Anchor(__link.toString());
		_anchor.setReference(__link.toString());
		_anchor.setFont(new Font(FontFamily.TIMES_ROMAN, 10));
		_paragraph.add(_anchor);
		this.pdfDocument.add(_paragraph);
	}

	/**
	 * Adds the paragraph.
	 *
	 * @param __paragraphText the __paragraph text
	 * @throws DocumentException the document exception
	 */
	public void addParagraph(String __paragraphText) throws DocumentException {
		Paragraph _paragraph = new Paragraph(__paragraphText);
		_paragraph.setFont(new Font(FontFamily.TIMES_ROMAN, 10));
		this.pdfDocument.add(_paragraph);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		this.pdfDocument.close();
	}

}
