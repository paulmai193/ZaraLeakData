package logia.zara.httpclient;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HttpUnitRequest implements AutoCloseable {

	private static final Logger	LOGGER	= Logger.getLogger(HttpUnitRequest.class);

	private final String		URL;
	private WebClient			web;

	public HttpUnitRequest(String __url) {
		super();
		this.URL = __url;
		web = new WebClient(BrowserVersion.CHROME);
		web.getOptions().setThrowExceptionOnScriptError(false);
		web.getOptions().setThrowExceptionOnFailingStatusCode(false);
		web.getOptions().setJavaScriptEnabled(true);
		web.getOptions().setCssEnabled(true);
		web.getOptions().setRedirectEnabled(true);
		web.getOptions().setDoNotTrackEnabled(true);
	}

	public HtmlPage crawl() throws Exception {
		try {
			return web.getPage(this.URL);
		}
		catch (Exception __ex) {
			LOGGER.error("Error when crawl data from " + this.URL, __ex);
			throw __ex;
		}
	}

	@Override
	public void close() throws Exception {
		web.close();
	}

}
