package wrapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerTemplateSystem extends AbstractTemplateSystem {

	StringTemplateLoader stringLoader;
    Configuration cfg;
    Map<String, Object> root;
    	
	public FreeMarkerTemplateSystem() {
		super("freemarker");
		cfg = new Configuration();
		stringLoader = new StringTemplateLoader();

	    cfg.setTemplateLoader(stringLoader);
	    cfg.setObjectWrapper(new DefaultObjectWrapper());
	    root = new HashMap<String, Object>();
	}

	@Override protected String expand(String tplName) {
	    StringWriter writer = new StringWriter();
        try {
	        Template temp = cfg.getTemplate(tplName);
			temp.process(root, writer);
	        writer.flush();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return writer.toString();
	}

	@Override public void putContext(String key, Object value) {
        root.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
	   stringLoader.putTemplate(name, template);
	}
}
