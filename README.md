# direct-im-server
Direct Texting (the current project name) is an effort within DirectTrust to bring instance messaging technology to health care in much the same way the DirectProject brought SMTP (i.e. e-mail) based workflows to health care.  The concept utilizes many of the same philosophies of the DirectProject to ensure secure and authenticate flow of messages between organizations and entities within those organizations.

This prototype wraps a full blow [Openfire](https://www.igniterealtime.org/projects/openfire/) 4.2.0 server as a SpringBoot application and allows for configuration of trust stores and server to server TLS certificates (DirectTexting effectively uses mutual TLS for server to server communication).

**NOTE:** The current state is not configured for proper security out of the box.  This is being worked on so that an out of the box deployment is correctly configured to security server to server communication and to remove the default list of trust anchors.  Futher development will allow for the configuration of trust bundles (using the same DirectProject trust bundle distribution) as well as API to configure trust.

To build and run this project, clone this project and run the following commands
```
mvn clean package
cd target
java -jar direct-im-server-0.0.1-SNAPSHOT.jar
```

You can then configure the server by connecting to http://<server>:9090/.
