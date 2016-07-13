package logia.zara.application;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.text.html.parser.DTD;

public class TestSwing {

	public static void main(String[] args) {
		try {
			URL u = new URL("http://www.java2s.com");
			OutputStream out = new FileOutputStream("/home/logia193/Desktop/zara/test.htm");
			InputStream in = u.openStream();
			DTD html = DTD.getDTD("http://www.java2s.com");
			System.out.println(html.getName());

			in.close();
			out.flush();
			out.close();
		}
		catch (Exception e) {
			System.err.println("Usage: java PageSaver url local_file");
		}
	}

}
