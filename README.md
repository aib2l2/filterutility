# Filter Utility (Java)

This is a Java command-line application that reads one or more .txt files and filters each line by data type: integers, floating-point numbers, or text. Each type is saved into a separate file. The program can also display summary statistics in the console.

## How to Run

```bash
java -jar filter-utility-1.0-SNAPSHOT.jar -f -p sample- in1.txt in2.txt
```

Arguments:
- `-f` – full statistics
- `-p` – prefix for output files
- input files – one or more .txt files

## Output Files

The program may create:
- `sample-integers.txt`
- `sample-floats.txt`
- `sample-strings.txt`

Each file contains lines matching that data type.

## Command-Line Options

| Flag | Description |
|------|-------------|
| `-p` | Output filename prefix |
| `-o` | Output directory (optional) |
| `-a` | Append to files if they exist |
| `-s` | Short statistics |
| `-f` | Full statistics |

## Build

Use Maven to build:

```bash
mvn clean package
```

Output will be in `target/filter-utility-1.0-SNAPSHOT.jar`.

## Example Input

```
45
3.14
text
-0.001
1.5E-10
1234567890
```

## Technologies

- Java 17
- Maven
- No external libraries

## Author

Project by Aibol for educational purposes.
