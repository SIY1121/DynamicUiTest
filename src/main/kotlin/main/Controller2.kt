package main

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.input.ClipboardContent
import javafx.scene.input.TransferMode
import java.net.URL
import java.util.*

class Controller2 : Initializable{
    @FXML
    lateinit var source: TextField
    @FXML
    lateinit var target: Label
    @FXML
    lateinit var listView : ListView<String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        listView.items.addAll("1.mp4","2.mp3","3.mp4")

        listView.setOnDragDetected {
            println("drag detected")
            val db  = source.startDragAndDrop(TransferMode.COPY)
            val clipboardContent = ClipboardContent().apply {
                putString(listView.selectionModel.selectedItem)
            }
            db.setContent(clipboardContent)
            it.consume()
        }

        source.setOnDragDetected {
            println("drag detected")
            val db  = source.startDragAndDrop(TransferMode.COPY)
            val clipboardContent = ClipboardContent().apply {
                putString(source.text)
            }
            db.setContent(clipboardContent)
            it.consume()
        }

        target.setOnDragOver {
            println("drag over")
            if(it.dragboard.hasString()){
                it.acceptTransferModes(TransferMode.COPY)
            }
            it.consume()
        }

        target.setOnDragDropped {
            if(it.dragboard.hasString()){
                target.text = it.dragboard.string
                it.isDropCompleted = true
            }
            it.consume()
        }
    }

}