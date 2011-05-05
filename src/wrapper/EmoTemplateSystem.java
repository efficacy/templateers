package wrapper;

import java.util.HashMap;
import java.util.Map;

import org.stringtree.context.Context;
import org.stringtree.context.MapContext;
import org.stringtree.solomon.EasySolomon;
import org.stringtree.solomon.Session;

public class EmoTemplateSystem extends AbstractTemplateSystem {
	protected Map<String, String> templates;
	protected Context<Object> context;
	protected Session session;
	protected EasySolomon templater;
	
	public EmoTemplateSystem() {
		super("emo");
		templates = new HashMap<String, String>();
		context = new MapContext<Object>();
		templater = new EasySolomon(new MapContext<String>(templates), context);
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
