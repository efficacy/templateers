package wrapper;

import java.util.HashMap;
import java.util.Map;

import org.stringtree.fetcher.MapFetcher;
import org.stringtree.finder.MapStringKeeper;
import org.stringtree.template.EasyTemplater;

public class StringtreeTemplateSystem extends AbstractTemplateSystem {
	protected Map<String, Object> templates;
	protected MapStringKeeper context;
	protected EasyTemplater templater;
	
	public StringtreeTemplateSystem() {
		super("st");
		templates = new HashMap<String, Object>();
		context = new MapStringKeeper();
		templater = new EasyTemplater(new MapFetcher(templates), context);
	}

	@Override protected String expand(String tplName) {
		return templater.toString(tplName);
	}

	@Override public void putContext(String key, Object value) {
		context.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
		templates.put(name, template);
	}
	
}
