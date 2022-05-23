package org.catplayer.javafxdemo.usage

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.stage.Stage
import javafx.util.Pair
import javafx.util.StringConverter
import org.catplayer.javafxdemo.StageHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ComboBoxDemo : StageHandler {

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(ComboBoxDemo::class.java)

        /**
         * null option than pure null
         */
        private val EMPTY_PAIR = Pair("", "")
    }

    private val account = ComboBox<Pair<String, String>>()

    override val order: Int
        get() = 0

    override fun handle(stage: Stage, scene: Scene) {

        stage.title = "ComboBoxDemo"

        val label = Label("Account")
        account.prefWidth = 200.0
        val button = Button("Save")

        HBox(label, account, button).apply {
            spacing = 10.0
            alignment = Pos.CENTER
            padding = Insets(40.0)
            scene.root = this
        }

        with(account) {
            initCombo()
        }

        button.setOnAction {
            if (account.value == EMPTY_PAIR) {
                logger.warn("no save needed; no item selected")
            } else {
                logger.info("saving: ${account.value}")
            }
        }

    }


    private fun ComboBox<Pair<String, String>>.initCombo() {

        val accountOptions = buildList<Pair<String, String>> {
            add(Pair("Auto Expense", "60000"))
            add(Pair("Interest Expense", "61000"))
            add(Pair("Office Expense", "62000"))
            add(Pair("Salaries Expense", "63000"))
        }

        items.apply {
            add(EMPTY_PAIR)
            addAll(accountOptions)
        }

        value = EMPTY_PAIR

//        cellFactory = Callback {
//            object : ListCell<Pair<String, String>>() {
//                override fun updateItem(item: Pair<String, String>?, empty: Boolean) {
//                    super.updateItem(item, empty)
//                    text = if (empty) {
//                        ""
//                    } else {
//                        item?.key
//                    }
//                }
//            }
//        }

        converter = object : StringConverter<Pair<String, String>>() {
            /**
             * Converts the object provided into its string form.
             * Format of the returned string is defined by the specific converter.
             * @param object the object of type `T` to convert
             * @return a string representation of the object passed in
             */
            override fun toString(`object`: Pair<String, String>?): String? {
                return `object`?.key
            }

            /**
             * Converts the string provided into an object defined by the specific converter.
             * Format of the string and type of the resulting object is defined by the specific converter.
             * @param string the `String` to convert
             * @return an object representation of the string passed in.
             */
            override fun fromString(string: String?): Pair<String, String>? {
                return null
            }
        }

//        buttonCell = cellFactory.call(null)
    }


}