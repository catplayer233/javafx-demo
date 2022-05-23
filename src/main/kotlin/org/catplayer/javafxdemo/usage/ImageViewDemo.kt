package org.catplayer.javafxdemo.usage

import javafx.geometry.Insets
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.catplayer.javafxdemo.StageHandler

class ImageViewDemo : StageHandler {

    companion object {
        private const val IMAGE_LOCATION = "images/download.png"
    }


    override val order: Int
        get() = -3

    override fun handle(stage: Stage, scene: Scene) {

        stage.title = "ImageViewDemo"

        val imageView1 = ImageView(IMAGE_LOCATION)

        val imageView2 = ImageView(
            Image(IMAGE_LOCATION, 360.0, 360.0, true, true)
        )

        val imageView3 = ImageView(
            Image(IMAGE_LOCATION, 360.0, 360.0, false, true)
        )

        val imageView4 = ImageView(Image(IMAGE_LOCATION)).apply {
            isPreserveRatio = true
            fitHeight = 360.0
            fitWidth = 360.0
            viewport = Rectangle2D(20.0, 59.0, 100.0, 100.0)
        }

        VBox().apply {
            padding = Insets(10.0)

            children.addAll(
                HBox().apply {
                    padding = Insets(20.0)
                    children.addAll(imageView1, imageView2)
                },
                HBox().apply {
                    padding = Insets(20.0)
                    children.addAll(imageView3, imageView4)
                }
            )

            scene.root = this
        }
    }
}