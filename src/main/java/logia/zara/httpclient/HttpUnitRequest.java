package logia.zara.httpclient;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HttpUnitRequest implements AutoCloseable {

	private static final Logger LOGGER = Logger.getLogger(HttpUnitRequest.class);

	private final String        URL;
	private WebClient           web;

	public HttpUnitRequest(String __url) {
		super();
		this.URL = __url;
		this.web = new WebClient(BrowserVersion.CHROME);
		this.web.getOptions().setThrowExceptionOnScriptError(false);
		this.web.getOptions().setThrowExceptionOnFailingStatusCode(false);
		this.web.getOptions().setJavaScriptEnabled(true);
		this.web.getOptions().setCssEnabled(true);
		this.web.getOptions().setRedirectEnabled(true);
		this.web.getOptions().setDoNotTrackEnabled(true);
	}

	@Override
	public void close() throws Exception {
		this.web.close();
	}

	public HtmlPage crawl() throws Exception {
		try {
			return this.web.getPage(this.URL);
		}
		catch (Exception __ex) {
			HttpUnitRequest.LOGGER.error("Error when crawl data from " + this.URL, __ex);
			throw __ex;
		}
	}

}
