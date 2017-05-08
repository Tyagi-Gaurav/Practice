### Enable JPDA Debugger
- For Maven Jetty: export
```
 MAVEN_OPTS="-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"
 export MAVEN_OPTS="-Xmx3000m -XX:MaxPermSize=256m"
 ```
### Maven
- Show dependency Tree:
```
mvn dependency:tree
```
- Maven Debugging:
```
set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=4000
```
- Cobertura-maven-plugin
```
mvn cobertura:clean cobertura:cobertura
```
- pmd
```
mvn pmd:check
```

### Android
- Install on Emulator
```
adb -s emulator-5554 install <apk_file_path>
```
- Uninstall From Emulator
```
adb -s emulator-5554 uninstall <app Name> -- Get App Name from "adb shell, cd data/apps"
```

### Cygwin
- Have Cygwin generate real core dump:  
```
export CYGWIN="$CYGWIN error_start=dumper -d %1 %2"
```

### Static Code Analysis
- FindBugs GUI
```
java -jar $env:FINDBUGS_HOME\lib\findbugs.jar -onlyAnalyze
```

### Keystores
- Print Certificates in Keystore
```
keytool -list -keystore <keyStoreFile>
```

### Networking/Hardware/OS
- DOS: Get Process listening on a port
```
netstat -ano | grep 61616
```
- Linux
```
netstat -tulpn | grep 61616 | awk ‘{print $NF}’ | awk -F ‘/‘ ‘{print $1}’ | xargs ps -f | cat
lsof -n -i4TCP:$PORT | grep LISTEN
```
- Check Memory Usage
```
cat /proc/meminfo
```
- Setup SSH Tunnel
```
ssh -N -L localhost:3306:DATABASE_MACHINE:3306 BRIDGE_MACHINE_USER@BRIDGE_MACHINE
```
- Setup strace for a Process
```
strace -f -o <output_file> <Process for which strace is required>
```
- See Top Network Users
```
iftop
```
- Check flavour of Linux
```
cat /proc/version
cat /etc/redhat-release
cat /etc/*-release
lsb_release -a
```
- Why does home directory does not have permissions on it ?
```
Check umask
umask 022 is usually the default.
```
- Why am I getting permission denied upon login or su - user ?
```
If "nofile" is set to "unlimited" in /etc/security/limits.conf (or in files in limits.d)
then the user cannot login.
```
- How to increase the ulimit on a shell while logging in?
```
su <user> --shell /bin/bash -c "ulimit -l unlimited"
```
- How to check route/hops between source and target machine ?
```
traceroute -e <host> <port>
traceroute -e -P TCP <host> <port>
```
- How to generate SSL Certificates
```
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /tmp/self-signed.key -out /tmp/self-signed.crt
```
- How to remove install rpm when directories are partially removed ?
```
yum clean all && rpm --rebuilddb
package-cleanup --problems
yum erase <package>*
yum --setopt=tsflags=noscripts remove <package>*
rpm -e --noscripts <package>*
```
- Flatten out contents of a file
```
awk 'NF {sub(/\r/, ""); printf "%s\\n",$0;}' <fileName>
```
- Monitor packets - HTTP request on a specific port
```tcpdump -A -s 0 'tcp port 8500 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'
```
- Monitor packets - LocalHost to Local Host
```tcpdump -A -s 0 'tcp port 8500 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)' -i lo
```
- How do I check my user ID ?
```
id
```
- What id systemctl does not work ?
```
export XDG_RUNTIME_DIR=/run/<user>/<user_id>
```
### MySQL
- Connect to MySQL as root
```
mysql --user=root --password <password>
```
- Create Admin user
```
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
```
- Grant all privileges
```
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost' WITH GRANT OPTION;
```
- Start MySQL
```
mysqld or start service MySQL55
```
- Shutdown MySQL
```
mysqladmin -u root shutdown
```

### Oracle
- Get Active Session for a user
```
SELECT s.sid, s.serial#, s.status, p.spid FROM v$session s, v$process p
WHERE s.username = '<Schema Name>'
  AND p.addr(+) = s.paddr;
        - select sid,serial#,username, server, osuser,STATUS from GV$SESSION
where username = '<Schema_User>'
```
- Kill Active session for a user
```
alter system kill session 'sid,serial#';
```
- See SQLs that were executed by a user.
```
select * from  sys.v_$sql s, sys.all_users u WHERE
s.parsing_user_id=u.user_id
and UPPER(u.username) not in ('SYS','SYSTEM')
and u.username = '<userName>'
```
- Enable/Disable all Constraints
```
BEGIN
  FOR c IN
  (SELECT c.owner, c.table_name, c.constraint_name
   FROM user_constraints c, user_tables t
   WHERE c.table_name = t.table_name
   AND c.status = 'ENABLED'
   ORDER BY c.constraint_type DESC)
  LOOP
    dbms_utility.exec_ddl_statement('alter table "' || c.owner || '"."' || c.table_name || '" enable constraint ' || c.constraint_name);
  END LOOP;
END;
```
- Get List of Constraints:
```
select decode(constraint_type,
     'C', 'Check',
     'O', 'R/O View',
     'P', 'Primary',
     'R', 'Foreign',
     'U', 'Unique',
     'V', 'Check view') type,
   constraint_name,
   status, TABLE_NAME, R_OWNER, R_CONSTRAINT_NAME FROM DBA_CONSTRAINTS
WHERE
constraint_name LIKE '%BAN3_BAN2%' and
OWNER = '<Owner_name'
```
- Unlock Account
```
ALTER USER scott ACCOUNT UNLOCK;
```

### Emacs
- Emacs (C - Ctrl, M-Alt)
    - Copy: M-w
    - Cut: C-w
    - Paste: C-y
    - Delete rest of the Line: C-k
    - Go to end of line: C-e
    - Undo Operation: C-x u
    - Insert a Line: C-o
    - Split Current Buffer: C-x 2
    - Switch to another window: C-x o
    - Create Directory:  M-x make-directory
    - List Buffers: C-x C-b
    - Select another buffer: C-x <Left>
    - Kill buffer: C-x k
    - Close current window: C-x 0
    - Select All Text in Buffer: C-x h
    - Set Mark for copy: C-space
    - Open a shell: M-x shell
    - Open a new shell: C-u M-x shell
    - Copy File as new File: C-x C-w
    - Delete Word: M-d
    - Create Directory: M-x make-directory RET <directory to create> RET
    - Go to end of file: M->
    - Go to beginning of file: M-<
    - Read Only mode: Enable/Disable with C-x C-q
    - Tabify/Untabify: C-x h M-x Tabify/Untabify
    - Show whitespace characters: M-x whitespace-mode
    - Replace String: M-x replace-string
    - Shell
        - Show Previous commands: C-Up
        - Search Commands: M-r
    - Dired commands
        - Start Dired mode: C-x d <directory>
        - Copy File/Directory: On the file, press Shift-C
        - List Directory: C-x C-d

### Linux
- Get Public IP address for local machine
```
curl -s checkip.dyndns.org | sed -e 's/.*Current IP Address: //' -e 's/<.*$//‘
```
- View free disk space in human readble format
```
df -h
```
- View disk space in all mounted systems
```
df -a
```
- View disk space for / filesystem
```
df -hT /
```
- Check where current directory is mounted (Use findmnt)
```
(until findmnt . ; do cd .. ; done)
findmnt
```
- Check disk usage in the current directory
```
du -hsx * | sort -rh | head -10
for i in G M K; do du -ah | grep [0-9]$i | sort -nr -k 1; done | head -n 11
```
- For total disk usage
```
du -ch /
```

### Vagrant
- Get Vagrant Status
```
vagrant status
```
- SSH to Vagrant Machine
```
vagrant ssh
```
- Reload Vagrant Configuration
```
vagrant reload
```
- Create Symbolic Link to shared Folder
```
sudo ln -fs /vagrant_data/ /var/www
```
- Initialise a Vm
```
vagrant init precise64 http://files.vagrantup.com/precise64.box
```
- Install AWS Plugin on Vagrant
```
vagrant plugin install vagrant-aws
```
- Copy War file into AWS
```
scp -i <pem_file> <war_file> <user_name>@<ec2_host>:<target_path>
```
- Sync local machine and remote machine
```
rsync -av --progress -e "ssh -i <pem_file>" <file> <user>@<host>:<target_path>
```
### MongoDB
- Importing Data in MongoDB
```
mongoimport —collection <collectionName> <fileName>
```

### Ansible Commands
- Ping Server
```
ansible <servername> -m ping
```
- Check uptime
```
ansible <servername> -a uptime
```
- Get Local Setup:
```
ansible all -i "localhost," -c local -m setup
```