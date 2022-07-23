# 📈 _evocode_
Create _robocode_ robots, using [genetic programming](https://en.wikipedia.org/wiki/Genetic_programming).

## 🚀 Getting Started
### 🚧 Installation
1. Download _robocode_ from [sourceforge.net](https://sourceforge.net/projects/robocode/files/robocode/).
2. Install, using `java -jar robocode-installer.x.y.z.jar`.
  2.1. Install to `C:\robocode`.
4. In `C:\robocode\robocode.bat` adjust the lines depending on your Java version.
5. _Test run_ via `robocode.bat` or from start menu.

### ➕ Link to eclipse
1. Follow the guide [Create a project](https://www.robowiki.net/wiki/Robocode/Eclipse/Create_a_Project) on [robowiki](https://www.robowiki.net).
  1.1. Add `C:\robocode\libs\robocode.jar` according to guide.
  1.2. Link _javadoc_ according to guide.
2. Also add the [`robocode.api`](https://mvnrepository.com/artifact/net.sf.robocode/robocode.api) at correct version to `pom.xml` for your convenience.
3. In _robocode_ under `Options > Preferences > Development Options` add your Eclipse project and...
4. ...in Eclipse follow the guide [Running from Eclipse](https://robowiki.net/wiki/Robocode/Eclipse/Running_from_Eclipse) to make your robots runnable and debuggable from Eclipse.

### 🐛 Misc
- Check, if this problem occurs: https://stackoverflow.com/a/70600427.

## 🔧 Configuration

// TODO

## 📝 Consideration
You may have wondered why there is no support for concurrency/multi-threading. Well, I have also tried around with this - in fact there is no way to run multiple instances of the robocode engine (API) in eclipse. You may instantiate multiple objects, but there will be always one underlying factory, performing all tasks, resulting in no improvement in performance...

### 📄 Sidenote (for myself)
Yes, I also considered running multiple copies of robocode (complete clones of `C:\robocode`) under `C:\robocode\<thread number>` to prevent the described problem. Sadly it is hardcoded in the robocode API to primarily use the instance that is part of the classpath of the caller.
