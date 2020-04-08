# Endrex
*"man, i wish i could find a better name"*

This is a Slimefun addon, that makes "The End" world less boring (I guess?). You can explore this world, find some
wild purple trees, or just came here with GEO Miner to gather resources.

## Builds
Right not there's no build available yet, but once it's done I'll release the binary at [this](https://github.com/nahkd123/Endrex/releases)
page, so you can download it.

## Build it yourself
Endrex uses Maven to build, so you'll need Maven.

### Build it using Eclipse IDE
Eclipse IDE now has m2e plugin installed by default, so to build Maven using Eclipse IDE, simply right click inside "Project Explorer" > 
"Import" > Search for "maven" > Browse for the Maven project > "Finish". Once you've done, right click the project name > Maven > Maven
Build > Set goal to "install" > Click "Run".

Once it's done, you can get the JAR file in ``/target/Endrex v1.0.0.jar``

### Build it using Maven command
```bash
cd Endrex
mvn install
```