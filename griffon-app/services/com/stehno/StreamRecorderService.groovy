package com.stehno

import griffon.core.artifact.GriffonService
import griffon.metadata.ArtifactProviderFor

@ArtifactProviderFor(GriffonService)
class StreamRecorderService {

    /*
        connect and start downloading bytes to the file
        update the byte counter
        honor the byte limit, if specified
        allow to be stopped
     */

    void startRecording(URL url, File file, long byteLimit){
        log.info 'Start recording {} bytes of stream ({}) into file ({}).',(byteLimit ?: 'all'), url, file
    }

    void stopRecording(){
        log.info 'Stopped recording.'
    }
}
