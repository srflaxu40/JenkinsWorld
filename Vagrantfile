# -*- mode: ruby -*-
# vi: set ft=ruby :

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.

unless Vagrant.has_plugin?("vagrant-env")
  system("vagrant plugin install vagrant-env")
  puts "vagrant-env installed, please try the command again."
end

unless Vagrant.has_plugin?("vagrant-docker-compose")
  system("vagrant plugin install vagrant-docker-compose")
  puts "vagrant-docker-compose installed, please try the command again."
end

unless Vagrant.has_plugin?("vagrant-docker-login")
  system("vagrant plugin install vagrant-docker-login")
  puts "vagrant-docker-login installed, please try the command again."
end

Vagrant.configure("2") do |config|

  config.env.enable #enabled 

$script = <<SCRIPT

rm /etc/environment
touch /etc/environment
echo "PATH=\"/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games\"" >> /etc/environment

echo "DOCKER_USERNAME=\"#{ENV['DOCKER_USERNAME']}\"" >> /etc/environment
echo "DOCKER_PASSWORD=\"#{ENV['DOCKER_PASSWORD']}\"" >> /etc/environment
echo "P4_SERVER_NAME=\"#{ENV['P4_SERVER_NAME']}\"" >> /etc/environment
echo "P4PASSWORD=\"#{ENV['P4PASSWORD']}\"" >> /etc/environment
SCRIPT

$ansible = <<SCRIPT
sudo apt-get update
sudo apt-get -y install software-properties-common
sudo apt-add-repository ppa:ansible/ansible
sudo apt-get update
sudo apt-get install -y ansible

sudo mkdir /awx-test
sudo chmod 777 /awx-test

SCRIPT

  #config.vm.provision "file", source: "config/elasticsearch.yml", destination: "elasticsearch.yml"

  config.vm.provision "shell", inline: $script, run: "always"
  config.vm.provision "shell", inline: $ansible

  config.vm.provision "file", source: "awx-test/docker-compose.yml", destination: "/awx-test/docker-compose.yml"
  
  config.vm.box = "ubuntu/xenial64"

  config.vm.define "localhost" do |l|
    l.vm.hostname = "localhost"
  end

  config.vm.synced_folder "awx-test/awx_persist/", "/opt/aws_projects"

  config.vm.provision :docker
  config.vm.provision :docker_compose

  config.vm.provision :docker_login, username: ENV['DOCKER_USERNAME'], password: ENV['DOCKER_PASSWORD']

  config.vm.provision :docker_compose, yml: "/awx-test/docker-compose.yml", run: "always"

  config.vm.provision :docker_compose, yml: "/awx-test/docker-compose.yml", run: "restart"

  config.vm.provision "docker" do |d|

    d.pull_images "mattgruter/artifactory:latest"
    d.pull_images "noonien/perforce-server"

  end

  config.vm.provision "file", source: "setup-units.yml", destination: "setup-units.yml"
  config.vm.provision "file", source: "roles", destination: "roles"

  config.vm.provision "ansible_local" do |ansible|

    ansible.extra_vars = {
        DOCKER_USERNAME: ENV['DOCKER_USERNAME'],
        DOCKER_PASSWORD: ENV['DOCKER_PASSWORD']
    }

    ansible.playbook = "setup-units.yml"
    ansible.tags     = ["configure", "deploy"]
    ansible.verbose  = true
    ansible.limit = 'all,localhost'
  end


  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine and only allow access
  # via 127.0.0.1 to disable public access

  # Create a private network, which allows host-only access to the machine
  # using a specific IP.
  config.vm.network "private_network", ip: "10.0.2.15"
  config.vm.network "forwarded_port", guest: 80, host: 8081, host_ip: "127.0.0.1"
  config.vm.network "forwarded_port", guest: 1666, host: 1666, host_ip: "127.0.0.1"

  # Create a public network, which generally matched to bridged network.
  # Bridged networks make the machine appear as another physical device on
  # your network.
  #config.vm.network "public_network"

  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.
  # config.vm.synced_folder "../data", "/vagrant_data"

  config.vm.provider "virtualbox" do |vb|
     vb.gui = false
     vb.memory = 3500
     vb.name = "docker-utils"
   end

end
