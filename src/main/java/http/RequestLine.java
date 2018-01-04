package http;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;

class RequestLine {
    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

    private String method;
    private String path;

    private Map<String, String> params = new HashMap<String, String>();

    RequestLine(String requestLine) {
    	log.debug("request line : {}", requestLine);
    	String[] splited = requestLine.split(" ");	//첫째줄, 요청라인 == 메소드 URL http버전 
    	//(1)URL
    	method = splited[0];	
    	
    	// (2)path(url) 구하기
    	if(method.equals("POST"))	{
    		path = splited[1];
    		return;
    	}
    	int index = splited[1].indexOf("?");
    	if(index == -1) {
    		//post 방식일 때
    		path = splited[1];
    	} else {
    		//get 방식일 때
    		path = splited[1].substring(0, index);
    		params = HttpRequestUtils.parseQueryString(splited[1].substring(index+1));
    	}
    }

    String getMethod() {
        return method;
    }

    String getPath() {
        return path;
    }

    Map<String, String> getParams() {
        return params;
    }
}
