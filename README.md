# Bluebird project

A very simple CLI-based real-life task manager written in Java. Given below are instructions on how to use it.

If you're interested to find out what it does, check out the USERGuide[docs/README.md]!

## Running from Command Line (macOS)

Prerequisites: JDK 17 (it hasn't been tested on newer Java versions)

1. Navigate to the project root directory
   - the folder containing `/src`
1. Compile the Java files
   - `javac -d out -sourcepath src/main/java src/main/java/bluebird/*.java src/main/java/bluebird/**/*.java`
      - `-d out` tells Java to place compiled `.class` files inside the `out/` directory.
      - `-sourcepath` ensures that Java compiles all necessary dependencies.
      - The `*.java` and `**/*.java` ensure that all Java files in subdirectories are compiled.
1. Run the program
   - `java -cp out bluebird.Main`

## Messing with JAR file

1. Create a JAR file from the compiled Java files
   - `jar cfm myjar.jar manifest.txt -C out .`
      - Replace `myjar.jar` with any suitable name
      - `c` creates a new JAR file.
      - `f` specifies the output filename (`myjar.jar`).
      - `m` includes the manifest file.
      - `-C out .` means “change to the `out/` directory and add all compiled files.”
1. Run the program from the JAR
   - `java -jar myjar.jar`
      - Replace `myjar.jar` with whatever name was chosen in the previous step
