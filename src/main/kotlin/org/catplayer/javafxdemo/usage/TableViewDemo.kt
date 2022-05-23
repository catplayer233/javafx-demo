package org.catplayer.javafxdemo.usage

import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.catplayer.javafxdemo.StageHandler


class TableViewDemo : StageHandler {


    override val order: Int
        get() = -2

    override fun handle(stage: Stage, scene: Scene) {

        stage.title = "TableViewDemo"

        VBox().apply {

            padding = Insets(10.0)
            spacing = 10.0

            val tableView = TableView<Item>().apply {
                columns.addAll(
                    TableColumn<Item, String>("SKU").apply {
                        cellValueFactory = PropertyValueFactory("sku")
                    },
                    TableColumn<Item, String>("Item").apply {
                        cellValueFactory = PropertyValueFactory("desc")
                    },
                    TableColumn<Item, String>("Price").apply {
                        cellValueFactory = PropertyValueFactory("price")
                    },
                    TableColumn<Item, String>("Tax").apply {
                        cellValueFactory = PropertyValueFactory("taxable")
                    },
                )

                items.addAll(
                    Item("KBD-0455892", "Mechanical Keyboard", 100.0f, true),
                    Item("145256", "Product Docs", 0.0f, false),
                    Item("OR-198975", "O-Ring (100)", 10.0f, true)
                )
            }

            val hBox = HBox().apply {
                spacing = 8.0

                children.apply {
                    addAll(
                        Button("Inventory").apply {
                            disableProperty().bind(tableView.selectionModel.selectedItemProperty().isNull)
                        },

                        Button("Tax").apply {
                            disableProperty().bind(
                                tableView.selectionModel.selectedItemProperty().isNull
                                    .or(
                                        Bindings.select<Boolean>(
                                            tableView.selectionModel.selectedItemProperty(),
                                            "taxable"
                                        ).isEqualTo(false)
                                    )
                            )
                        },
                    )
                }
            }

            children.addAll(tableView, hBox)

            scene.root = this
        }


    }
}


data class Item(val sku: String, val desc: String, val price: Float, val taxable: Boolean)