package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;
import util.IOUtils;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    
    private RequestLine requestLine;	 //요청라인
    Map<String, String> headers = new HashMap<String, String>();//요청헤드
    Map<String, String> params = new HashMap<String, String>();//쿼리스트링 파라미터

    public HttpRequest(InputStream is) {
    	try {
    		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
    		String line = br.readLine();
    		if(line == null) {
    			return;
    		}
    		//(1),(2)요청라인
    		requestLine = new RequestLine(line);
    		
        	//(3)요청헤더
        	line = br.readLine();	
    		while(!line.equals("")) {
    			log.debug("header : {}",line);
    			String[] headerTokens = line.split(":");	//요청헤더는 필드명:필드값 형태로 이루어져 있음
   				headers.put(headerTokens[0].trim(), headerTokens[1].trim());
    			line = br.readLine();
    		}
    		
    		//(4)요청본문
    		if(getMethod().isPost()) {
    			String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
    			params = HttpRequestUtils.parseQueryString(requestBody);
    		}else {
    			params = requestLine.getParams();
    		}
    	}catch(IOException e) {
    		log.error(e.getMessage());
    	}
    }


    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getParameter(String name) {
        return params.get(name);
    }
}
