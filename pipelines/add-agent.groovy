#!/usr/bin/env groovy


/**
 * This script is meant to be executed by a parameterized job in Jenkins and will then create new agents (slaves) as per the parameters
 *
 * SUGGESTED PAIRED PARAMETERS IN JENKINS (type, name, default values, description):
 * String - AgentName
 * String - AgentDescription - "Auto-created Jenkins agent" - Description that'll be set for _every_ created agent
 * String - AgentHome - "D:\JenkinsAgent" - Remote filesystem root for the agent
 * String - AgentExecutors - 2 - Number of executors for the agent
 */

import hudson.model.Node.Mode
import hudson.slaves.*
import jenkins.model.*


def addNode (String AgentHome, String AgentExecutors, String AgentDescription, String AgentName) {

    // There is a constructor that also takes a list of properties (env vars) at the end, but haven't needed that yet
    DumbSlave dumb = new DumbSlave(AgentName,  // Agent name, usually matches the host computer's machine name
            AgentDescription,           // Agent description
            AgentHome,                  // Workspace on the agent's computer
            AgentExecutors,             // Number of executors
            Mode.EXCLUSIVE,             // "Usage" field, EXCLUSIVE is "only tied to node", NORMAL is "any"
            AgentName,                         // Labels
            new JNLPLauncher(),         // Launch strategy, JNLP is the Java Web Start setting services use
            RetentionStrategy.INSTANCE) // Is the "Availability" field and INSTANCE means "Always"

    Jenkins.instance.addNode(dumb)
    println "Agent '$it' created with $agentExecutors executors and home '$agentHome'"

}
/*
Jenkins.instance.nodes.each {
    println "AFTER - Agent: $it"
}
*/

