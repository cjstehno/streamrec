package com.stehno
import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Observable

@ArtifactProviderFor(GriffonModel)
class StreamRecModel {
//    @Observable int clickCount = 0

    @Observable String streamUrl = 'http://'
    @Observable File recordingFile
    @Observable boolean streamLimited = false
}