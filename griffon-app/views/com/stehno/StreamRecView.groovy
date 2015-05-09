package com.stehno

import griffon.core.artifact.GriffonView
import griffon.metadata.ArtifactProviderFor

import static javax.swing.SwingConstants.RIGHT

@ArtifactProviderFor(GriffonView)
class StreamRecView {

    FactoryBuilderSupport builder
    StreamRecModel model

    @SuppressWarnings('GroovyAssignabilityCheck')
    void initUI() {
        builder.with {
            application(
                size: [475, 200],
                resizable: false,
                id: 'mainWindow',
                title: application.configuration['application.title'],
                iconImage: imageIcon('/griffon-icon-48x48.png').image,
                iconImages: [
                    imageIcon('/griffon-icon-48x48.png').image,
                    imageIcon('/griffon-icon-32x32.png').image,
                    imageIcon('/griffon-icon-16x16.png').image]
            ) {
                gridLayout(rows: 4, cols: 1)
                panel {
                    label(text: 'Stream:', preferredSize: [50, 30], horizontalAlignment: RIGHT)
                    textField(text: bind { model.streamUrl }, preferredSize: [400, 30])
                }
                panel {
                    label(text: 'File:', preferredSize: [50, 30], horizontalAlignment: RIGHT)
                    textField(text: bind { model.recordingFile }, preferredSize: [360, 30], enabled: false)
                    button(text: '...', selectFileAction)
                }
                panel {
                    gridLayout(rows: 1, cols: 2)
                    panel {
                        checkBox()
                        label(text: 'Limit to ')
                        spinner(preferredSize: [50, 30], enabled: false)
                        label(text: 'MB')
                    }
                    panel {
                        label(text: 'Recorded:', preferredSize: [75, 30], horizontalAlignment: RIGHT)
                        label(text: '0', preferredSize: [50, 30], horizontalAlignment: RIGHT)
                        label(text: 'MB', preferredSize: [50, 30])
                    }

                }
                panel {
                    button(text: 'Start', enabled: false, startAction)
                    button(text: 'Stop', enabled: false, stopAction)
                }
            }
        }
    }
}