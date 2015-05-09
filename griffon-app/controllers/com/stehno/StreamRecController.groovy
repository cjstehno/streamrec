package com.stehno

import griffon.core.artifact.GriffonController
import griffon.metadata.ArtifactProviderFor

import javax.swing.*

import static javax.swing.JFileChooser.APPROVE_OPTION

@ArtifactProviderFor(GriffonController)
class StreamRecController {

    StreamRecModel model

    void selectFile() {
        JFileChooser chooser = new JFileChooser()
        if (chooser.showOpenDialog(null) == APPROVE_OPTION) {
            model.recordingFile = chooser.selectedFile
        }
    }

    void toggleLimiting(){
        model.streamLimited = !model.streamLimited
    }

    void start() {
        log.info 'Started!'
    }

    void stop() {
        log.info 'Stopped!'
    }
}