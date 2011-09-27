package test;

import java.util.Arrays;

public class SingleTemplateTest extends PerformanceTestCase {
	
	public void testAbsorbSetupTime() {
		assertEquals("hello", "hello");
	}
	
	public void testPlainText() {
		putTemplate("ugh", "hello there!");
		check("plain text", 10000, "hello there!", "ugh");
	}

	public void testSingleSubstitution() {
		putContext("name", "Frank");
		putTemplate("ugh", "hello there, ${name}!");
		check("single subst", 10000, "hello there, Frank!", "ugh");
	}

	public void testIncludeTemplate() {
		putTemplate("name", "Frank");

		st.putTemplate("ugh", "hello there, ${*name}!");
		
		emo.putTemplate("ugh", "hello there, ${*name}!");

		vel.putTemplate("ugh", "hello there, #include(\"name\")!");

		fm.putTemplate("ugh", "hello there, <#include \"name\">!");

		casper.putTemplate("ugh", "hello there, ${templates.get('name')}!");
		
		check("include", 10000, "hello there, Frank!", "ugh");
	}

	public void testConditionalSubstitution() {
		putContext("yes", Boolean.TRUE);

		st.putTemplate("ugh", "hello there, ${yes?'Frank':'Margaret'}!");

		emo.putTemplate("ugh", "hello there, ${yes?'Frank':'Margaret'}!");

		vel.putTemplate("ugh", "hello there, #if($yes)Frank#else#**#Margaret#end!");

		fm.putTemplate("ugh", "hello there, <#if yes>Frank<#else>Margaret</#if>!");

		casper.putTemplate("ugh", "hello there, <% if (yes) { $out('Frank'); } else { $out('Margaret'); } %>!");

		check("conditional", 10000, "hello there, Frank!", "ugh");
	}

	public void testBeanCall() {
		putContext("obj", this);
		putTemplate("ugh", "name=${obj.name}");
		check("bean call", 10000, "name=testBeanCall", "ugh");
	}

	public void testIteration() {
		putContext("family", Arrays.asList("Frank", "Margaret", "Elizabeth", "Katherine"));

		st.putTemplate("person", "[${this}]");
		st.putTemplate("ugh", "hello there, ${family*person/','}!");

		emo.putTemplate("person", "[${this}]");
		emo.putTemplate("ugh", "hello there, ${family*person/','}!");

		vel.putTemplate("ugh", "hello there, #foreach($person in $family)[$person],#end!");

		fm.putTemplate("ugh", "hello there, <#list family as person>[${person}]<#if person_has_next>,</#if></#list>!");

		casper.putTemplate("ugh", "hello there, <% n = family.size(); for (i=0; i < n; i++) { $out('[' + family.get(i) + ']'); if (i < n-1) $out(','); } %>!");

		check("iteration", 10000, "hello there, [Frank],[Margaret],[Elizabeth],[Katherine]!", "ugh");
	}
}
