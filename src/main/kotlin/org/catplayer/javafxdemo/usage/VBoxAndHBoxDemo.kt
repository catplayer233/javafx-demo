package org.catplayer.javafxdemo.usage

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.Callback
import org.catplayer.javafxdemo.StageHandler

class VBoxAndHBoxDemo : StageHandler {
    override val order: Int
        get() = -6

    override fun handle(stage: Stage, scene: Scene) {

        stage.title = "VBox & HBox Layout demo"

        VBox().apply {
            children.addAll(
                // refresh hyperLink
                HBox().apply {
                    alignment = Pos.BOTTOM_LEFT
                    /*margin property */
                    VBox.setMargin(this, Insets(10.0))
                    children.addAll(
                        Button("Refresh").apply { },
                        HBox().apply {
                            alignment = Pos.BOTTOM_RIGHT
                            children.add(Hyperlink("Sign Out").apply { })
                            /*when the window's width increment, this make sure the "Sign Out" still align to the right*/
                            HBox.setHgrow(this, Priority.ALWAYS)
                        }
                    )
                },
                // table view
                TableView<PersonName>().apply {
                    VBox.setMargin(this, Insets(0.0, 10.0, 10.0, 10.0))
                    columns.addAll(
                        TableColumn<PersonName, String>("Last Name").apply {
                            cellValueFactory = Callback {
                                SimpleObjectProperty(it.value.lastName)
                            }
                        },
                        TableColumn<PersonName, String>("First Name").apply {
                            cellValueFactory = Callback {
                                SimpleObjectProperty(it.value.firstName)
                            }
                        },
                    )
                    items.addAll(
                        PersonName("George", "Washington")
                    )

                    VBox.setVgrow(this, Priority.ALWAYS)
                },
                Separator(),
                // close
                HBox().apply {
                    VBox.setMargin(this, Insets(10.0))
                    alignment = Pos.BOTTOM_RIGHT
                    children.addAll(
                        Button("Close")
                    )
                }
            )

            scene.root = this
        }
    }
}

data class PersonName(val firstName: String, val lastName: String)
