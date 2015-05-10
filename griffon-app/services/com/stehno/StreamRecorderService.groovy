package com.stehno

import griffon.core.artifact.GriffonService
import griffon.metadata.ArtifactProviderFor

@ArtifactProviderFor(GriffonService)
class StreamRecorderService {

    void startRecording(URL url, File file, long byteLimit) {
        log.info 'Start recording {} bytes of stream ({}) into file ({}).', (byteLimit ?: 'all'), url, file
    }

    void stopRecording() {
        log.info 'Stopped recording.'
    }
}
