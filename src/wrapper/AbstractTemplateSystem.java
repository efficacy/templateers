package wrapper;

import org.stringtree.timing.StopWatch;

public abstract class AbstractTemplateSystem implements TemplateSystem {

	protected String name;
	protected StopWatch clock;
	protected String result;
	
	public AbstractTemplateSystem(String name) {
		this.name = name;
		this.clock = new StopWatch();
	}

	@Override public long check(String label, int n, String expected, String tplName) {
		clock.reset();
		for (int i = 0; i < n; ++i) {
			result = expand(tplName);
		}
		clock.stop();
		if (!expected.equals(result)) {
System.err.println("expected [" + expected + "] got [" + result + "]");
			System.out.print("[*]");
		}
		return clock.total();
	}

	protected abstract String expand(String tplName);

	@Override public String getName() {
		return name;
	}
}
