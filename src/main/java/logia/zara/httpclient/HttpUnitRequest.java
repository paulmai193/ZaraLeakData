package logia.zara.httpclient;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * The Class HttpUnitRequest.
 *
 * @author Paul Mai
 */
public class HttpUnitRequest implements AutoCloseable {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(HttpUnitRequest.class);

	/** The url. */
	private final String        URL;

	/** The web. */
	private WebClient           web;

	/**
	 * Instantiates a new http unit request.
	 *
	 * @param __url the url
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		this.web.close();
	}

	/**
	 * Crawl.
	 *
	 * @return the html page
	 * @throws Exception the exception
	 */
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
