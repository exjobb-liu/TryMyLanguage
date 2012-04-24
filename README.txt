TryMyLanguage

TryMyLanguage (TML) is a web-based platform which is used to learn and explore a new programming language. TML is an install-free environment which allows you to compile and run code online.
Licensing: This software is released under GNU General Public License version 2

Requirements

TML is a Java web application which means that it can be run any Servlet container which implements Java Servlet specification.  Here we have the instruction for installation on tomcat version 7 on an Ubuntu Linux 11.10.

If you have already installed TML and want to update it with a new version, please make a backup of the  language configuration file before proceeding with upgrade process. The file is located in the root of the directory in which the application is deployed and it's called langparam.bin. In order to upgrade, just place the new war file in webapp folder of tomcat and remove the old directory and after restarting tomcat (sudo /etc/init.d/tomcat7 restart),  place langparam.bin in its original place. 

1. Install Tomcat 7 by issuing the following command

sudo apt-get install tomcat7 

2.copy tml.war into /var/lib/tomcat7/webapps

sudo cp tml.war /var/lib/tomcat7/webapps

3. Now you can access the application by the following URL

    http://localhost:8080/tml

4. If you are running the application for the first time, you'll be redirected to a page for configuration a programming language. There you can define different configuration parameters related to your programming language.
Note that for the compiler/interpreter to work you should have them either in the global path variable or set the full path when configuring the language. You can change global variables by editing the file /etc/environment.  
If you want to access the configuration page later again, replace the "ide" at the end of the URL that the browser is pointing to, to "configure".

5. After finishing the configuration, you'll be redirected the main page of the application. At the center of the page, you have a panel containing a text editor in which you can start programming in the language you have configured in the previous step. As you can see the code editor highlight the syntax of the program based on the keywords you have defined. After writing a program, you can compile/interpret/run it by clicking on the "Run" button which is located on top of the editor panel. You will get the output of the program in the bottom panel. You can also work on multiple files by clicking on the "New tab" button. 

6. Add custom documentation
    The left panel on the homepage, contains documentation material. The material is in the form of simple HTML. In order to provide your own content, you should put the HTML files in the ‘doc’ directory inside the directory in which TML is deployed. If you have followed the above instructions to install TML on Ubuntu, you can find the directory at the following path

/var/lib/tomcat7/webapps/tml/doc

This directory already contains a set of sample HTML files to demonstrate how you can use this feature of TML.

There are two requirements that should be followed. First there should be a file called index.html at the root of the doc directory. Second, all the links in HTML files should be defined in relative form. For example. http://somewhere.com/something/something.html is wrong. Instead you should define the link like: something/something.html
When writing documentation for a programming language, often times it contains code snippets of the language. To make it easier and more intuitive for the students to compile and run these code snippets, a global JavaScript functions is defined that you can you to copy the code to the editor panel. For more information and an example of its usage refer to the default documentation material i.e. HTML files in the doc directory.  
Note that an HTML frame is used to display documentation on the left panel and you should remember that HTML frames are cached in the browser. So every time you change something in the HTML files, the actual web page may or may not be updated (depending on the caching policy of the browser). There are two way to make this behavior predictable. You can either add these two tags to the head of all HTML files and there for disable caching:

<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="pragma" content="no-cache"/>

Or depending on the browser the you are using, you can refresh the content of the frame only and not the whole page. For example if you are using Firefox you can right-click on the documentation panel and from the "This Frame" menu you can choose "Reload Frame"
  

Sandboxing

One security requirement for software such as TML is to prevent attackers to run malicious code on the server. For this purpose, a shell script is provided to easily setup Sandboxing on Ubuntu Linux 11.10:

The shell script is located in the following path:

the_directory_where_you_deployed_TML/other/sandbox.sh

First you need to Install apparmor-utils package:
    In terminal type : sudo apt-get install apparmor-utils
after that you run the script provided that you are running it with root privileges;
The script takes 4 arguments. The first argument should be  0 or 1 indicating that you want to enable or disable Sandboxing. The second argument is the path to the program executable that you want to put in sandbox. It can be the compiler, the interpreter or sample executable program written in your desired language.
The third argument is the path to the directory that the program should have read/write access to.
There is a directory at the TML installation path called output (/var/lib/tomcat7/webapps/tml/output) that you usually want to have read/write access to, since it is the directory in which all the programs written by the users are stored and compiled. Note path should be defined without the trailing slash at the end.
The fourth argument again takes 0 or 1 and shows if you are running the shell script against a sample binary executable program as apposed to a compiler or an interpreter.


Plotting:
TML also support plotting of the program output. In order for it to work you should implement the PlotDataConvertor.java interface provided by the software. For further instruction refer to the comments which are included in the interface file. 
The file is located at the following path:

the_directory_where_you_deployed_TML/other/PlotDataConvertor.java


Troubleshooting

In order to investigate possible issues with system you can study log files of the web server. If you have followed the above instructions and deployed the application on tomcat 7 on Ubuntu 11.10, you can access the log files in the following path:
/var/log/tomcat7/
The main log file that might be interested in is either catalina.out for logs that are being created at the moment or catalina-somedate.out for the archive of the logs that have been created before.
 

