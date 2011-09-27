package wrapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.xfltr.hapax.Template;
import com.xfltr.hapax.TemplateDictionary;
import com.xfltr.hapax.TemplateException;
import com.xfltr.hapax.parser.TemplateParserException;

public class HapaxTemplateSystem extends AbstractTemplateSystem {
	Map<String, String> templates;
	TemplateDictionary dict;
	
	public HapaxTemplateSystem() {
		super("hapax");
		templates = new HashMap<String, String>();
		dict = TemplateDictionary.create();
	}

	@Override protected String expand(String tplName) {
		String ret="??";
		try {
			String template = templates.get(tplName);
			if (null != template) {
				Template tmpl = Template.parse(template);
				ret = tmpl.renderToString(dict);
			}
		} catch (TemplateParserException e) {
			ret = "Error: " + e.getMessage();
		} catch (TemplateException e) {
			ret = "Error: " + e.getMessage();
		}
		
		return ret;
	}
	
	private TemplateDictionary putChildContext(TemplateDictionary dict, String key, Object value) {
		if (value instanceof Collection) {
			for (Object object : (Collection<?>)value) {
				TemplateDictionary child = putChildContext(dict.addChildDict(key), key, object);
				child.showSection(key + "_separator");
			}
			dict.showSection(key);
		} else if (value instanceof String) {
			dict.put(key, (String)value);
		} else {
			dict.put(key, value.toString());
		}
        return dict;
	}

	@Override public void putContext(String key, Object value) {
		putChildContext(dict, key, value);
	}

	@Override public void putTemplate(String name, String template) {
	   templates.put(name, template);
	}
}
