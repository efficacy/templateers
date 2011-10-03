package wrapper;

import java.util.HashMap;
import java.util.Map;

import com.floreysoft.jmte.Engine;

public class JMTETemplateSystem extends AbstractTemplateSystem {
	protected Map<String, String> templates;
	protected Map<String, Object> model;
	protected Engine engine;
	
	public JMTETemplateSystem() {
		super("jmte");
		templates = new HashMap<String, String>();
		model = new HashMap<String, Object>();
		engine = new Engine();
	}

	protected String expand(String tplName) {
		String template = templates.get(tplName);
		return engine.transform(template, model);
	}

	@Override public void putContext(String key, Object value) {
		model.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
		templates.put(name, template);
	}
	
}
