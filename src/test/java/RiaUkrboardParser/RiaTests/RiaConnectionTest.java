package RiaUkrboardParser.RiaTests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class RiaConnectionTest {
	
	@Test
	public void testURL() throws Exception {
	    String strUrl = "https://www.ria.com/uk/advertisement/search/?search_text=%D0%B0%D0%B2%D1%82%D0%BE";

	    try {
	        URL url = new URL(strUrl);
	        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
	        urlConn.connect();
	        System.out.println(urlConn.getHeaderFields());
	        assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
	    } catch (IOException e) {
	        System.err.println("Error creating HTTP connection");
	        e.printStackTrace();
	        throw e;
	    }
	}
}
