package wrapper;

import java.util.HashMap;
import java.util.Map;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.SimpleTemplateRegistry;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRegistry;
import org.mvel2.templates.TemplateRuntime;

import com.floreysoft.jmte.Engine;

public class MVELTemplateSystem extends AbstractTemplateSystem {
	protected TemplateRegistry registry;
	protected Map<String, Object> model;
	protected Engine engine;
	
	public MVELTemplateSystem() {
		super("mvel");
		registry = new SimpleTemplateRegistry();
		model = new HashMap<String, Object>();
		engine = new Engine();
	}

	protected String expand(String tplName) {
		CompiledTemplate template = registry.getNamedTemplate(tplName);
		return (String) TemplateRuntime.execute(template, model, registry);
	}

	@Override public void putContext(String key, Object value) {
		model.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
		registry.addNamedTemplate(name, TemplateCompiler.compileTemplate(template));
	}
	
}
