package wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.script.ScriptException;

import com.casper.Casper;
import com.casper.CasperException;

@SuppressWarnings("deprecation") 
public class CasperTemplateSystem extends AbstractTemplateSystem {
	Properties props;
	Map<String, String> templates;
	
	public CasperTemplateSystem() {
		super("casper");
		props = new Properties();
		templates = new HashMap<String, String>();
		props.put("templates", templates);
	}

	@Override protected String expand(String tplName) {
		StringBufferInputStream in = new StringBufferInputStream(templates.get(tplName));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			Casper.eval(in, out, props);
		} catch (CasperException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
        return out.toString();
	}

	@Override public void putContext(String key, Object value) {
        props.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
	   templates.put(name, template);
	}
}
