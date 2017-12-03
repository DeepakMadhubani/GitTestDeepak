package com.macys.stella.attribute.webservice.rest.util;


import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.STATS;
import static org.jbehave.core.reporters.Format.XML;

import java.util.Properties;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory.ResolveToPackagedName;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.runner.RunWith;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public abstract class JbehaveStoryMapper extends JUnitStory {

	private final CrossReference xref = new CrossReference();

	public JbehaveStoryMapper() {
		configuredEmbedder().embedderControls()
		.doGenerateViewAfterStories(true)
		.doIgnoreFailureInStories(true)
		.doIgnoreFailureInView(true)
		.useThreads(1).useStoryTimeoutInSecs(10000);
	}

	@Override
	public Configuration configuration() {
		Class<? extends Embeddable> embeddableClass = this.getClass();
		Properties viewResources = new Properties();
		viewResources.put("decorateNonHtml", "true");
		ParameterConverters parameterConverters = new ParameterConverters();
		parameterConverters
		.addConverters(new ParameterConverters.EnumConverter());
		return new MostUsefulConfiguration()
		.useStoryControls(
				new StoryControls().doDryRun(false)
				.doSkipScenariosAfterFailure(false))
				.useParameterControls(
						new ParameterControls()
						.useDelimiterNamedParameters(true))
						.useStoryLoader(new LoadFromClasspath(embeddableClass))
						.useStoryPathResolver(new UnderscoredCamelCaseResolver())
						.useStoryReporterBuilder(
								new StoryReporterBuilder()
								.withCodeLocation(
										CodeLocations
										.codeLocationFromClass(embeddableClass))
										.withDefaultFormats()
										.withPathResolver(new ResolveToPackagedName())
										.withViewResources(viewResources)
										.withFormats(CONSOLE, STATS, HTML, XML)
										.withFailureTrace(true)
										.withFailureTraceCompression(true)
										.withCrossReference(xref))
										.useParameterConverters(parameterConverters)
										.useStepMonitor(xref.getStepMonitor());
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), this);
	}

}
