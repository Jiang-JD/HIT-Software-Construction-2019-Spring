package constant;

import java.util.regex.Pattern;

/**
 * Reusable pool to save objects usually use
 *
 */
public class ReusablePool {
	/*
	 * Pattern compile
	 */
	public static Pattern PATTERN_OPERATIONLOG = Pattern.compile(Regex.REGEX_OPERATIONLOG);
	public static Pattern PATTERN_EXCEPTIONLOG = Pattern.compile(Regex.REGEX_EXCEPTIONLOG);
}
