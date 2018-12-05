package org.gt.chat;

import cucumber.api.CucumberOptions;
import cucumber.api.StepDefinitionReporter;
import cucumber.api.junit.Cucumber;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.CucumberFeature;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static java.util.Arrays.asList;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features"},
        plugin = {"pretty", "html:target/cucumber-html-report"},
        glue = "org.gt.chat")
public class RunCukesTest {
    public static void main(String[] args) throws IOException {
        byte exitstatus = run(args, Thread.currentThread().getContextClassLoader());
        System.exit(exitstatus);
    }

    /**
     * Launches the Cucumber-JVM command line.
     *
     * @param argv        runtime options. See details in the {@code cucumber.api.cli.Usage.txt} resource.
     * @param classLoader classloader used to load the runtime
     * @return 0 if execution was successful, 1 if it was not (test failures)
     * @throws IOException if resources couldn't be loaded during the run.
     */
    private static byte run(String[] argv, ClassLoader classLoader) throws IOException {
        RuntimeOptions runtimeOptions = new RuntimeOptions(new ArrayList<String>(asList(argv)));
        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        List<CucumberFeature> features = runtimeOptions.cucumberFeatures(resourceLoader);

        Formatter formatter = runtimeOptions.formatter(classLoader);
        Reporter reporter = runtimeOptions.reporter(classLoader);
        StepDefinitionReporter stepDefinitionReporter = runtimeOptions.stepDefinitionReporter(classLoader);
        cucumber.runtime.Runtime runtime = new cucumber.runtime.Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);

        runtime.getGlue().reportStepDefinitions(stepDefinitionReporter);

        ForkJoinPool forkJoinPool = new ForkJoinPool(java.lang.Runtime.getRuntime().availableProcessors());
        forkJoinPool.execute(() -> features.parallelStream()
                .peek(x -> System.out.println("Executing " + x.getPath()))
                .forEach(feature -> feature.run(formatter, reporter, runtime)));

//        for (CucumberFeature cucumberFeature : features) {
//            System.out.printf("Running feature using custom parallel runner.");
//            cucumberFeature.run(formatter, reporter, runtime);
//        }

        //formatter.done();
        //formatter.close();
        runtime.printSummary();
        return runtime.exitStatus();
    }
}
