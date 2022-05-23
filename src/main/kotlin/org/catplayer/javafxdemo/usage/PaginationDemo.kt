package org.catplayer.javafxdemo.usage

import javafx.scene.Scene
import javafx.scene.control.Pagination
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.Callback
import org.catplayer.javafxdemo.StageHandler

class PaginationDemo : StageHandler {

    private val imageURLs = arrayOf(
        Image("https://www.bekwam.net/images/bekwam_rc_charging.png"),
        Image("https://www.bekwam.net/images/bekwam_rc_discharging.png"),
        Image("https://www.bekwam.net/images/bekwam_rl_scope.png")
    )


    override val order: Int
        get() = -5

    override fun handle(stage: Stage, scene: Scene) {


        VBox().apply {
            val pagination = Pagination(imageURLs.size, 0).apply {
                pageFactory = Callback {
                    ImageView(imageURLs[it])
                }
            }
            children.add(pagination)

            scene.root = this
        }
    }
}