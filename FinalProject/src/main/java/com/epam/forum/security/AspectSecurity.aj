package com.epam.forum.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public aspect AspectSecurity {

	private static Logger logger = LogManager.getLogger();

	pointcut dbWrite(String query): (
			   call(* java.sql.Statement.addBatch(String))
			|| call(* java.sql.Statement.execute(String))
			|| call(* java.sql.Statement.executeQuery(String))			
			|| call(* java.sql.Statement.executeUpdate(String)))
			&& args(query);

	pointcut getParameter(): call(String
			javax.servlet.http.HttpServletRequest.getParameter(String));

	Object around(String query): dbWrite(query){
		logger.info("query before execute: {} ", query);
		Object ret = (proceed(query));
		return ret;
	}

	String around(): getParameter(){
		String parameterValue = proceed();
		logger.info("before sanitization: {} ", parameterValue);
		if (parameterValue != null) {
			Document.OutputSettings outputSettings = new Document.OutputSettings();
			outputSettings.prettyPrint(false);
			parameterValue = Jsoup.clean(parameterValue, "", Whitelist.relaxed(), outputSettings);
			parameterValue = parameterValue.strip();
		}
		logger.info("after sanitization: {} ", parameterValue);
		return parameterValue;
	}
}
