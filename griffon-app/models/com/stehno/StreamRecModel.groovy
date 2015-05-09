package com.stehno

import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Observable
import griffon.transform.PropertyListener

@ArtifactProviderFor(GriffonModel)
class StreamRecModel {

    @Observable @PropertyListener(validator)
    String streamUrl

    @Observable @PropertyListener(validator)
    File recordingFile

    @Observable
    boolean streamLimited = false

    @Observable
    boolean valid = false

    def validator = { evt ->
        valid = streamUrl?.size() > 10 && recordingFile
    }
}