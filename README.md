# evocode
Create robocode robots, using genetic programming


## Prerequisites

### Installation
1. Download robocode from https://sourceforge.net/projects/robocode/files/robocode/
2. Install, using `java -jar robocode-installer.x.y.z.jar`
3. Install to `C:\robocode`
4. In `C:\robocode\robocode.bat` adjust the lines depending on Java version
5. _Test run_

### Link to eclipse
1. Follow the guide on https://www.robowiki.net/wiki/Robocode/Eclipse/Create_a_Project
1.1. Add `C:\robocode\libs\robocode.jar` according to guide
1.2. Link javadoc according to guide
2. Also add https://mvnrepository.com/artifact/net.sf.robocode/robocode.api at correct version to `pom.xml`

### Misc
- Prüfe, ob dieses Problem auftritt https://stackoverflow.com/a/70600427

## Consideration
You may have wondered why there is no support for concurrency/multi-threading. Well, I have also tried around with this - in fact there is no way to run multiple instances of the robocode engine (API) in eclipse. You may instantiate multiple objects, but there will be always one underlying factory, performing all tasks, resulting in no improvement in performance...

### Sidenote (for myself)
Yes, I also considered running multiple copies of robocode (complete clones of `C:\robocode`) under `C:\robocode\<thread number>` to prevent the described problem. Sadly it is hardcoded in the robocode API to primarily use the instance that is part of the classpath of the caller.