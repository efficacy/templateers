package wrapper;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;

public class VelocityTemplateSystem extends AbstractTemplateSystem {

	VelocityEngine ve;
    VelocityContext context;
	
	public VelocityTemplateSystem() {
		super("velocity");
	    ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "string");
        ve.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        ve.addProperty("string.resource.loader.modificationCheckInterval", "1");
	    ve.init();
	    context = new VelocityContext();
	}

	@Override protected String expand(String tplName) {
	    Template t = ve.getTemplate(tplName);
	    StringWriter writer = new StringWriter();
	    t.merge( context, writer );
	    return writer.toString();
	}

	@Override public void putContext(String key, Object value) {
	    context.put(key, value);
	}

	@Override public void putTemplate(String name, String template) {
		StringResourceLoader.getRepository().putStringResource(name, template);
	}
}
