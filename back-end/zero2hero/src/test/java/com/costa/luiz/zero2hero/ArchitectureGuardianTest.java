package com.costa.luiz.zero2hero;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureGuardianTest {

    private static JavaClasses importedClasses;

    @BeforeAll
    static void setUp() {
        ImportOption ignoreTests = location -> {
            return !location.contains("/test/"); // ignore any URI to sources that contains '/test/'
        };
        importedClasses = new ClassFileImporter().withImportOption(ignoreTests).importPackages("com.costa.luiz.zero2hero");
    }

    @Test
    void webShouldOnlyBeAccessedByWeb() {
        ArchRule serviceRule = classes()
                .that().resideInAPackage("..web..")
                .should().onlyBeAccessed()
                .byAnyPackage("..web..");
        serviceRule.check(importedClasses);
    }

    @Test
    void layeredArchitectureGuardian() {
        Architectures.LayeredArchitecture layeredArchitecture = layeredArchitecture()
                .layer("Controller").definedBy("..web..")
                .layer("Business").definedBy("..business..")
                .layer("Persistence").definedBy("..persistence..")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Business").mayOnlyBeAccessedByLayers("Controller", "Business")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Business");

        layeredArchitecture.check(importedClasses);
    }
}
