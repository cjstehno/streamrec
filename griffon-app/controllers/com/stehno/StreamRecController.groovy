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

    /*
        url and file must be specified before start is enabled
        start must be active for stop to be enabled
     */

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