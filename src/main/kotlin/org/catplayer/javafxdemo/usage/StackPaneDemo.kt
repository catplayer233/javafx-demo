package org.catplayer.javafxdemo.usage

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import org.catplayer.javafxdemo.StageHandler

class StackPaneDemo : StageHandler {


    override val order: Int
        get() = -7

    override fun handle(stage: Stage, scene: Scene) {

        stage.title = "StackPane Demo"

        scene.root = StackPane().apply {
            children.addAll(
                Rectangle(200.0, 100.0, Color.BLACK),
                Circle(50.0, Color.BLUE),
                Button("Hello StackPane")
            )
        }
    }


}