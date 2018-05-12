# JenkinsWorld

Jenkins on a Windows Master / slave model using Ansible.

Run, and provision Jenkins with examples of bootstrapping jobs as code using init.groovy.d, Job DSL, and Jenkins Pipeline Plugin for 
bad ass automation.

*Plus* - do it all with Ansible AWX, Artifactory, and ELK for testing.

# Table of Contents

   * [JenkinsWorld](#jenkinsworld)
      * [Setup](#setup)
      * [Setting up your local virtualenv](#setting-up-your-local-virtualenv)
      * [Running Vagrant:](#running-vagrant)
      * [Windows:](#windows)


## Setup

* This repo expects you've installed [Homebrew](https://brew.sh/).
* This repo expects that you have installed [Docker for Mac](https://docs.docker.com/docker-for-mac/install/) or [Docker for Windows](https://www.docker.com/docker-windows).

## Setting up your local virtualenv
1. Install [PyEnv](https://github.com/pyenv/pyenv#homebrew-on-mac-os-x)
   - Now install python v2.7.10
     `pyenv install 2.7.10`

2. Install virtualenv
  - `brew install pyenv-virtualenv`
  * Add content to your ~/.bash_profile (one-time only)
```
eval "$(pyenv init -)"
eval "$(pyenv virtualenv-init -)"
```

3. Create a virtualenv
  - `pyenv virtualenv 2.7.10 ansible`

4. Activate project
  - `pyenv activate ansible`

* You should now be in the ansible project.  Install your pip modules (one-time only):
```
pip install requirements.txt
```

## Running Vagrant:
* [Vagrant Installation](https://www.vagrantup.com/docs/installation/).
* Before running vagrant up please create a `.env` file and set the following variables:
```
  1 
  2 # These are for boot up of Vagrant
  3 DOCKER_USERNAME="pdinklage"
  4 DOCKER_PASSWORD="bigdaddy"
  5 
  6 # For Perforce
  7 
  8 P4_SERVER_NAME=test
  9 P4PASSWORD=abc12345678
 10 
```
* `vagrant up`

* You can now browse to the following sites:
* [Artifactory](127.0.0.1)
* [Ansible AWX](127.0.0.1:80) - 127.0.0.1:
  - username: *admin*
  - password: *password*
* [P4 Server](127.0.0.1)

## Windows:
* This requres you install the pywinrm module in requirements.txt
* For windows machines you need to follow the directions outlined in docs/WINDOWS-README.md in order to setup WinRM as a service, and enable basic auth.
* The window-hosts file outlines hosts in order to provision.
* You must ensure ansible fact gathering is enabled (in windows-hosts).
* The following roles support windows provisioning:
  - Jenkins
* Example of provisioning a remote Windows server over WinRM; notice we set our basic auth password for our windows user on the CML; Other auth mechanisms such as Kerberos, AD exist:
```
ansible-playbook -i windows-hosts -e "target=jknepper ansible_password=asdfio12!@" jenkins-master.yml --tags="master" -vvv
```

## Jenkins Master:
* Environment variables to set:
  - MASTER_HOSTNAME - The hostname / Domain Name / IP of your Jenkins Master.
  - AGENT_NAME - The name of your Jenkins Agent; this must be added to your master via Configure -> Manage Nodes -> Add Agent.
  - ansible_password - The password to your WinRM enabled Machine.
  - target - The name of the host you are targeting in your inventory.
  - (optional) MASTER_PORT - This defaults to 8080.
  - (optional) ansible_user - Can be overridden on the CML.

## Jenkins Slave:
* Environment variables to set:
  - MASTER_HOSTNAME - The hostname / Domain Name / IP of your Jenkins Master.
  - AGENT_NAME - The name of your Jenkins Agent; this must be added to your master via Configure -> Manage Nodes -> Add Agent.
  - ansible_password - The password to your WinRM enabled Machine.
  - target - The name of the host you are targeting in your inventory.
  - (optional) MASTER_PORT - This defaults to 8080.
  - (optional) ansible_user - Can be overridden on the CML.

## Quick Commands:
* Provision Jenkins Master:
```
ansible-playbook -i windows-hosts -e "target=jknepper ansible_password=asdfio12!@" jenkins-master.yml --tags="master" -vvv
```

* Provision Jenkins Slave:
```
ansible-playbook -i windows-hosts -e "target=jknepper ansible_password=asdfio12!@ MASTER_HOSTNAME=10.0.0.98 AGENT_NAME=windows-agent-01" jenkins-slave.yml --tags="slave" -vvv
```

