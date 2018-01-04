package http;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;

class RequestLine {
    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

    //private String method;	//요청방식
    private HttpMethod method;
    private String path;	//요청 url
    

    private Map<String, String> params = new HashMap<String, String>();

    RequestLine(String requestLine) {
    	log.debug("request line : {}", requestLine);
    	String[] splited = requestLine.split(" ");	//첫째줄, 요청라인 == 메소드 URL http버전 
    	//(1)URL
    	method = HttpMethod.valueOf(splited[0]);	
    	
    	// (2)path(url) 구하기
    	if(method.isPost())	{
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

    HttpMethod getMethod() {
        return method;
    }

    String getPath() {
        return path;
    }

    Map<String, String> getParams() {
        return params;
    }
}
