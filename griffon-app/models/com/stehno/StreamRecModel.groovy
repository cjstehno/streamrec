package com.stehno

import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Observable
import griffon.transform.PropertyListener

@ArtifactProviderFor(GriffonModel)
class StreamRecModel {

    @Observable @PropertyListener(enabler)
    String streamUrl

    @Observable @PropertyListener(enabler)
    File recordingFile

    @Observable
    boolean streamLimited = false

    @Observable
    long byteLimit

    @Observable
    boolean valid = false

    @Observable
    boolean recording = false

    def enabler = { evt ->
        valid = isValidUrl(streamUrl) && recordingFile
    }

    private static boolean isValidUrl(String url) {
        // this is hacky but fine for this demo
        try {
            return url && new URL(url)
        } catch (ex) {
            return false
        }
    }
}