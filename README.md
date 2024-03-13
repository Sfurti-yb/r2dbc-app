# R2DBC APP WITH YUGABYTEDB

## To Run the app

### Prerequisites

1. Java Development Kit (JDK)
   1. Install JDK 8 or later. JDK installers for Linux and macOS can be downloaded from [Oracle](http://jdk.java.net/), [Adoptium (OpenJDK)](https://adoptium.net/), or [Azul Systems (OpenJDK)](https://www.azul.com/downloads/?package=jdk). Homebrew users on macOS can install using `brew install openjdk`.
2. Maven 
3. YugabyteDB
   1. To install YugabyteDB, follow the steps on [this page](https://docs.yugabyte.com/preview/quick-start/)

### Steps

1. Start a 3 node YugabyteDB cluster. To start a 3 node cluster, follow the steps on [this page](https://docs.yugabyte.com/preview/reference/configuration/yugabyted/#create-a-local-multi-node-cluster) 
2. Run the following command:
```shell
 mvn clean package exec:java -Dexec.mainClass="org.example.Main"
```