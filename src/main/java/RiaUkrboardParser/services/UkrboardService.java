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

import RiaUkrboardParser.dao.ParserDao;
import RiaUkrboardParser.models.Post;
import ch.qos.logback.classic.Logger;

@Service
public class UkrboardService {

	@Autowired
	private ParserDao dao;

	private final Logger log =  (Logger) LoggerFactory.getLogger(UkrboardService.class);
	private final String UKRBOARD_URL = "http://www.ukrboard.com.ua/ua/board/?adv_search=1&q=";
	//private URL f = new URL(UKRBOARD_URL);
	private String page = "&page=";

	public List<Post> getPostListByQuerry(String querry, Integer count) throws IOException, InterruptedException {
		Document doc = null;
		List<Post> postList = new ArrayList<Post>();
		Integer counter = 1;
	
		while (postList.size()<count) {
			
			doc = Jsoup.connect(UKRBOARD_URL+querry+page+counter).get();
			Elements elements = doc.getElementsByAttributeValue("class", "i_l_i_c_mode3 ");
			if (!doc.getElementsByClass("t85").hasText()) {
				elements.forEach(elem->{
				Post post = new Post();
				post.setTicket(querry);
				post.setTitle(elem.select("a[href]").first().text());
				post.setTicketPhotoUrl(elem.select("img[src]").first().attr("src"));
				post.setDefinitionData(elem.select("div[class=i_text no_hide ]").select("a[href]").text());
				post.setTicketUrl(elem.select("a[href]").attr("href"));
				post.setLocation(elem.select("span[class=t9pt descr]").last().text());
				post.setTicketPrice(elem.select("div[class=i_price_c]").text());
				
				postList.add(post);
				
				log.info(String.valueOf(postList.size()));
			});
				log.error("Page is"+counter);
			} else {
				log.error("Break");
				break;
			}
			
			if(counter%10==0)Thread.sleep(6000);
			counter++;
		}
		

		// Element element = doc.title();
		return postList;
	}
}
