package com.example.demo;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.metrics.ArchitectureMetrics;
import com.tngtech.archunit.library.metrics.ComponentDependencyMetrics;
import com.tngtech.archunit.library.metrics.MetricsComponents;

import java.util.Set;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.example.demo")
public class MyArchitectureTest {

//        @ArchTest
//        static ArchRule services_should_be_prefixed =
//            classes()
//                .that().resideInAPackage("..service..")
//                .and().areAnnotatedWith(MyService.class)
//                .should().haveSimpleNameStartingWith("Service");
//
//        @ArchTest
//        static ArchRule controllers_should_not_have_Gui_in_name =
//            classes()
//                .that().resideInAPackage("..controller..")
//                .should().haveSimpleNameNotContaining("Gui");
//
//        @ArchTest
//        static ArchRule controllers_should_be_suffixed =
//            classes()
//                .that().resideInAPackage("..controller..")
//                .or().areAnnotatedWith(MyController.class)
//                .or().areAssignableTo(AbstractController.class)
//                .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    static ArchRule classes_named_controller_should_be_in_a_controller_package =
        slices().matching("com.example.demo.(*)..").should().notDependOnEachOther();

//    @ArchTest
    public static void rule3(JavaClasses classes) {

        Set<JavaPackage> packages = classes.getPackage("com.example.demo").getSubpackages();

        // These components can also be created in a package agnostic way, compare MetricsComponents.from(..)
        MetricsComponents<JavaClass> components = MetricsComponents.fromPackages(packages);

        ComponentDependencyMetrics metrics = ArchitectureMetrics.componentDependencyMetrics(components);

        System.out.println("Ce: " + metrics.getEfferentCoupling("com.example.demo.customer"));
        System.out.println("Ca: " + metrics.getAfferentCoupling("com.example.demo.customer"));
        System.out.println("I: " + metrics.getInstability("com.example.demo.customer"));
        System.out.println("A: " + metrics.getAbstractness("com.example.demo.customer"));
        System.out.println("D: " + metrics.getNormalizedDistanceFromMainSequence("com.example.demo.customer"));
    }
}
