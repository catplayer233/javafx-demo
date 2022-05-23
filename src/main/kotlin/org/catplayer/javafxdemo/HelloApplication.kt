package org.catplayer.javafxdemo

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.catplayer.javafxdemo.usage.*
import java.util.*

class HelloApplication : Application() {


    override fun start(stage: Stage) {

        Scene(VBox(), 1280.0, 720.0).apply {
            stage.scene = this
        }.let {
            StageHandleAdapter.handle(stage, it)
        }
        stage.show()
    }
}


fun main() {
    Application.launch(HelloApplication::class.java)
}

interface StageHandler : Comparable<StageHandler> {

    val order: Int

    fun handle(stage: Stage, scene: Scene)

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: StageHandler): Int {
        return order.compareTo(other.order)
    }
}

