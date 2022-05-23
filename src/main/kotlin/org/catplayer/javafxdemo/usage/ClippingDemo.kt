package org.catplayer.javafxdemo.usage

import javafx.scene.Scene
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import org.catplayer.javafxdemo.StageHandler

class ClippingDemo : StageHandler {

    override val order: Int
        get() = -8

    override fun handle(stage: Stage, scene: Scene) {

        StackPane().apply {

            border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii(4.0), BorderStroke.THICK))
            children.add(Pane(createShape()).apply {
                setPrefSize(100.0, 100.0)
                clipChildren(this, 4.0)
            })
            scene.root = Pane(this)
        }
    }

    private fun createShape() = Ellipse(50.0, 50.0).apply {
        centerX = 80.0
        centerY = 80.0
        fill = Color.LIGHTCORAL
        stroke = Color.LIGHTCORAL
    }

    private fun clipChildren(region: Region, arc: Double) {
        val outputClip = Rectangle().apply {
            arcWidth = arc
            arcHeight = arc

            region.clip = this
        }

        region.layoutBoundsProperty().addListener { _, _, newValue ->
            outputClip.width = newValue.width
            outputClip.height = newValue.height
        }
    }


}