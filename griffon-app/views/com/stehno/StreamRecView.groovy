package com.stehno
import griffon.core.artifact.GriffonView
import griffon.metadata.ArtifactProviderFor

import javax.swing.SpinnerNumberModel

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
                    textField(
                        text: bind('streamUrl', target: model, mutual: true),
                        preferredSize: [400, 30],
                        enabled: bind { !model.recording }
                    )
                }
                panel {
                    label(text: 'File:', preferredSize: [50, 30], horizontalAlignment: RIGHT)
                    textField(text: bind { model.recordingFile }, preferredSize: [360, 30], enabled: false)
                    button(text: '...', enabled: bind { !model.recording }, selectFileAction)
                }
                panel {
                    gridLayout(rows: 1, cols: 2)
                    panel {
                        checkBox(
                            text: 'Limit to',
                            selected: bind { model.streamLimited },
                            enabled: bind { !model.recording },
                            toggleLimitingAction
                        )
                        spinner(
                            model: new SpinnerNumberModel(0,0,1000,1),
                            preferredSize: [60, 30],
                            value: bind('byteLimit', target:model, mutual: true, converter:{ x-> x * 1024}, reverseConverter:{ x-> x/1024}),
                            enabled: bind { model.streamLimited && !model.recording }
                        )
                        label(text: 'MB')
                    }
                    panel {
                        label(text: 'Recorded:', preferredSize: [75, 30], horizontalAlignment: RIGHT)
                        label(text: '0', preferredSize: [50, 30], horizontalAlignment: RIGHT)
                        label(text: 'MB', preferredSize: [50, 30])
                    }

                }
                panel {
                    button(text: 'Start', enabled: bind { model.valid && !model.recording }, startAction)
                    button(text: 'Stop', enabled: bind { model.recording }, stopAction)
                }
            }
        }
    }
}