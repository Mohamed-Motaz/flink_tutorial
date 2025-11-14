# Flink Quickstart Job (flink_trial)

This repository contains a small Apache Flink example job (`zein.flink.trial.FirstExample`) used for local development and testing.

## Development (IDE / local run)

For convenience there is a non-default Maven profile `dev` that forks the JVM and passes the JVM flags required on Java 9+ so Flink's ClosureCleaner and Kryo can reflect into JDK internals.

Run locally with:

```bash
# Run using the dev profile which supplies the necessary --add-opens flags
mvn -Pdev -DskipTests exec:java
```

If you prefer not to use the profile, you can run with MAVEN_OPTS:

```bash
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED" \
  mvn -Dexec.mainClass=zein.flink.trial.FirstExample exec:java
```

Or run the shaded JAR (if you have built with non-provided scopes):

```bash
java --add-opens java.base/java.lang=ALL-UNNAMED -jar target/flink_trial-1.0.jar
```

## Production / Deployment

For deployment to a Flink cluster you should NOT package the Flink runtime inside your job JAR. Ensure the Flink dependencies are declared with `<scope>provided</scope>` in `pom.xml` (this project is configured that way by default). Then build and submit with a Flink distribution matching the Flink version in the POM:

```bash
# Package (scoped as provided so Flink runtime is not included)
mvn -DskipTests package

# Use a Flink distribution matching ${flink.version} (example: 1.14.4)
# from that Flink distribution directory:
./bin/flink run -c zein.flink.trial.FirstExample /path/to/target/flink_trial-1.0.jar
```

## Notes
- The `dev` profile is intended for local development convenience only. Keep the `provided` scopes for Flink when creating production artifacts.
- If you upgrade Java or Flink and encounter other module-access reflection errors, add the necessary `--add-opens` flags to the `dev` profile or your run command.

## Troubleshooting
- If Maven fails to download artifacts, try a forced update:

```bash
mvn -U -DskipTests package
```

- If you need me to add a `dev-dependencies` profile that switches Flink dependencies to compile scope for local testing, I can add that as an alternative to editing scopes directly.
