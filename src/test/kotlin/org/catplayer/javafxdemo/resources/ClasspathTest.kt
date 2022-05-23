package org.catplayer.javafxdemo.resources

import org.catplayer.javafxdemo.resource.ResourceUtils
import org.junit.jupiter.api.Test

class ClasspathTest {


    @Test
    fun showClassPathTest() {
        ResourceUtils.searchClassNames(
            "org.catplayer.javafxdemo.usage",
            ClasspathTest::class.java.classLoader,
        ) { className, _ ->
            println(className)
        }
    }

    @Test
    fun `get classes name in jar file`() {

        ResourceUtils.searchClassNames(
            "org.intellij.lang.annotations",
            ClasspathTest::class.java.classLoader
        ) { className, _ ->
            println(className)
        }

    }
}