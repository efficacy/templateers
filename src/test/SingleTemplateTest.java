package test;

import java.util.Arrays;

public class SingleTemplateTest extends PerformanceTestCase {

	int times = 10000; // 10000

	public void testAbsorbSetupTime() {
		assertEquals("hello", "hello");
	}
	
	public void testPlainText() {
		putTemplate("ugh", "hello there!");
		check("plain text", times, "hello there!", "ugh");
	}

	public void testSingleSubstitution() {
		putContext("name", "Frank");
		putTemplate("ugh", "hello there, ${name}!");

		hapax.putTemplate("ugh", "hello there, {{name}}!");
		
		check("single subst", times, "hello there, Frank!", "ugh");
	}

	public void testIncludeTemplate() {
		putTemplate("name", "Frank");

		stringtree.putTemplate("ugh", "hello there, ${*name}!");
		
		emo.putTemplate("ugh", "hello there, ${*name}!");

		velocity.putTemplate("ugh", "hello there, #include(\"name\")!");

		freemarker.putTemplate("ugh", "hello there, <#include \"name\">!");

		hapax.putTemplate("ugh", "hello there, {{~name}}!"); // Hapax does not seem to have includes

		casper.putTemplate("ugh", "hello there, ${templates.get('name')}!");
		
		check("include", times, "hello there, Frank!", "ugh");
	}

	public void testConditionalSubstitution() {
		putContext("yes", Boolean.TRUE);

		stringtree.putTemplate("ugh", "hello there, ${yes?'Frank':'Margaret'}!");

		emo.putTemplate("ugh", "hello there, ${yes?'Frank':'Margaret'}!");

		velocity.putTemplate("ugh", "hello there, #if($yes)Frank#else#**#Margaret#end!");

		freemarker.putTemplate("ugh", "hello there, <#if yes>Frank<#else>Margaret</#if>!");

		hapax.putTemplate("ugh", "hello there, {{yes}}!"); // Hapax does not seem to have conditioals

		casper.putTemplate("ugh", "hello there, <% if (yes) { $out('Frank'); } else { $out('Margaret'); } %>!");

		check("conditional", times, "hello there, Frank!", "ugh");
	}

	public void testBeanCall() {
		putContext("obj", this);
		putTemplate("ugh", "name=${obj.name}");

		hapax.putTemplate("ugh", "name={{obj.name}}"); // Hapax does not seem to have bean calls

		check("bean call", times, "name=testBeanCall", "ugh");
	}

	public void testIteration() {
		putContext("family", Arrays.asList("Frank", "Margaret", "Elizabeth", "Katherine"));

		stringtree.putTemplate("person", "[${this}]");
		stringtree.putTemplate("ugh", "hello there, ${family*person/','}!");

		emo.putTemplate("person", "[${this}]");
		emo.putTemplate("ugh", "hello there, ${family*person/','}!");

		velocity.putTemplate("ugh", "hello there, #foreach($person in $family)[$person],#end!");

		freemarker.putTemplate("ugh", "hello there, <#list family as person>[${person}]<#if person_has_next>,</#if></#list>!");
		
		hapax.putTemplate("ugh", "{{#family}}[{{family}}]{{#family_separator}},{{/family_separator}}{{/family}}");

		casper.putTemplate("ugh", "hello there, <% n = family.size(); for (i=0; i < n; i++) { $out('[' + family.get(i) + ']'); if (i < n-1) $out(','); } %>!");

		check("iteration", times, "hello there, [Frank],[Margaret],[Elizabeth],[Katherine]!", "ugh");
	}
}
