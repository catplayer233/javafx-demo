package org.catplayer.javafxdemo.usage

import javafx.scene.Scene
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Series
import javafx.stage.Stage
import org.catplayer.javafxdemo.StageHandler


class LineChartDemo : StageHandler {
    override val order: Int
        get() = -4

    override fun handle(stage: Stage, scene: Scene) {

        LineChart(
            NumberAxis("Time Constant", 0.0, 5.0, 1.0),
            NumberAxis("Voltage (Vs)", 0.0, 1.0, 0.1)
        ).apply {

            val series = Series<Number, Number>()
            series.data.add(XYChart.Data(0.0, 0.0))
            series.data.add(XYChart.Data(0.7, 0.5))
            series.data.add(XYChart.Data(1.0, 0.632))
            series.data.add(XYChart.Data(2.0, 0.865))
            series.data.add(XYChart.Data(3.0, 0.95))
            series.data.add(XYChart.Data(4.0, 0.982))
            series.data.add(XYChart.Data(5.0, 0.993))

            data.add(series)
            title = "RC Charging"
            style = "-fc-background-color: lightgray"
            createSymbols = false
            isLegendVisible = false

            scene.root = this
        }


    }
}