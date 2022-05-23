package org.catplayer.javafxdemo

import javafx.scene.Scene
import javafx.stage.Stage
import org.catplayer.javafxdemo.resource.ResourceUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object StageHandleAdapter {

    private val logger: Logger = LoggerFactory.getLogger(StageHandleAdapter::class.java)

    private val handlers: TreeSet<StageHandler> = TreeSet()


    init {

        ResourceUtils.searchClassNames(
            "org.catplayer.javafxdemo.usage",
            StageHandleAdapter::class.java.classLoader
        ) { className, classLoader ->
            try {
                val targetClass = classLoader.loadClass(className)

                if (StageHandler::class.java.isAssignableFrom(targetClass)) {
                    val handler = targetClass.getDeclaredConstructor().newInstance() as StageHandler
                    putHandler(handler)
                }

            } catch (e: Exception) {
                logger.error("load class: [$className] failed", e)
            }
        }
    }


    private fun putHandler(vararg handlers: StageHandler) {
        this.handlers.addAll(handlers)
    }


    fun handle(stage: Stage, scene: Scene) {
        return handlers.first().handle(stage, scene)
    }


}