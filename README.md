0) To build the project
`mvn clean package`
This would produce the executable `jar` file in `./target/`. You should be
using the `jar` with `-jar-with-dependencies` in the name

1) To create the schema, use scripts in `./scripts/` directory

2) To start the program in files-mode (ie., data to be plotted is in file)
`java -jar ./target/*-jar-with-dependencies.jar`
(Or simply click on the `jar` file to open it)

3) To start the program in DB-mode (ie., data to be plotted should already in the DB)
(Not complete. Use at your own risk!)
`java -jar ./target/*-jar-with-dependencies.jar -db`
