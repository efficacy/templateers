package test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;
import wrapper.EmoTemplateSystem;
import wrapper.FreeMarkerTemplateSystem;
import wrapper.StringtreeTemplateSystem;
import wrapper.TemplateSystem;
import wrapper.VelocityTemplateSystem;

public abstract class PerformanceTestCase extends TestCase {
	protected StringtreeTemplateSystem st;
	protected EmoTemplateSystem emo;
	protected VelocityTemplateSystem vel;
	protected FreeMarkerTemplateSystem fm;
	
	Collection<TemplateSystem> systems;
	
	public void setUp() {
		st = new StringtreeTemplateSystem();
		emo = new EmoTemplateSystem();
		vel = new VelocityTemplateSystem();
		fm = new FreeMarkerTemplateSystem();
		
		systems = new ArrayList<TemplateSystem>(); 
		systems.addAll(Arrays.asList(st, emo, vel, fm));
	}
	
	public void testSystem() {
		Properties system = System.getProperties();
		System.out.println("Running tests using Java " + system.getProperty("java.version") +
				" on " + system.getProperty("os.name") + "(" + system.getProperty("os.arch") + ") " + 
				system.getProperty("os.version"));
	}
	
	protected void putTemplate(String name, String template) {
		for (TemplateSystem system : systems) {
			system.putTemplate(name, template);
		}
	}
	
	protected void putContext(String key, Object value) {
		for (TemplateSystem system : systems) {
			system.putContext(key, value);
		}
	}
	
	protected void check(String label, String expected, String tplName) {
		check(label, 1, expected, tplName);
	}
	
	protected void check(String label, int n, String expected, String tplName) {
		Map<String, Long> results = new LinkedHashMap<String, Long>();
		System.out.print(label);
		System.out.print("(");
		System.out.print(n);
		System.out.print("): ");
		for (TemplateSystem system : systems) {
			String name = system.getName();
			long result = system.check(label, n, expected, tplName);
			System.out.print(name);
			System.out.print("=");
			System.out.print(result);
			System.out.print("  ");
			results.put(name, result);
		}
		System.out.println();
	}
}
