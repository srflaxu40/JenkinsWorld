import jenkins.model.*
import java.util.logging.Logger

def logger = Logger.getLogger("")
def initialized = false

def pluginArr = ["active-directory",
                 "artifactory",
                 "build-history-metrics-plugin",
                 "dotnet-as-script",
                 "flexible-publish",
                 "git",
                 "github",
                 "global-slack-notifier",
                 "groovy",
                 "groovy-postbuild",
                 "jobConfigHistory",
                 "job-dsl",
                 "nested-view",
                 "naginator",
                 "p4",
                 "rebuild",
                 "role-strategy",
                 "test-results-analyzer",
                 "uno-choice",
                 "unity3d-plugin",
                 "windows-slaves",
                 "workflow-aggregator",
                 "workflow-cps",
                 "workflow-durable-task-step"]

def plugins = pluginArr

logger.info("" + plugins.join(" "))

def instance = Jenkins.getInstance()
def pm = instance.getPluginManager()
def uc = instance.getUpdateCenter()

plugins.each {
  logger.info("Checking " + it)
  if (!pm.getPlugin(it)) {
    logger.info("Looking UpdateCenter for " + it)
    if (!initialized) {
      uc.updateAllSites()
      initialized = true
    }
    def plugin = uc.getPlugin(it)
    if (plugin) {
      logger.info("Installing " + it)
    	def installFuture = plugin.deploy(true)
      while(!installFuture.isDone()) {
        logger.info("Waiting for plugin install: " + it)
        sleep(3000)
      }
    }
  }
}

