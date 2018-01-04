package http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    public HttpRequest(InputStream is) {
    	try {
    		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
    		String line = br.readLine();
    		if(line == null) {
    			return;
    		}
    		
    		//String url = HttpRequestUtils.getUrl(line);
    		Map<String, String> headers = new HashMap<String, String>();
    		
    		while(!"".equals(line)) {
    			log.debug("header : {}",line);
    			line = br.readLine();
    			String[] headerTokens = line.split(": ");
    			if(headerTokens.length == 2) {
    				headers.put(headerTokens[0], headerTokens[1]);
    			}
    		}
    		log.debug("Content-Length : {}", headers.get("Content-Length"));
    	}catch(Exception e) {
    		e.getStackTrace();
    	}
    }

    public String getMethod() {
        return null;
    }

    public String getPath() {
        return null;
    }

    public String getHeader(String name) {
        return null;
    }

    public String getParameter(String name) {
        return null;
    }
}
