package brreg.utils.vpoint.util;
import java.io.PrintStream;

/**
 * Filters Xerces warnings from standard output and standard error streams.
 *
 * @author Christopher Simons
 */
public class XercesWarningFilter extends PrintStream {
	private static final String SAXPARSER = "saxparser";
	private static final String PACKAGE_A = "org.apache.xerces.parsers.saxparser:";
	private static final String PACKAGE_B = "org.apache.xerces.jaxp.saxparserimpl";

	private static final String WARNING = "warning";

	private static final String END_SIG = "is not recognized.";
	private static PrintStream STDOUT;
	private static PrintStream STDERR;

	private XercesWarningFilter(PrintStream printStream) {
		super(printStream);
	}

	private static boolean initialized = false;
	public static synchronized void start() {
		if (!initialized) {
			STDOUT = System.out;
			STDERR = System.err;
			initialized = true;
		}
		System.setOut(new XercesWarningFilter(STDOUT));
		System.setErr(new XercesWarningFilter(STDERR));
	}

	public static synchronized void stop() {
		if (initialized) {
			System.setOut(STDOUT);
			System.setErr(STDERR);
		}
	}

	@Override
	public void println(String s) {
		String sLowercase = s.toLowerCase();
			if(!(sLowercase.matches(".*warning.*saxparser.*"))) {
				super.print(s);
			}
		}

	    /*
			if (!((sLowercase.contains(BEGIN_SIGA)|| sLowercase.contains(BEGIN_SIGB)) && sLowercase.contains(END_SIG))) {
			super.print(s);
		}
		*/
	}



