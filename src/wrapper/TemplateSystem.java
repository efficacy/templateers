package wrapper;

public interface TemplateSystem {
	String getName();
	void putTemplate(String name, String template);
	void putContext(String key, Object value);
	long check(String label, int n, String expected, String tplName);
}