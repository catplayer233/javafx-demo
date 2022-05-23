package org.catplayer.javafxdemo.resource

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.JarFile
import java.util.stream.Collectors
import kotlin.io.path.exists

/**
 * utils to search and get target resources
 */
object ResourceUtils {

    private val logger: Logger = LoggerFactory.getLogger(ResourceUtils::class.java)

    private var CLASS_PATH_ENTRIES: List<ClassPathEntry>? = null

    private val CLASS_PATH_ENTRIES_LOCK = Any()

    private const val CLASS_PATH = "java.class.path"

    private const val PATH_SEPARATOR = "path.separator"

    private const val DEFAULT_SEPARATOR = ":"


    /**
     * search target package's classes' name
     */
    fun searchClassNames(
        packageName: String,
        classLoader: ClassLoader,
        filterPredicate: (String) -> Boolean = { true },
        block: (String, ClassLoader) -> Unit
    ) {
        return loadClassPathEntries().flatMap {
            it.searchClassesName(packageName)
        }.filter(filterPredicate).forEach {
            block(it, classLoader)
        }
    }


    private fun loadClassPathEntries(): List<ClassPathEntry> {

        return (if (CLASS_PATH_ENTRIES == null) {
            synchronized(CLASS_PATH_ENTRIES_LOCK) {
                if (CLASS_PATH_ENTRIES != null) {
                    CLASS_PATH_ENTRIES
                } else {
                    val classPathSeparator = System.getProperty(PATH_SEPARATOR, DEFAULT_SEPARATOR)
                    (System.getProperty(CLASS_PATH)?.split(classPathSeparator)
                        ?.mapNotNull(ResourceUtils::map2ClasspathEntry)
                        ?: emptyList()).apply {
                        CLASS_PATH_ENTRIES = this
                    }
                }
            }
        } else {
            CLASS_PATH_ENTRIES
        }) ?: emptyList()
    }

    private fun map2ClasspathEntry(classPathEntryPath: String): ClassPathEntry? {
        return when {
            classPathEntryPath.endsWith(".jar") -> JarArchiveClassPathEntry(classPathEntryPath)
            !classPathEntryPath.contains(".") -> FileClassPathEntry(classPathEntryPath)
            else -> null
        }
    }


}


internal abstract class ClassPathEntry {

    companion object {
        private const val PERIOD = '.'
    }


    abstract fun searchClassesName(packageName: String): List<String>

    fun cleanPackagePath(packageName: String): String {

        return if (packageName.isEmpty()) {
            packageName
        } else {
            val separatorChar = File.separatorChar
            packageName.replace(PERIOD, separatorChar)
        }
    }

    fun cleanClassFileName(classFileName: String) =
        classFileName.replace(File.separatorChar, PERIOD)


}

internal class FileClassPathEntry(fileRootPath: String) : ClassPathEntry() {

    private val rootPath: String = if (fileRootPath.endsWith(File.separatorChar)) fileRootPath.substring(
        0,
        fileRootPath.length - 1
    ) else fileRootPath

    override fun searchClassesName(packageName: String): List<String> {

        val packageRootPath = Path.of("$rootPath${File.separatorChar}${cleanPackagePath(packageName)}")

        if (!packageRootPath.exists())
            return emptyList()

        return Files.find(
            packageRootPath,
            1,
            { t, _ ->
                t.toFile().let {
                    it.isFile && it.name.endsWith(".class")
                }
            }).use {
            it.map { path ->
                path.toFile().name.let { className ->
                    "$packageName.${className.substring(0, className.length - ".class".length)}"
                }
            }.collect(Collectors.toList())
        }

    }

}

internal class JarArchiveClassPathEntry(jarPath: String) : ClassPathEntry() {

    private val jar: JarFile = JarFile(jarPath)

    override fun searchClassesName(packageName: String): List<String> {

        val packagePath = cleanPackagePath(packageName)

        return jar.stream().filter {
            val entryName = it.name
            entryName.startsWith(packagePath) && entryName.endsWith(".class")
        }.map {
            cleanClassFileName(it.name.substringBefore(".class"))
        }.collect(Collectors.toList())
    }
}

