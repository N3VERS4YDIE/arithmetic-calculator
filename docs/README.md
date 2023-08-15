# Arithmetic Calculator

This project provides an arithmetic calculator that allows you to perform fundamental mathematical operations quickly and conveniently, with the ability to evaluate multiple operations in a single expression.

![Showcase](showcase.gif)

## Features

- Handles decimal numbers and basic arithmetic operators (+, -, \*, /, %, ^).
- Provides a graphical user interface (GUI) for easy input and calculation.
- Displays the result of mathematical expressions in real time.

## Directory Structure

```bash
.
├── LICENSE
├── build.xml
├── docs
│   ├── README.md
│   └── showcase.gif
├── lib
│   ├── flatlaf-3.1.1.jar
│   ├── flatlaf-intellij-themes-3.1.1.jar
│   └── nblibraries.properties
├── nbproject
│   ├── ...
└── src
    ├── calculator
    │   ├── Calculator.java
    │   ├── CalculatorGUI.form
    │   └── CalculatorGUI.java
    └── main
        └── Main.java
```

> [!NOTE]
> The `nbproject` directory contains configuration files related to the NetBeans IDE (used only to design the Java Swing GUI using the NetBeans GUI builder). This may not be necessary if you use a different IDE or build the project with a different build process.

## Usage

### Running the JAR Release

1. Download the latest release of the calculator JAR file from the [Releases](https://github.com/N3VERS4YDIE/arithmetic-calculator/releases) section.
2. Open a terminal or command prompt.
3. Navigate to the directory where the downloaded JAR file is located.
4. Run the JAR file using the following command:

```bash
java -jar arithmetic-calculator.jar
```

### Running from Source

1. Clone the repository or download the source code.
2. Open the project in your preferred Java IDE.
3. Build the project using the provided build.xml file.
4. Run the Main class to launch the calculator application.

### Input

You can input mathematical expressions using both your keyboard and the buttons provided by the GUI.

### Keybindings

| Keyboard  | GUI | Action                                |
| --------- | --- | ------------------------------------- |
| Backspace | C   | Clear character at cursor position    |
| Esc       | CA  | Clear entire expression               |
| Enter     | =   | Replace entire expression with result |

## Contribution

Contributions to this project are welcome! If you find any issues or want to add new features, feel free to create pull requests or submit issues in the project repository.

## Credits

This project uses [FlatLaf](https://www.formdev.com/flatlaf/) library to give a nice ✨ look and feel to the Java Swing GUI.

## License

This project is licensed under the [MIT License](/LICENSE). Feel free to use and modify the code as needed.
