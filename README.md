# Student Management System (Console + JDBC) - Because Who Doesn't Love Typing in a Terminal?

I’m just a python dev who speeds things up with c++. I don’t use windows, so I never tested this crap on windows — god knows if it works there. On ubuntu it runs.

This is a working java piece of shit. Make sure you use docker for safety, because java smells. I use docker to keep away any diseases from this language.. (I use chatgpt for modify this readme file! god forgive me!)

## Tech Stack (Or Lack Thereof)
- **Java 8+**: Because why use anything newer when you can cling to the past?
- **JDBC (MySQL Connector/J)**: The bridge between your code and database chaos.
- **MySQL**: The database that pretends to be reliable.
- **Console-based input/output**: Because GUIs are for quitters.
- **Docker (Recommended)**: Because setting up MySQL manually is a special kind of masochism.

## Setup Instructions (Recommended - Seriously, Don't Skip This)
Just run:
```
docker-compose up
```
And pray it works. If it doesn't, well, that's on you.

### Ensure Containers Are Running (Because They Might Not Be)
```
docker-compose ps
```

### Open Shell in App Container:
```
docker-compose exec app bash
```
Or, if you're feeling adventurous and know the container ID:
```
docker exec -it <container_id> bash
```

### Run the Thing (Finally!)
Inside the container:
```
java -cp "lib/*:src" StudentManager
```
Watch the magic happen... or not.

## Setup Instructions (Not Recommended - For the Brave or Foolish)
If you're dumb enough to do this manually (spoiler: you are), here's how:

1. Install MySQL and create a database called `student_db`. Good luck with that.
2. Run the schema definition from `schema.sql` in MySQL. Hope you don't mess up the syntax.
3. How to run:
   ```
   javac -cp "lib/*:." src/StudentManager.java
   java -cp "lib/*:src" StudentManager
   ```
   Compile and run like it's 1995.

## Workflow (My Painful Journey)
Here's how I suffered through this:

- Downloaded JDK from Oracle Downloads. Because open-source isn't cool enough.
- Installed MySQL Connector .deb: `mysql-connector-j_9.4.0-1ubuntu22.04_all.deb`. Fun times.
- Verified the files at `/usr/share/java/`:
  ```
  ls /usr/share/java/
  java-atk-wrapper.jar
  java_defaults.mk
  libintl-0.21.jar
  libintl.jar
  mysql-connector-j-9.4.0.jar
  mysql-connector-java-9.4.0.jar
  ```
  Ah, the sweet smell of JAR files.
- Copied into project structure: `cp /usr/share/java/mysql-connector-j-9.4.0.jar ~/SuckingJavaProjects/student-db/lib/`. Because manual labor is therapeutic.
- Grabbed dotenv dependencies because why not:
  ```
  cd lib
  wget https://repo1.maven.org/maven2/io/github/cdimascio/dotenv-java/3.0.0/dotenv-java-3.0.0.jar
  ```
  More JARs to clutter your lib folder.

## Final Words
Enjoy this little shitty Java thing. Or don't. Your call.
