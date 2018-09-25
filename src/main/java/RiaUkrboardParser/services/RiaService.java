package RiaUkrboardParser.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import RiaUkrboardParser.dao.ParserDao;
import RiaUkrboardParser.models.Post;
import ch.qos.logback.classic.Logger;

@Service
public class RiaService {

	@Autowired
	private ParserDao dao;

	private final Logger log = (Logger) LoggerFactory.getLogger(RiaService.class);
	private final String RIA_URL = "https://www.ria.com/uk/advertisement/search/page";
	private final String PAGE = "/";
	private final String QUERRY_TEXT = "/?search_text=";

	public List<Post> getPostListByQuerry(String querry, Integer count) throws IOException, InterruptedException {

		Document doc;
		List<Post> postList = new ArrayList<Post>();
		Integer outOfRange = 0;
		Integer counter = 1;

		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);

		while (postList.size() < count) {
			try {
				HtmlPage page = webClient.getPage(RIA_URL + PAGE + counter + QUERRY_TEXT + querry);
				doc = Jsoup.parse(page.asXml());
				if (postList.isEmpty()) {
					outOfRange = Integer.valueOf(doc.getAllElements().select("span[class=page-item mhide]").last()
							.select("a[class=page-link]").text());
				}
				
				Elements elements = doc.getElementsByClass("content-bar");
				// log.info(elements.outerHtml());
				log.info("Range is: " + outOfRange.toString());

				elements.remove(elements.last());
				if (counter < outOfRange) {
					elements.forEach(elem -> {
						Post post = new Post();
						post.setTicket(querry);
						post.setTitle(elem.select("div[class=tticket]").select("a[href]").last().text());
						post.setTicketPhotoUrl(elem.select("div[class=ticket-photo]").select("a[href]").select("img")
								.first().attr("src"));

						post.setDefinitionData(elem.select("div[class=definition-data]").select("p").first().text());
						post.setTicketUrl(elem.select("a[href]").first().attr("href"));
						post.setLocation(elem.select("div[class=location grey]").text());
						post.setTicketPrice(elem.select("strong[class=price size20]").text());

						postList.add(post);

					});
					log.info("Page is" + counter);
				} else {
					log.info("Break, pages are over");
					break;
				}

				if (counter % 10 == 0)
					Thread.sleep(6000);
				counter++;
			} catch (IOException ex) {
				log.error(ex.getMessage());
			} finally {
				webClient.close();
			}

		}
		return postList;
	}

}
