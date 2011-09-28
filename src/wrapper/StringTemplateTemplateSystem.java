package wrapper;

import java.util.LinkedHashMap;
import java.util.Map;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

public class StringTemplateTemplateSystem extends AbstractTemplateSystem {
	protected StringBuffer defs;
	protected Map<String, Object> context;
	
	public StringTemplateTemplateSystem() {
		super("stringtemplate");
		defs = new StringBuffer();
		context = new LinkedHashMap<String, Object>();
	}

	@Override protected String expand(String tplName) {
		STGroup group = new STGroupString(null, defs.toString(), '<', '>');
        ST tpl = group.getInstanceOf(tplName);
		for (Map.Entry<String, Object> entry : context.entrySet()) {
			tpl.add(entry.getKey(), entry.getValue());
		}
        return tpl.render();
	}

	@Override public void putContext(String key, Object value) {
		context.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
		defs.append(name);
		defs.append("() ::= <<");
		defs.append(template);
		defs.append(">>\r\n");
	}

	public void defineTemplate(String name, String template, String... args) {
		defs.append(name);
		defs.append("(");
		for (int i = 0; i < args.length; ++i) {
			defs.append(args[i]);
			if (i < args.length-1)
				defs.append(',');
		}
		defs.append(") ::= <<");
		defs.append(template);
		defs.append(">>\r\n");
	}
	
}
