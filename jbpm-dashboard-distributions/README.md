Distribution Builder
==========================

To generate all the distribution files go to the jbpm-dashboard root directory and type the following command:

    $ mvn clean install -Dfull -DskipTests

Currently, the following artifacts are generated:

* **jbpm-dashbuilder-jboss-as7.war:**  Product distribution the JBoss AS 7.x application server.

  Detailed installation instructions [here](https://github.com/droolsjbpm/jbpm-dashboard/blob/master/jbpm-dashboard-distributions/src/main/jbossas7/README.md).

* **jbpm-dashbuilder-wildfly-8.war:**  Product distribution for the Widlfly 8.x application server.

  Detailed installation instructions [here](https://github.com/droolsjbpm/jbpm-dashboard/blob/master/jbpm-dashboard-distributions/src/main/wildfly8/README.md).

* **jbpm-dashbuilder-eap-6_1.war:**  Product distribution for the RedHat EAP 6.1 application server.

  Detailed installation instructions [here](https://github.com/droolsjbpm/jbpm-dashboard/blob/master/jbpm-dashboard-distributions/src/main/eap6_1/README.md).

* **jbpm-dashbuilder-tomcat-7.war:**  Product distribution for Apache Tomcat 7 server.

  Detailed installation instructions [here](https://github.com/droolsjbpm/jbpm-dashboard/blob/master/jbpm-dashboard-distributions/src/main/tomcat7/README.md).

* **jbpm-dashbuilder-was-8.war:**  Product distribution for Websphere 8.5 server.

  It requires to set up a data source connection for any of the supported databases (at the time of this writing: DB2, Postgres, Mysql, H2, Oracle or SQLServer).

  Detailed installation instructions [here](https://github.com/droolsjbpm/jbpm-dashboard/blob/master/jbpm-dashboard-distributions/src/main/was8/README.md).