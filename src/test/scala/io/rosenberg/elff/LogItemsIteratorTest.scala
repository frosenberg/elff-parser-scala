package io.rosenberg.elff

import collection.mutable.Stack
import org.scalatest._

class ElffLogParserTest extends FlatSpec with Matchers {

	"The ElffLogParser " should "read 5 log lines with an implicit log format specification" in {

		val source = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/logfile1.txt"));
		val parser = new ElffLogParser(null);
		val it = parser.getLogItems(source.getLines());

		val l = it.next();

		validateLine(l)
		
		it.next() should not be (null);
		it.next() should not be (null);
		it.next() should not be (null);
		it.next() should not be (null);
		
		it.hasNext should be (false);		
	}
	
	"The ElffLogParser " should "read 5 log lines with an explicit log format specification" in {

		val logFields = "date time time-taken c-ip sc-status s-action sc-bytes cs-bytes cs-method cs-uri-scheme cs-host cs-uri-path cs-uri-query cs-username s-hierarchy s-supplier-name rs(Content-Type) cs(User-Agent) sc-filter-result sc-filter-category x-virus-id s-ip s-sitename x-virus-details x-icap-error-code x-icap-error-details".split(" ")
		
		val source = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/logfile1.txt"));
		val parser = new ElffLogParser(logFields);
		val it = parser.getLogItems(source.getLines());

		val l = it.next();

		validateLine(l);
		
		it.next() should not be (null);
		it.next() should not be (null);
		it.next() should not be (null);
		it.next() should not be (null);
		
		it.hasNext should be (false);		
	}
	
	def validateLine(l: scala.collection.mutable.Map[String,String]) {
		l("date") should be("2005-04-12");
		l("time") should be("21:03:45");
		l("time-taken") should be ("57358");
		l("c-ip") should be ("192.16.170.46");
		l("sc-status") should be ("503");
		l("s-action") should be ("TCP_ERR_MISS");
		l("sc-bytes") should be ("1736");
		l("cs-bytes") should be ("617");
		l("cs-method") should be ("GET");
		l("cs-uri-scheme") should be ("http");
		l("cs-host") should be ("www.yahoo.com");
		l("cs-uri-path") should be ("/");
		l("cs-uri-query") should be ("-");
		l("cs-username") should be ("-");
		l("s-hierarchy") should be ("NONE");
		l("s-supplier-name") should be ("192.16.170.42");
		l("rs(Content-Type)") should be ("-");
		l("cs(User-Agent)") should be ("\"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)\"");
		l("sc-filter-result") should be ("DENIED");
		l("sc-filter-category") should be ("none");
		l("x-virus-id") should be ("-");
		l("s-ip") should be ("192.16.170.42");
		l("s-sitename") should be ("SG-HTTP-Service");
		l("x-virus-details") should be ("-");
		l("x-icap-error-code") should be ("server_unavailable");
		l("x-icap-error-details") should be ("\"Server unavailable: No ICAP server is available to process request.\"");
	}
}