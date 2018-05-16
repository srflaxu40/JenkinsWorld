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


pipelineJob('add-jenkins-agent-01') {

  def repo = 'https://github.com/srflaxu40/JenkinsWorld'

  description("Create a new Jenkins agent(s)")

  definition {
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          branches('master')
          scriptPath('pipelines/Jenkinsfile.add-jenkins-agents-01')
          extensions { }
        }
      }
    }
  }
}
