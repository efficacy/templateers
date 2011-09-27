package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;
import wrapper.CasperTemplateSystem;
import wrapper.EmoTemplateSystem;
import wrapper.FreeMarkerTemplateSystem;
import wrapper.HapaxTemplateSystem;
import wrapper.StringtreeTemplateSystem;
import wrapper.TemplateSystem;
import wrapper.VelocityTemplateSystem;

public abstract class PerformanceTestCase extends TestCase {
	protected StringtreeTemplateSystem st;
	protected EmoTemplateSystem emo;
	protected VelocityTemplateSystem vel;
	protected FreeMarkerTemplateSystem fm;
	protected HapaxTemplateSystem hapax;
	protected CasperTemplateSystem casper;
	
	Collection<TemplateSystem> systems;
	
	public void setUp() {
		st = new StringtreeTemplateSystem();
		emo = new EmoTemplateSystem();
		vel = new VelocityTemplateSystem();
		fm = new FreeMarkerTemplateSystem();
		hapax = new HapaxTemplateSystem();
		casper = new CasperTemplateSystem();
		
		systems = new ArrayList<TemplateSystem>(); 
		systems.addAll(Arrays.asList(emo, st, fm, vel, hapax, casper));
	}
	
	public void testSystem() {
		Properties system = System.getProperties();
		System.out.println("Running tests using Java " + system.getProperty("java.version") +
				" on " + system.getProperty("os.name") + " (" + system.getProperty("os.arch") + ") " + 
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
