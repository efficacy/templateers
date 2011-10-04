package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;
import wrapper.CasperTemplateSystem;
import wrapper.JangodTemplateSystem;
import wrapper.SolomonTemplateSystem;
import wrapper.FreeMarkerTemplateSystem;
import wrapper.HapaxTemplateSystem;
import wrapper.JMTETemplateSystem;
import wrapper.MustacheJTemplateSystem;
import wrapper.StringTemplateTemplateSystem;
import wrapper.StringtreeTemplateSystem;
import wrapper.TemplateSystem;
import wrapper.VelocityTemplateSystem;

public abstract class PerformanceTestCase extends TestCase {
	protected StringtreeTemplateSystem stringtree;
	protected SolomonTemplateSystem emo;
	protected JMTETemplateSystem jmte;
	protected StringTemplateTemplateSystem stringtemplate;
	protected MustacheJTemplateSystem mustache;
	protected VelocityTemplateSystem velocity;
	protected FreeMarkerTemplateSystem freemarker;
	protected HapaxTemplateSystem hapax;
	protected CasperTemplateSystem casper;
	protected JangodTemplateSystem jangod;
	
	Collection<TemplateSystem> systems;
	
	public void setUp() {
		stringtree = new StringtreeTemplateSystem();
		emo = new SolomonTemplateSystem();
		jmte = new JMTETemplateSystem();
		stringtemplate = new StringTemplateTemplateSystem();
		mustache = new MustacheJTemplateSystem();
		velocity = new VelocityTemplateSystem();
		freemarker = new FreeMarkerTemplateSystem();
		hapax = new HapaxTemplateSystem();
		casper = new CasperTemplateSystem();
		jangod = new JangodTemplateSystem();
		
		systems = new ArrayList<TemplateSystem>(); 
		systems.addAll(Arrays.asList(
				emo,
				stringtree,
				jmte,
				mustache, 
				stringtemplate, 
				freemarker, 
				velocity, 
				hapax, 
				casper,
				jangod
			));
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
