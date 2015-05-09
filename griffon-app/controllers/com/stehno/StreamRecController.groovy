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
        start must be active for stop to be enabled
     */

    void toggleLimiting(){
        model.streamLimited = !model.streamLimited
    }

    void start() {
        log.info 'Start recording stream ({}) into file ({}).', model.streamUrl, model.recordingFile
        model.recording = true
    }

    void stop() {
        log.info 'Stopped recording.'
        model.recording = false
    }
}