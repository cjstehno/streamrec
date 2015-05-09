application {
    title = 'streamrec'
    startupGroups = ['streamRec']
    autoShutdown = true
}

mvcGroups {
    'streamRec' {
        model = 'com.stehno.StreamRecModel'
        view = 'com.stehno.StreamRecView'
        controller = 'com.stehno.StreamRecController'
    }
}