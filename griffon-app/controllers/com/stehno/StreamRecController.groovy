package com.stehno

import griffon.core.artifact.GriffonController
import griffon.metadata.ArtifactProviderFor

import javax.inject.Inject
import javax.swing.*

import static javax.swing.JFileChooser.APPROVE_OPTION

@ArtifactProviderFor(GriffonController)
class StreamRecController {

    StreamRecModel model

    @Inject StreamRecorderService streamRecorderService

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
        model.recording = true

        runInsideUIAsync {
            streamRecorderService.startRecording(new URL(model.streamUrl), model.recordingFile, model.byteLimit)
        }
    }

    void stop() {
        model.recording = false

        runInsideUIAsync {
            streamRecorderService.stopRecording()
        }
    }
}