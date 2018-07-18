package main

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.net.URL
import java.util.*

class Controller : Initializable {

    @FXML
    lateinit var root: BorderPane
    @FXML
    lateinit var content: Pane
    @FXML
    lateinit var right: Pane
    @FXML
    lateinit var left: Pane
    @FXML
    lateinit var center: Pane
    @FXML
    lateinit var bottom: Pane

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        content.setOnDragDetected {
            println("detected x:${it.sceneX} y:${it.sceneY}")
            content.startDragAndDrop(TransferMode.COPY)
            it.consume()
        }
        content.setOnMousePressed {
            println("pressed")
            content.isMouseTransparent = true
            it.consume()
        }
        content.setOnMouseReleased {
            println("released x:${it.sceneX} y:${it.sceneY}")
            content.isMouseTransparent = false
            //(content.parent as Pane).children.remove(content)
            //bottom.children.add(content)
            println((center.parent as Pane).boundsInParent)
            it.consume()
        }

        val eventHandler = EventHandler<MouseDragEvent> {
            println(it.target)
            println(it.gestureSource)
            if (it.eventType == MouseDragEvent.MOUSE_DRAG_RELEASED) {
                ((it.gestureSource as Node).parent as Pane).children.remove(it.gestureSource as Node)
                (it.target as Pane).children.add(it.gestureSource as Node)

            }
            it.consume()
        }

        left.setOnDragDropped { println("drrroped") }

        left.addEventHandler(MouseDragEvent.ANY, eventHandler)
        center.addEventHandler(MouseDragEvent.ANY, eventHandler)
        bottom.addEventHandler(MouseDragEvent.ANY, eventHandler)
        right.addEventHandler(MouseDragEvent.ANY, eventHandler)
    }

    fun onClick(actionEvent: ActionEvent) {
        (content.parent as Pane).children.remove(content)
        val stage = Stage(StageStyle.UNDECORATED)
        stage.scene = Scene(content)
        stage.show()
        //left.children.add(content)
    }

}