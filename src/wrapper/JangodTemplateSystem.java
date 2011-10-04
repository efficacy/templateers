package wrapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.util.FileWritingUtils;

import net.asfun.jangod.template.TemplateEngine;

public class JangodTemplateSystem extends AbstractTemplateSystem {
	private static final String TPL_ROOT = "data/jangod/templates";
	
	protected File tplRoot;
	protected Map<String, Object> model;
	protected TemplateEngine engine;
	
	public JangodTemplateSystem() {
		super("jangod");
		tplRoot = new File(TPL_ROOT);
		tplRoot.mkdirs();
		model = new HashMap<String, Object>();
		engine = new TemplateEngine();
        engine.getConfiguration().setWorkspace(TPL_ROOT);
	}

	protected String expand(String tplName) {
        try {
			String ret = engine.process(tplName + ".jangod", model);
			return ret.substring(0, ret.length()-1);
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR: " + e.getMessage(); 
		}
		
	}

	@Override public void putContext(String key, Object value) {
		model.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
		try {
			FileWritingUtils.writeFile(new File(tplRoot, name + ".jangod"), template);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
