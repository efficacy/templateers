package wrapper;

import org.stringtree.Context;
import org.stringtree.context.MapContext;
import org.stringtree.solomon.EasySolomon;
import org.stringtree.solomon.Session;
import org.stringtree.solomon.TemplateCache;

public class SolomonTemplateSystem extends AbstractTemplateSystem {
	protected TemplateCache templates;
	protected Context<String> context;
	protected Session session;
	protected EasySolomon templater;
	
	public SolomonTemplateSystem() {
		super("solomon");
		templates = new TemplateCache();
		context = new MapContext<String>();
		templater = new EasySolomon(templates, context);
		session = new Session();
	}

	protected String expand(String tplName) {
		return templater.toString(tplName, session);
	}

	@Override public void putContext(String key, Object value) {
		context.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
		templates.put(name, template);
	}
	
}
