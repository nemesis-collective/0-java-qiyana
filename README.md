# Questions about Maven

1. What is the minimum number of commands needed to clean, compile, test, deploy, and report on a project? (Note the lifecycle and dependencies).

   - 2 commands. Running the command `mvn deploy`, you can clean, compile, test and deploy the project. Then, `mvn site` to report the project.
   - Note: These two commands can be combined to a single command: `mvn deploy site`.

2. How can help be displayed for the Maven plugin: org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M1?

   - Running the command `mvn surefire:help`.

3. How can all of a project's plugins be displayed?

   - Running the command `mvn help:effective-pom`. You can run `mvn help:effective-pom > effective-pom.xml` to create a POM file with all the configurations of the project, including all plugins.

4. How can Maven dependencies be cleared from the local cache?

   - Running the command `mvn dependency:purge-local-repository`. After the cleaning, Maven will download the dependencies again.

5. How can Maven dependencies updates in the project be listed?

   -  Running the command `mvn versions:display-dependency-updates`. 

6. How can Maven produce a dependency graph?

   - Running the command `mvn dependency:tree`. 