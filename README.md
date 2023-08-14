# CF Spring boot

Java application implemented using Java/Spring Boot.

## Deploying it to CF

Run the following command to configure which Cloud Foundry environment you want to connect to in the terminal.

```bash
cf api <CF_API_ENDPOINT>

cf login
```

To deploy project into Cloud Foundry created a application manifest and enabled application for Cloud Foundry.

```bash
mvn clean install

cf push

cf apps
```

## XSUAA for Cloud Foundry

Updated application manifest.

```bash
---
applications:
  - name: java
    random-route: true
    path: ./target/java-0.0.1-SNAPSHOT.jar
    memory: 1024M
    buildpacks:
      - sap_java_buildpack
    env:
      TARGET_RUNTIME: tomcat
      JBP_CONFIG_COMPONENTS: "jres: ['com.sap.xs.java.buildpack.jdk.SAPMachineJDK']"
  - name: web
    random-route: true
    path: web
    memory: 1024M
    env:
      destinations: >
        [
          {
            "name":"java",
            "url":"https://java-grateful-toucan-ez.cfapps.us10-001.hana.ondemand.com/",
            "forwardAuthToken": true
          }
        ]
    services:
      - javauaa
```

Then, created instance of the Authorization and Trust Management Service

```bash
cf create-service xsuaa application javauaa -c xsuaa-security/xs-security.json
cf create-service saas-registry application saas-registry -c saas-registry/config.json

cf unbind-service java javauaa
cf unbind-service java saas-registry
cf unbind-service web javauaa

cf delete-service javauaa
cf delete-service saas-registry   

cf update-service javauaa -c xs-security.json
```

In order to get environment variables for OAuth 2.0, we shoould run following command:

```bash
cf env app_name_here
```
[XSUAA](https://help.sap.com/docs/btp/sap-business-technology-platform/set-up-your-application-for-multitenancy)

## Logging service

```bash
cf create-service application-logs lite my-logs

cf bind-service java my-logs
```
[Logging service](https://help.sap.com/docs/application-logging-service/sap-application-logging-service/sap-application-logging-service-for-cloud-foundry-environment)

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change, suppose new custom handlers or entities.

Please make sure to update tests as appropriate.

## Cloud Foundry

[Project link on CF](https://web-generous-crane-ut.cfapps.us10-001.hana.ondemand.com)
