# JenkinsWorld

Run, and provision against Amazon's Cloud (AWS).

# Development Setup

* This repo expects you've installed [Homebrew](https://brew.sh/).
* Install rbenv with the instructions below.
* This repo expects that you have installed [Docker for Mac](https://docs.docker.com/docker-for-mac/install/) or [Docker for Windows](https://www.docker.com/docker-windows).
* Please also setup your [aws configuration](http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html).

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

3. Activate project
  - `pyenv activate ansible`

* You should now be in the ansible project.  Install your pip modules (one-time only):
```
pip install requirements.txt
```

# Windows:
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
