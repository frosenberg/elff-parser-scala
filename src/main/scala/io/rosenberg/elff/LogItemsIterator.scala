package io.rosenberg.elff

/**
 * Provides an iterator implementation for parsing W3C ELFF log files.
 * 
 * Based on https://gist.github.com/cveld/23504be02d85bac38c69
 * with fixes for better detection of escape sequences. 
 */
class LogItemsIterator(lines: Iterator[String], fields: Array[String]) extends Iterator[collection.mutable.Map[String, String]] {
	var _nextLine = None: Option[collection.mutable.Map[String, String]];

	var _columns: Array[String] = null;
	var _hasNext = None: Option[Boolean];

	def hasNext(): Boolean = {
		_hasNext match {
			case None => {
				return !nextLine().isEmpty;
			}
			case Some(value) => return value;
		}
	}

	def nextLine(): Option[collection.mutable.Map[String, String]] = {
		var found: String = null;

		while (lines.hasNext && found == null) {
			val line = lines.next();
			if (line.length == 0) {
				found = line;
			}
			if (line.charAt(0) == '#') {
				if (line.startsWith("#Fields: ")) { 
					val allColumns = line.split(" ");
					_columns = allColumns.drop(1); // remove "#Fields"
				} else {
					_columns = fields;
				}
			} else {
				found = line;
			}

		}

		val foundHash = new collection.mutable.HashMap[String, String];
		if (found == null) {
			_hasNext = Some(false);
			_nextLine = None;
			return None;
		}

		// this regex ensure that we parse honor the "" and '' as escape characters 
		// They are used in the user agent for example.
		val regex = "[^\\s\"']+|\"[^\"]*\"|'[^']*'".r
		val _values = regex.findAllIn(found).toList
		if (_values.length == _columns.length) {
			for (idx <- 0 to _values.length - 1) {
				foundHash += _columns(idx) -> _values(idx);
			}
			_hasNext = Some(true);
			_nextLine = Some(foundHash);
			return Some(foundHash);
		} else {
			println("Line parsing unsuccessful: "+ found)
		}
		
		return null;
	}

	def next: collection.mutable.Map[String, String] = {
		if (!hasNext()) {
			throw new NoSuchElementException("next on empty iterator")
		}

		val result = _nextLine;
		nextLine();
		return result.get;
	}
}
