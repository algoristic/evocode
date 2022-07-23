# 📈 _evocode_
Create _robocode_ robots, using [genetic programming](https://en.wikipedia.org/wiki/Genetic_programming).

## 💚 Getting Started
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
The configuration of the simulation happens inside a ´.properties´ file.

### Project properties
This settings control mostly the naming in your project. They will determine the naming structure of the folders in your project and the scheme for your robot-names as well.
```
evo.project.name=Name of your project
evo.project.robocodeLocation=path/to/robocode
evo.project.packageName=name.of.your.package
evo.project.robotClassName=Evocode_[individual]_[generation]
evo.project.generationDirectoryPrefix=g_
evo.project.individualDirectoryPrefix=tmp.i_
```

### Run configurations
```
evo.run.writeLog=true|false
evo.run.termination=iterations|timer|convergence
```
#### Timer options
Depending on the value of `evo.run.termination`:

Produce a specific amount of generations.
```
evo.run.iterations=4
```

Run a specific amount of time, defined over a `java.util.concurrent.TimeUnit`.
```
evo.run.timer.unit=hours
evo.run.timer.time=2
```

Run the simulation until any generation produces an individual that hits the denoted fitness.
```
evo.run.convergence=5000
```

### Genome generation and structure
// TODO

### Island setup
The total amount of individuals of a generation will be equally distributed over the defined number of islands. The islands stay isolated fro each other unless a migration happens. This leads to a partition of the search space and can produce a number of totally different apporaches to the solution.

There are two types of migration: _random_ and _ring_. The random migration let's individuals more or less uncrolled jump between islands (just as the name states). The ring migration leads to a more controlled behaviour where the migration individuals travel to the next adjascent island. Each migration is defined by an epoch (the mount of generations until the next generation happens) and a percentual migration chance for each individual of a generation. So a migration happens if `generation % epoch == 0`.
```
evo.strategy.islands.num=5
evo.gen.0.islands.migration=ring, random
evo.gen.0.islands.migration.ring.epoch=30
evo.gen.0.islands.migration.ring.chance=0.01
evo.gen.0.islands.migration.random.epoch=30
evo.gen.0.islands.migration.random.chance=0.01
```

### Evolution control
// TODO

## 🚀 Running the application
As of now there are two possibilities to run the app:

### Run the simulation
Run: `de.algoristic.evocode.EvocodeApplication` `absolute/path/to/your/config.properties` to run a project inside the directory where your configuration file resides.

### Run and display a specific individual
Run: `de.algoristic.evocode.EvocodeApplication` `absolute/path/to/your/config.properties` `<generation>` `<individual>` to re-run and display a already evaluated individual from a generation in that directory.

## 📝 Consideration
You may have wondered why there is no support for concurrency/multi-threading. Well, I have also tried around with this - in fact there is no way to run multiple instances of the robocode engine (API) in eclipse. You may instantiate multiple objects, but there will be always one underlying factory, performing all tasks, resulting in no improvement in performance...

### 📄 Sidenote (for myself)
Yes, I also considered running multiple copies of robocode (complete clones of `C:\robocode`) under `C:\robocode\<thread number>` to prevent the described problem. Sadly it is hardcoded in the robocode API to primarily use the instance that is part of the classpath of the caller.
