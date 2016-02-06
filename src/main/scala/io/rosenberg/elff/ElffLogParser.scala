package io.rosenberg.elff

import scala.io.Codec
import java.nio.charset.CodingErrorAction

/**
 * The <code>ElffLogParser<code> provides parsing capabilities for the 
 * W3C Extended Log File Format. 
 * 
 * @param fields: optional metadata describing the fields to be parser. If it 
 * is present  in the log file header (e.g., # Fields: ...) then this parameter
 * can be set to <code>null</code>. 
 */
class ElffLogParser(fields: Array[String]) {
	
	/**
	 * Some log files suffer from encoding issues. Setting this to try will try
	 * to replace the encoding with the default UTF-8.
	 */
	private var _fixEncodingError = false;
	
	def this(fields: Array[String], fixEncodingError: Boolean) = {
		this(fields);
		_fixEncodingError = fixEncodingError;
	}
	
	/**
	 * Given log lines it will return a map of W3C ELFF fields to values for each line.
	 */
	def getLogItems(lines: Iterator[String]): Iterator[collection.mutable.Map[String, String]] = {
		// this is to avoid encoding errors that frequently happen in large log files
		if (_fixEncodingError) {
			implicit val codec = Codec("UTF-8")
			codec.onMalformedInput(CodingErrorAction.REPLACE)
			codec.onUnmappableCharacter(CodingErrorAction.REPLACE)
		}
		return new LogItemsIterator(lines, fields)
	}
	
}