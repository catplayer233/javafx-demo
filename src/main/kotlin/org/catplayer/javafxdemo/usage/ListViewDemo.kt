package org.catplayer.javafxdemo.usage

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.ListView
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.catplayer.javafxdemo.StageHandler
import java.util.function.Predicate


class ListViewDemo : StageHandler {

    private val players = arrayOf(
        Player("BOS", "David Ortiz"),
        Player("BOS", "Jackie Bradley Jr."),
        Player("BOS", "Xander Bogarts"),
        Player("BOS", "Mookie Betts"),
        Player("HOU", "Jose Altuve"),
        Player("HOU", "Will Harris"),
        Player("WSH", "Max Scherzer"),
        Player("WSH", "Bryce Harper"),
        Player("WSH", "Daniel Murphy"),
        Player("WSH", "Wilson Ramos")
    )

    private val playersProperty = SimpleObjectProperty(FXCollections.observableArrayList<Player>())

    private val viewablePlayersProperty =
        SimpleObjectProperty(FilteredList(playersProperty.get()))

    private val filterProperty = viewablePlayersProperty.get().predicateProperty()


    override val order: Int
        get() = -1

    override fun handle(stage: Stage, scene: Scene) {

        stage.title = "listViewDemo"

        val vBox = VBox().apply {
            padding = Insets(10.0)
            spacing = 4.0
        }

        val hBox = HBox().apply {
            spacing = 2.0
        }

        val filterGroup = ToggleGroup()

        val eventHandler = EventHandler { event: ActionEvent ->
            (event.source as? ToggleButton)?.let {
                (it.userData as? Predicate<Player>).apply {
                    filterProperty.set(this)
                }
            }
        }

        val showAllButton = ToggleButton("Show All").apply {
            isSelected = true
            toggleGroup = filterGroup
            onAction = eventHandler
            userData = Predicate<Player> { true }
        }


        val toggleButtons = players
            .map { it.team }
            .distinct()
            .map { team ->
                ToggleButton(team).apply {
                    toggleGroup = filterGroup
                    onAction = eventHandler
                    userData = Predicate<Player> {
                        it.team == team
                    }
                }
            }

        hBox.children.apply {
            add(showAllButton)
            addAll(toggleButtons)
        }

        val listView = ListView<Player>().apply {
            itemsProperty().bind(viewablePlayersProperty)
        }

        vBox.children.addAll(hBox, listView)

        scene.root = vBox

        stage.onShowing = EventHandler {
            playersProperty.get().addAll(players)
        }
    }
}


data class Player(val team: String, val playerName: String) {
    override fun toString(): String = "$playerName($team)"
}

