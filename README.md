elff-parser-scala
==================

# Purpose
The purpose of this little parser is to provide W3C Extended Log File Format (ELFF) 
Parsing capabilities in Scala (and thus in Spark). Many tools use the ELFF, such 
as Bluecoat.

ELFF comes in many different flavors  The idea is that the either the log file
contains a specification of the fields in the logfile. For example:

```
#Software: SGOS 3.2.4.8
#Version: 1.0
#Date: 2005-04-12 19:56:33
#Fields: date time time-taken c-ip sc-status s-action sc-bytes cs-bytes cs-method cs-uri-scheme cs-host cs-uri-path cs-uri-query cs-username s-hierarchy s-supplier-name rs(Content-Type) cs(User-Agent) sc-filter-result sc-filter-category x-virus-id s-ip s-sitename x-virus-details x-icap-error-code x-icap-error-details
2005-04-12 21:03:45 57358 192.16.170.46 503 TCP_ERR_MISS 1736 617 GET http www.yahoo.com / - - NONE 192.16.170.42 - "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)" DENIED none - 192.16.170.42 SG-HTTP-Service - server_unavailable "Server unavailable: No ICAP server is available to process request."
2005-04-12 22:00:43 74721 192.16.170.45 503 TCP_ERR_MISS 1736 503 GET http www.cnn.com / - - NONE 192.16.170.43 - "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; FunWebProducts-MyWay)" DENIED none - 192.16.170.43 SG-HTTP-Service - server_unavailable "Server unavailable: No ICAP server is available to process request."
``` 

The parser will read the `Field:` line and parse out the fields that are used in the log files.
If no `Field: ` metadata line is present, you have to provide it to the parser. See the 
second unit test for details. If both are provided, the `Field` attribute in the file 
takes precedence.  


This implementation is rudimentary and just basically tested. It is also my first
ever attemp to learn Scala. Comments and suggestion welcome through issues. 

# Building / Test

```
sbt test
sbt package
```

# Contributing

Please file an issue or submit a pull request. Thank you!