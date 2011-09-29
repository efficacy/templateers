package wrapper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.stringtree.util.FileWritingUtils;

import com.sampullara.mustache.Mustache;
import com.sampullara.mustache.MustacheBuilder;
import com.sampullara.mustache.MustacheException;

public class MustacheJTemplateSystem extends AbstractTemplateSystem {
	MustacheBuilder mc;
	File templates;
	Map<String, Object> context;
	Map<String, Mustache> parsed;
	
	static {
		Logger.getLogger(Mustache.class.getName()).setLevel(Level.OFF);
	}
	
	public MustacheJTemplateSystem() {
		super("mustachej");
		templates = new File("data/mustachej/templates");
		templates.mkdirs();
		parsed = new HashMap<String, Mustache>();
		mc = new MustacheBuilder(templates);
		context = new HashMap<String, Object>();
	}

	@Override protected String expand(String tplName) {
		Writer writer = new StringWriter();
		try {
			Mustache mustache = parsed.get(tplName);
	        mustache.execute(writer, context);
		} catch (MustacheException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return writer.toString();
	}

	@Override public void putContext(String key, Object value) {
		context.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
		try {
			File path = new File(templates, name + ".mustache");
			FileWritingUtils.writeFile(path, template);
			parsed.put(name, mc.parseFile(path.getName()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MustacheException e) {
			e.printStackTrace();
		}
	}
	
}
