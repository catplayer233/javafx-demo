package org.catplayer.javafxdemo.usage

import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import org.catplayer.javafxdemo.StageHandler

class RotateDemo : StageHandler {
    override val order: Int
        get() = Int.MIN_VALUE

    override fun handle(stage: Stage, scene: Scene) {

        scene.root = Pane(transformRectangle())

    }

    private fun transformRectangle(): Rectangle = Rectangle(200.0, 100.0, Color.AQUA).apply {
        translateX = 100.0
        translateY = 200.0

        CoroutineScope(Dispatchers.JavaFx).launch {
            /*
            24帧下旋转360度
            */
            val timeSlice: Long = 1000 / 30

            val rotateSlice: Double = 360.0 / timeSlice

            var count = 0
            while (true) {
                delay(timeSlice)
                count++
                rotate = rotateSlice * count
            }
        }

    }

}