ssh-add ~/sshkeys/digital-ocean/do
ansible-vault view ./roles/appservers/vars/credentials.yml
ansible-playbook site.yml --sudo --vault-password-file=/home/tomasz/NetBeansProjects/letsweb-micro/provision/roles/appservers/vars/password.txt