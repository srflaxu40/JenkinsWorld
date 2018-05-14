pipelineJob('build-unity-example-01') {

  def repo = 'https://github.com/srflaxu40/JenkinsWorld'

  description("Pipeline for $repo")

  definition {
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          branches('master')
          scriptPath('pipelines/Jenkinsfile.build-unity-example-01')
          extensions { }
        }
      }
    }
  }
}
