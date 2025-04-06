import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AtiLangFrontend {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AtiLangFrontend().createMainWindow());
    }

    private void createMainWindow() {
        JFrame frame = new JFrame("AtiLang - Compiler Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in fullscreen mode
        frame.setLayout(new CardLayout()); // Use CardLayout for switching between views

        // Set custom icon for the application
        ImageIcon icon = new ImageIcon("C:/Users/sriva/Downloads/doodle-math-objects-border-free-vector.jpg");frame.setIconImage(icon.getImage());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK); // Black background

        JLabel titleLabel = new JLabel("Welcome to AtiLang!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE); // White text

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(0, 1));
        textPanel.setPreferredSize(new Dimension(750, 250));
        textPanel.setBackground(Color.BLACK); // Black background

        JLabel introLabel = new JLabel("<html>" +
            "AtiLang is a powerful custom-built compiler designed to handle a diverse range of computational tasks efficiently.<br>" +
            "Unlike traditional compilers, AtiLang integrates multiple functionalities, making it a multi-utility programming tool that goes beyond basic arithmetic operations.<br><br>" +
            "<b>1) Arithmetic Operations:</b> Perform addition, subtraction, multiplication, division, and more.<br>" +
            "<b>2) String Manipulation:</b-> Reverse strings, check for palindromes, count vowels, and format text.<br>" +
            "<b>3) Array Processing:</b> Find the largest/smallest number, sort arrays, and calculate sums or averages.<br>" +
            "<b>4) Mathematical Functions:</b> Compute factorials, Fibonacci numbers, prime numbers, GCD, and LCM.<br>" +
            "<b>5) Geometric Calculations:</b> Find areas and perimeters of various shapes.<br>" +
            "<b>6) Temperature Conversions:</b> Convert between Celsius and Fahrenheit.<br>" +
            "<b>7) Randomization & Number Theory:</b> Generate random numbers, check odd/even, and more.<br>" +
            "</html>");
        introLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        introLabel.setForeground(Color.WHITE); // White text

        textPanel.add(introLabel);

        JButton progButton = new JButton("Open Programming Interface");
        progButton.setFont(new Font("Arial", Font.BOLD, 16));
        progButton.setBackground(new Color(0, 153, 76)); // Green color
        progButton.setForeground(Color.WHITE); // White text
        progButton.setToolTipText("Click to open the programming interface");
        progButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        progButton.addActionListener(e -> showProgrammingInterface(frame));

        JButton manualButton = new JButton("Open Syntax Manual");
        manualButton.setFont(new Font("Arial", Font.BOLD, 16));
        manualButton.setBackground(new Color(0, 153, 76)); // Green color
        manualButton.setForeground(Color.WHITE); // White text
        manualButton.setToolTipText("Click to view the syntax manual");
        manualButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        manualButton.addActionListener(e -> showSyntaxManual(frame));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        mainPanel.add(textPanel, gbc);

        gbc.gridy = 2;
        mainPanel.add(progButton, gbc);

        gbc.gridy = 3;
        mainPanel.add(manualButton, gbc);

        frame.add(mainPanel, "MainMenu"); // Add main menu to CardLayout
        frame.setVisible(true);
    }

    private void showProgrammingInterface(JFrame frame) {
        JPanel progPanel = new JPanel(new BorderLayout());

        // Code editor area
        JTextArea codeEditor = new JTextArea();
        codeEditor.setFont(new Font("Monospaced", Font.PLAIN, 16));
        codeEditor.setBackground(Color.BLACK); // BLACK background for the editor
        codeEditor.setForeground(Color.WHITE); // WHITE text
        codeEditor.setCaret(new DefaultCaret() {
            {
                setBlinkRate(500); // Make caret blink every 500ms
            }
        
            @Override
            protected synchronized void damage(Rectangle r) {
                if (r == null) return;
                x = r.x;
                y = r.y;
                width = getComponent().getFontMetrics(getComponent().getFont()).charWidth('w');
                height = r.height;
                repaint();
            }
        
            @Override
            public void paint(Graphics g) {
                JTextComponent comp = getComponent();
                if (comp == null || !isVisible()) return;
                try {
                    Rectangle r = comp.modelToView(getDot());
                    if (r == null) return;
                    g.setColor(comp.getCaretColor());
                    g.fillRect(r.x, r.y, width, r.height); // block caret
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        codeEditor.setCaretColor(Color.WHITE); // make sure it's visible on black
        
        JScrollPane editorScroll = new JScrollPane(codeEditor);

        // Output display area
        JTextArea outputDisplay = new JTextArea();
        outputDisplay.setFont(new Font("Monospaced", Font.PLAIN, 16));
        outputDisplay.setEditable(false);
        outputDisplay.setBackground(Color.BLACK); // BLACK background for the output
        outputDisplay.setForeground(Color.WHITE);
        JScrollPane outputScroll = new JScrollPane(outputDisplay);

        // Split pane to make editor and output resizable
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScroll, outputScroll);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.7);

        // Run and Save buttons
        JButton runButton = new JButton("Run");
        runButton.setFont(new Font("Arial", Font.BOLD, 16));
        runButton.setBackground(new Color(0, 153, 76));
        runButton.setForeground(Color.WHITE);
        runButton.setToolTipText("Click to execute the code");
        runButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        runButton.addActionListener(e -> runCode(codeEditor, outputDisplay));

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setBackground(new Color(0, 153, 76));
        saveButton.setForeground(Color.WHITE);
        saveButton.setToolTipText("Click to save the code to a file");
        saveButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        saveButton.addActionListener(e -> saveCode(codeEditor));

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 153, 76)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setToolTipText("Go back to the main menu");
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        backButton.addActionListener(e -> ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "MainMenu"));

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(runButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(backButton);

        progPanel.add(splitPane, BorderLayout.CENTER);
        progPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(progPanel, "ProgrammingInterface"); // Add programming interface to CardLayout
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "ProgrammingInterface");
    }

    private void showSyntaxManual(JFrame frame) {
        JPanel manualPanel = new JPanel(new BorderLayout());
        manualPanel.setBackground(Color.BLACK);

        // Title for the syntax manual
        JLabel titleLabel = new JLabel("AtiLang Syntax Manual");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 153, 76)); // Green color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the title

        JTextArea manualText = new JTextArea();
        manualText.setFont(new Font("Monospaced", Font.PLAIN, 14));
        manualText.setEditable(false);
        manualText.setBackground(Color.BLACK);
        manualText.setForeground(Color.WHITE);
        manualText.setCaretColor(Color.WHITE);
        manualText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding inside the text area
        manualText.setLineWrap(true);
        manualText.setWrapStyleWord(true);
        manualText.setText("AtiLang Syntax Manual\n" +
            "=========================================================================================================================================================================\n" +
            "1. Arithmetic Operations:\n" +
            "   i) atiAdd(a, b) -> Adds two numbers\n\tSyntax: atiAdd(5,3);\n" +
            "\tExplanation: Returns the sum of a and b.\n\n" +
            "   ii) atiSub(a, b) -> Subtracts second number from first\n\tSyntax: atiSub(5,3);\n" +
            "\tExplanation: Returns the difference of a and b.\n\n" +
            "   iii) atiMul(a, b) -> Multiplies two numbers\n\tSyntax: atiMul(5,3);\n" +
            "\tExplanation: Returns the product of a and b.\n\n" +
            "   iv) atiDiv(a, b) -> Divides first number by second\n\tSyntax: atiDiv(5,3);\n" +
            "\tExplanation: Returns the quotient of a divided by b.\n\n" +
            "   v) atiRemainder(a, b) -> Computes remainder\n\tSyntax: atiRemainder(5,3);\n" +
            "\tExplanation: Returns the remainder when a is divided by b.\n\n" +
            "   vi) atiFactorial(n) -> Computes factorial\n\tSyntax: atiFactorial(5);\n" +
            "\tExplanation: Returns the factorial of n.\n\n" +
            "   vii) atiFibo(n) -> Computes Fibonacci number\n\tSyntax: atiFibo(7);\n" +
            "\tExplanation: Returns the n-th Fibonacci number.\n\n" +
            "   viii) atiOneDigitNumber(n) -> Checks if n is a single-digit number\n\tSyntax: atiOneDigitNumber(5);\n" +
            "\tExplanation: Returns true if n is a single-digit number.\n\n" +
            "2. Number Theory & Math Functions:\n" +
            "   i) atiEvenOrOdd(n) -> Determines if a number is even or odd\n\tSyntax: atiEvenOrOdd(4);\n" +
            "\tExplanation: Returns \"Even\" or \"Odd\".\n\n" +
            "   ii) atiMax(a, b) -> Finds the maximum of two numbers\n\tSyntax: atiMax(5,3);\n" +
            "\tExplanation: Returns the maximum of a and b.\n\n" +
            "   iii) atiMin(a, b) -> Finds the minimum of two numbers\n\tSyntax: atiMin(5,3);\n" +
            "\tExplanation: Returns the minimum of a and b.\n\n" +
            "   iv) atiExp(base, exponent) -> Computes power\n\tSyntax: atiExp(2,3);\n" +
            "\tExplanation: Returns base^exponent.\n\n" +
            "   v) atiRoot(n, degree=2) -> Computes root\n\tSyntax: atiRoot(25,2);\n" +
            "\tExplanation: Returns the degree root of n.\n\n" +
            "   vi) atiGCD(a, b) -> Computes greatest common divisor\n\tSyntax: atiGCD(24,36);\n" +
            "\tExplanation: Returns the greatest common divisor of a and b.\n\n" +
            "   vii) atiLCM(a, b) -> Computes least common multiple\n\tSyntax: atiLCM(12,18);\n" +
            "\tExplanation: Returns the least common multiple of a and b.\n\n" +
            "   viii) atiPrimeNumber(n) -> Checks if a number is prime\n\tSyntax: atiPrimeNumber(17);\n" +
            "\tExplanation: Returns true if n is prime.\n\n" +
            "   ix) atiPrimeInRange(start, end) -> Finds primes in range\n\tSyntax: atiPrimeInRange(10,50);\n" +
            "\tExplanation: Returns prime numbers in the range.\n\n" +
            "3. String Operations:\n" +
            "   i) atiVowels(s) -> Counts vowels in string\n\tSyntax: atiVowels(\"hello\");\n" +
            "\tExplanation: Returns the count of vowels in s.\n\n" +
            "   ii) atiRevString(s) -> Reverses a string\n\tSyntax: atiRevString(\"hello\");\n" +
            "\tExplanation: Reverses s.\n\n" +
            "   iii) atiStringPalindrome(s) -> Checks if a string is a palindrome\n\tSyntax: atiStringPalindrome(\"madam\");\n" +
            "\tExplanation: Returns true if s is a palindrome.\n\n" +
            "   iv) atiStringToUppercase(s) -> Converts string to uppercase\n\tSyntax: atiStringToUppercase(\"hello\");\n" +
            "\tExplanation: Converts all letters in s to uppercase.\n\n" +
            "   v) atiStringToLowercase(s) -> Converts string to lowercase\n\tSyntax: atiStringToLowercase(\"HELLO\");\n" +
            "\tExplanation: Converts all letters in s to lowercase.\n\n" +
            "   vi) atiFirstLetterCapital(s) -> Capitalizes the first letter of each word\n\tSyntax: atiFirstLetterCapital(\"hello world\");\n" +
            "\tExplanation: Capitalizes the first letter of each word in s.\n\n" +
            "   vii) atiReplaceSpaceWithUnderscore(s) -> Replaces spaces with underscores\n\tSyntax: atiReplaceSpaceWithUnderscore(\"hello world\");\n" +
            "\tExplanation: Replaces spaces with underscores in s.\n\n" +
            "   viii) atiCountNumberOfWords(s) -> Counts words in string\n\tSyntax: atiCountNumberOfWords(\"hello world\");\n" +
            "\tExplanation: Returns the number of words in s.\n\n" +
            "   ix) atiLengthOfString(s) -> Returns the length of a string\n\tSyntax: atiLengthOfString(\"hello\");\n" +
            "\tExplanation: Returns the length of s.\n\n" +
            "   x) ati2StringsSame(s1, s2) -> Checks if two strings are the same\n\tSyntax: ati2StringsSame(\"hello\", \"hello\");\n" +
            "\tExplanation: Returns true if s1 and s2 are the same.\n\n" +
            "   xi) ati2StringsSameIgnoreCase(s1, s2) -> Checks equality ignoring case\n\tSyntax: ati2StringsSameIgnoreCase(\"Hello\", \"hello\");\n" +
            "\tExplanation: Returns true if s1 and s2 are the same, ignoring case.\n\n" +
            "   xii) atiCompareStringsLexicographically(s1, s2) -> Compares two strings lexicographically\n\tSyntax: atiCompareStringsLexicographically(\"apple\", \"banana\");\n" +
            "\tExplanation: Compares s1 and s2 lexicographically.\n\n" +
            "   xiii) atiStringContainsAnotherString(s1, s2) -> Checks if one string contains another\n\tSyntax: atiStringContainsAnotherString(\"hello world\", \"world\");\n" +
            "\tExplanation: Returns true if s1 contains s2.\n\n" +
            "4. Array Operations:\n" +
            "   i) atiLargestArrayNumber(arr) -> Finds the largest number in array\n\tSyntax: atiLargestArrayNumber([1,2,3]);\n" +
            "\tExplanation: Returns the largest number in arr.\n\n" +
            "   ii) atiSmallestArrayNumber(arr) -> Finds the smallest number in array\n\tSyntax: atiSmallestArrayNumber([1,2,3]);\n" +
            "\tExplanation: Returns the smallest number in arr.\n\n" +
            "   iii) atiArraySum(arr) -> Computes sum of elements\n\tSyntax: atiArraySum([1,2,3]);\n" +
            "\tExplanation: Returns the sum of all elements in arr.\n\n" +
            "   iv) atiArrayMul(arr) -> Multiplies all elements in array\n\tSyntax: atiArrayMul([1,2,3]);\n" +
            "\tExplanation: Returns the product of all elements in arr.\n\n" +
            "   v) atiAverageOfArrayElements(arr) -> Computes average of array elements\n\tSyntax: atiAverageOfArrayElements([1,2,3]);\n" +
            "\tExplanation: Returns the average of elements in arr.\n\n" +
            "   vi) atiDivideAllArrayElementsByANumber(arr, n) -> Divides all elements by a number\n\tSyntax: atiDivideAllArrayElementsByANumber([10,20], 2);\n" +
            "\tExplanation: Divides each element in arr by n.\n\n" +
            "   vii) atiSearchOfNumberInArray(arr, x) -> Searches for a number in array\n\tSyntax: atiSearchOfNumberInArray([1,2,3], 2);\n" +
            "\tExplanation: Returns true if x is found in arr.\n\n" +
            "   viii) atiArrayCheckSortAscending(arr) -> Checks if array is sorted in ascending order\n\tSyntax: atiArrayCheckSortAscending([1,2,3]);\n" +
            "\tExplanation: Returns true if arr is sorted in ascending order.\n\n" +
            "   ix) atiArrayCheckSortDescending(arr) -> Checks if array is sorted in descending order\n\tSyntax: atiArrayCheckSortDescending([3,2,1]);\n" +
            "\tExplanation: Returns true if arr is sorted in descending order.\n\n" +
            "   x) atiPrintArrayReverse(arr) -> Prints array in reverse order\n\tSyntax: atiPrintArrayReverse([1,2,3]);\n" +
            "\tExplanation: Prints the elements of arr in reverse order.\n\n" +
            "5. Geometry & Conversions:\n" +
            "   i) atiCelsiusToFarhenheit(c) -> Converts Celsius to Fahrenheit\n\tSyntax: atiCelsiusToFarhenheit(25);\n" +
            "\tExplanation: Converts Celsius to Fahrenheit using the formula F = (9/5 * C) + 32.\n\n" +
            "   ii) atiFarhenheitToCelsius(f) -> Converts Fahrenheit to Celsius\n\tSyntax: atiFarhenheitToCelsius(77);\n" +
            "\tExplanation: Converts Fahrenheit to Celsius using the formula C = 5/9 * (F - 32).\n\n" +
            "   iii) atiAreaOfCircle(r) -> Computes area of a circle\n\tSyntax: atiAreaOfCircle(5);\n" +
            "\tExplanation: Returns the area of a circle with radius r using the formula A = πr^2.\n\n" +
            "   iv) atiPerimeterOfCircle(r) -> Computes perimeter of a circle\n\tSyntax: atiPerimeterOfCircle(5);\n" +
            "\tExplanation: Returns the circumference of a circle with radius r using the formula P = 2πr.\n\n" +
            "   v) atiAreaOfSquare(side) -> Computes area of a square\n\tSyntax: atiAreaOfSquare(4);\n" +
            "\tExplanation: Returns the area of a square with side length side using the formula A = side^2.\n\n" +
            "   vi) atiPerimeterOfSquare(side) -> Computes perimeter of a square\n\tSyntax: atiPerimeterOfSquare(4);\n" +
            "\tExplanation: Returns the perimeter of a square using the formula P = 4 * side.\n\n" +
            "   vii) atiAreaOfRectangle(l, w) -> Computes area of a rectangle\n\tSyntax: atiAreaOfRectangle(5,3);\n" +
            "\tExplanation: Returns the area of a rectangle with length l and width w using the formula A = l * w.\n\n" +
            "   viii) atiPerimeterOfRectangle(l, w) -> Computes perimeter of a rectangle\n\tSyntax: atiPerimeterOfRectangle(5,3);\n" +
            "\tExplanation: Returns the perimeter of a rectangle using the formula P = 2 * (l + w).\n\n" +
            "   ix) atiAreaOfTriangle(b, h) -> Computes area of a triangle\n\tSyntax: atiAreaOfTriangle(6,4);\n" +
            "\tExplanation: Returns the area of a triangle with base b and height h using the formula A = 1/2 * b * h.\n\n" +
            "   x) atiVolumeOfCube(side) -> Computes volume of a cube\n\tSyntax: atiVolumeOfCube(3);\n" +
            "\tExplanation: Returns the volume of a cube with side length side using the formula V = side^3.\n\n" +
            "=========================================================================================================================================================================\n");
        manualText.setCaretPosition(0); // Scroll to the top
        manualText.setLineWrap(true); // Enable line wrapping


        JScrollPane scrollPane = new JScrollPane(manualText);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        scrollPane.setBackground(Color.BLACK);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.BLACK);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchLabel.setForeground(Color.WHITE);

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(0, 153, 76)); // Green color
        searchButton.setForeground(Color.WHITE);
        searchButton.setToolTipText("Search for specific syntax");
        searchButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            String content = manualText.getText();
            if (!searchText.isEmpty()) {
                int index = content.toLowerCase().indexOf(searchText.toLowerCase());
                if (index != -1) {
                    manualText.setCaretPosition(index); // Scroll to the match
                    manualText.select(index, index + searchText.length()); // Highlight the match
                    manualText.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(frame, "No matches found for: " + searchText, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 153, 76)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setToolTipText("Go back to the main menu");
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        backButton.addActionListener(e -> ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "MainMenu"));

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(backButton);

        manualPanel.add(titleLabel, BorderLayout.NORTH); // Add title at the top
        manualPanel.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE); // Add search panel below the title
        manualPanel.add(scrollPane, BorderLayout.CENTER); // Add scrollable text area
        manualPanel.add(bottomPanel, BorderLayout.SOUTH); // Add back button at the bottom

        frame.add(manualPanel, "SyntaxManual"); // Add syntax manual to CardLayout
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "SyntaxManual");
    }

    private void runCode(JTextArea editor, JTextArea outputDisplay) {
        String code = editor.getText().trim();
        StringBuilder output = new StringBuilder();
        output.append("Output:\n");
        String[] statements = code.split(";"); // Split code by ';'
        int g=0;
        for (String statement : statements) {
            
            if(g==1){
                break;
            }
            statement = statement.trim();
            if (statement.isEmpty()) continue;
    
            try {
                if (statement.startsWith("atiAdd(") && statement.endsWith(")")) {
                    String args = statement.substring(7, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int a = Integer.parseInt(parts[0].trim());
                        int b = Integer.parseInt(parts[1].trim());
                        output.append("").append(a + b).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiAdd(a, b).\n");
                    }
                } else if (statement.startsWith("atiSub(") && statement.endsWith(")")) {
                    String args = statement.substring(7, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int a = Integer.parseInt(parts[0].trim());
                        int b = Integer.parseInt(parts[1].trim());
                        output.append(a - b).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiSub(a, b).\n");
                    }
                } else if (statement.startsWith("atiMul(") && statement.endsWith(")")) {
                    String args = statement.substring(7, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int a = Integer.parseInt(parts[0].trim());
                        int b = Integer.parseInt(parts[1].trim());
                        output.append(a * b).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiMul(a, b).\n");
                    }
                } else if (statement.startsWith("atiDiv(") && statement.endsWith(")")) {
                    String args = statement.substring(7, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int a = Integer.parseInt(parts[0].trim());
                        int b = Integer.parseInt(parts[1].trim());
                        if (b != 0) {
                            output.append(a / b).append("\n");
                        } else {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Division by zero is not allowed.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiDiv(a, b).\n");
                    }
                } else if (statement.startsWith("atiGCD(") && statement.endsWith(")")) {
                    String args = statement.substring(7, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int a = Integer.parseInt(parts[0].trim());
                        int b = Integer.parseInt(parts[1].trim());
                        output.append(gcd(a, b)).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiGCD(a, b).\n");
                    }
                } 
                else if (statement.startsWith("atiLCM(") && statement.endsWith(")")) {
                    String args = statement.substring(7, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int a = Integer.parseInt(parts[0].trim());
                        int b = Integer.parseInt(parts[1].trim());
                        output.append(lcm(a, b)).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiLCM(a, b).\n");
                    }
                } else if (statement.startsWith("atiPrimeNumber(") && statement.endsWith(")")) {
                    String args = statement.substring(15, statement.length() - 1).trim();
                    int n = Integer.parseInt(args);
                    output.append(isPrime(n) ? "true" : "false").append("\n");
                } else if (statement.startsWith("atiPrimeInRange(") && statement.endsWith(")")) {
                    String args = statement.substring(16, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int start = Integer.parseInt(parts[0].trim());
                        int end = Integer.parseInt(parts[1].trim());
                        output.append(primesInRange(start, end)).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiPrimeInRange(start, end).\n");
                    }
                }
                else if(statement.startsWith("atiOneDigitNumber(") && statement.endsWith(")")){
                    String args=statement.substring(18,statement.length()-1);
                    int n=Integer.parseInt(args.trim());
                    if(n>=0 && n<=9){
                        output.append("true\n");
                    }else{
                        g=1;
                        output.setLength(0);
                        output.append("false\n");
                    }
                } 
                else if (statement.startsWith("atiRemainder(") && statement.endsWith(")")) {
                    String args = statement.substring(13, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int a = Integer.parseInt(parts[0].trim());
                        int b = Integer.parseInt(parts[1].trim());
                        if (b != 0) {
                            output.append("").append(a % b).append("\n");
                        } else {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Division by zero is not allowed.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiRemainder(a, b).\n");
                    }
                }
                else if (statement.startsWith("atiFactorial(") && statement.endsWith(")")) {
                    String args = statement.substring(13, statement.length() - 1).trim();
                    int n = Integer.parseInt(args);
                    if (n < 0) {
                        g=1;
                        output.setLength(0);

                        output.append("Error: Factorial of a negative number is undefined.\n");
                    } else {
                        output.append("").append(factorial(n)).append("\n");
                    }
                }
                else if (statement.startsWith("atiFibo(") && statement.endsWith(")")) {
                    String args = statement.substring(8, statement.length() - 1).trim();
                    int n = Integer.parseInt(args);
                    if (n < 0) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Fibonacci sequence index cannot be negative.\n");
                    } else {
                        output.append("").append(fibonacci(n)).append("\n");
                    }
                }
                else if (statement.startsWith("atiEvenOrOdd(") && statement.endsWith(")")) {
                    String args = statement.substring(13, statement.length() - 1).trim();
                    int n = Integer.parseInt(args);
                    output.append("").append(n % 2 == 0 ? "Even" : "Odd").append("\n");
                }
                else if (statement.startsWith("atiMax(") && statement.endsWith(")")) {
                    String args = statement.substring(7, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int a = Integer.parseInt(parts[0].trim());
                        int b = Integer.parseInt(parts[1].trim());
                        output.append("").append(Math.max(a, b)).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiMax(a, b).\n");
                    }
                } else if (statement.startsWith("atiMin(") && statement.endsWith(")")) {
                    String args = statement.substring(7, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int a = Integer.parseInt(parts[0].trim());
                        int b = Integer.parseInt(parts[1].trim());
                        output.append("").append(Math.min(a, b)).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiMin(a, b).\n");
                    }
                }
                else if (statement.startsWith("atiExp(") && statement.endsWith(")")) {
                    String args = statement.substring(7, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        int base = Integer.parseInt(parts[0].trim());
                        int exponent = Integer.parseInt(parts[1].trim());
                        output.append("").append((int) Math.pow(base, exponent)).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiExp(base, exponent).\n");
                    }
                }
                else if (statement.startsWith("atiRoot(") && statement.endsWith(")")) {
                    String args = statement.substring(8, statement.length() - 1);
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        double n = Double.parseDouble(parts[0].trim());
                        double degree = Double.parseDouble(parts[1].trim());
                        if (degree != 0) {
                            output.append("").append(Math.pow(n, 1.0 / degree)).append("\n");
                        } else {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Root degree cannot be zero.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid number of arguments for atiRoot(n, degree).\n");
                    }
                }
                else if (statement.startsWith("atiVowels(") && statement.endsWith(")")) {
                    String args = statement.substring(10, statement.length() - 1).trim();
                    if ((args.startsWith("\"") && args.endsWith("\"")) || (args.startsWith("'") && args.endsWith("'"))) {
                        String str = args.substring(1, args.length() - 1); // remove quotes
                        int count = 0;
                        for (char c : str.toLowerCase().toCharArray()) {
                            if ("aeiou".indexOf(c) != -1) {
                                count++;
                            }
                        }
                        output.append("Number of vowels = ").append(count).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for ativowels(string). String must be enclosed in double or single quotes.\n");
                    }
                } 
                else if (statement.startsWith("atiRevString(") && statement.endsWith(")")) {
                    String args = statement.substring(13, statement.length() - 1).trim();
                    if ((args.startsWith("\"") && args.endsWith("\"")) || (args.startsWith("'") && args.endsWith("'"))) {
                        String str = args.substring(1, args.length() - 1); // remove surrounding quotes
                        String reversed = new StringBuilder(str).reverse().toString();
                        output.append("").append(reversed).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiRevString(string). String must be enclosed in double or single quotes.\n");
                    }
                }
                else if (statement.startsWith("atiStringPalindrome(") && statement.endsWith(")")) {
                    String args = statement.substring(20, statement.length() - 1).trim();
                    if ((args.startsWith("\"") && args.endsWith("\"")) || (args.startsWith("'") && args.endsWith("'"))) {
                        String str = args.substring(1, args.length() - 1); // remove surrounding quotes
                        String reversed = new StringBuilder(str).reverse().toString();
                        output.append("").append(str.equals(reversed) ? "true" : "false").append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiStringPalindrome(string). String must be enclosed in double or single quotes.\n");
                    }
                }
                else if (statement.startsWith("atiStringToUppercase(") && statement.endsWith(")")) {
                    String args = statement.substring(21, statement.length() - 1).trim();
                    if ((args.startsWith("\"") && args.endsWith("\"")) || (args.startsWith("'") && args.endsWith("'"))) {
                        String str = args.substring(1, args.length() - 1); // remove surrounding quotes
                        output.append("").append(str.toUpperCase()).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiStringToUppercase(string). String must be enclosed in double or single quotes.\n");
                    }
                }
                else if (statement.startsWith("atiStringToLowercase(") && statement.endsWith(")")) {
                    String args = statement.substring(21, statement.length() - 1).trim();
                    if ((args.startsWith("\"") && args.endsWith("\"")) || (args.startsWith("'") && args.endsWith("'"))) {
                        String str = args.substring(1, args.length() - 1); // remove surrounding quotes
                        output.append("").append(str.toLowerCase()).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiStringToLowercase(string). String must be enclosed in double or single quotes.\n");
                    }
                }
                else if (statement.startsWith("atiFirstLetterCapital(") && statement.endsWith(")")) {
                    String args = statement.substring(22, statement.length() - 1).trim();
                    if ((args.startsWith("\"") && args.endsWith("\"")) || (args.startsWith("'") && args.endsWith("'"))) {
                        String str = args.substring(1, args.length() - 1); // remove surrounding quotes
                        String[] words = str.split("\\s+");
                        StringBuilder result = new StringBuilder();
                        for (String word : words) {
                            if (!word.isEmpty()) {
                                result.append(Character.toUpperCase(word.charAt(0)));
                                if (word.length() > 1) {
                                    result.append(word.substring(1).toLowerCase());
                                }
                                result.append(" ");
                            }
                        }
                        output.append("").append(result.toString().trim()).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiFirstLetterCapital(string). String must be enclosed in double or single quotes.\n");
                    }
                }
                else if (statement.startsWith("atiReplaceSpaceWithUnderscore(") && statement.endsWith(")")) {
                    String args = statement.substring(30, statement.length() - 1).trim();
                    if ((args.startsWith("\"") && args.endsWith("\"")) || (args.startsWith("'") && args.endsWith("'"))) {
                        String str = args.substring(1, args.length() - 1); // Remove surrounding quotes
                        String result = str.replace(" ", "_");
                        output.append("").append(result).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiReplaceSpaceWithUnderscore(string). String must be enclosed in double or single quotes.\n");
                    }
                }
                else if (statement.startsWith("atiCountNumberOfWords(") && statement.endsWith(")")) {
                    String args = statement.substring(22, statement.length() - 1).trim();
                    if ((args.startsWith("\"") && args.endsWith("\"")) || (args.startsWith("'") && args.endsWith("'"))) {
                        String str = args.substring(1, args.length() - 1); // Remove quotes
                        // Use regex to split on whitespace and ignore empty strings
                        String[] words = str.trim().split("\\s+");
                        int count = (str.trim().isEmpty()) ? 0 : words.length;
                        output.append("Number of words: ").append(count).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiCountNumberOfWords(string). String must be enclosed in double or single quotes.\n");
                    }
                }
                else if (statement.startsWith("atiLengthOfString(") && statement.endsWith(")")) {
                    String args = statement.substring(18, statement.length() - 1).trim();
                    if ((args.startsWith("\"") && args.endsWith("\"")) || (args.startsWith("'") && args.endsWith("'"))) {
                        String str = args.substring(1, args.length() - 1); // Remove quotes
                        output.append("Length of string: ").append(str.length()).append("\n");
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiLengthOfString(string). String must be enclosed in double or single quotes.\n");
                    }
                }
                else if (statement.startsWith("ati2StringsSame(") && statement.endsWith(")")) {
                    String args = statement.substring(16, statement.length() - 1).trim();
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        String str1 = parts[0].trim();
                        String str2 = parts[1].trim();
                
                        if (((str1.startsWith("\"") && str1.endsWith("\"")) || (str1.startsWith("'") && str1.endsWith("'"))) &&
                            ((str2.startsWith("\"") && str2.endsWith("\"")) || (str2.startsWith("'") && str2.endsWith("'")))) {
                
                            str1 = str1.substring(1, str1.length() - 1);
                            str2 = str2.substring(1, str2.length() - 1);
                
                            output.append("Strings are same: ").append(str1.equals(str2)).append("\n");
                        } else {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Invalid input type for ati2StringsSame(string, string). Both inputs must be enclosed in double or single quotes.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: ati2StringsSame expects 2 arguments.\n");
                    }
                }
                else if (statement.startsWith("ati2StringsSameIgnoreCase(") && statement.endsWith(")")) {
                    String args = statement.substring(26, statement.length() - 1).trim();
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        String str1 = parts[0].trim();
                        String str2 = parts[1].trim();
                
                        if (((str1.startsWith("\"") && str1.endsWith("\"")) || (str1.startsWith("'") && str1.endsWith("'"))) &&
                            ((str2.startsWith("\"") && str2.endsWith("\"")) || (str2.startsWith("'") && str2.endsWith("'")))) {
                
                            str1 = str1.substring(1, str1.length() - 1);
                            str2 = str2.substring(1, str2.length() - 1);
                
                            output.append("Strings are same (ignore case): ").append(str1.equalsIgnoreCase(str2)).append("\n");
                        } else {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Invalid input type for ati2StringSameIgnoreCase(string, string). Both inputs must be enclosed in double or single quotes.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: ati2StringSameIgnoreCase expects 2 arguments.\n");
                    }
                }
                else if (statement.startsWith("atiCompareStringsLexicographically(") && statement.endsWith(")")) {
                    String args = statement.substring(35, statement.length() - 1).trim();
                    String[] parts = args.split(",");
                    if (parts.length == 2) {
                        String str1 = parts[0].trim();
                        String str2 = parts[1].trim();
                
                        if (((str1.startsWith("\"") && str1.endsWith("\"")) || (str1.startsWith("'") && str1.endsWith("'"))) &&
                            ((str2.startsWith("\"") && str2.endsWith("\"")) || (str2.startsWith("'") && str2.endsWith("'")))) {
                
                            str1 = str1.substring(1, str1.length() - 1);
                            str2 = str2.substring(1, str2.length() - 1);
                
                            int result = str1.compareTo(str2);
                            if (result == 0) {
                                output.append("Both strings are equal lexicographically.\n");
                            } else if (result < 0) {
                                output.append("\"").append(str1).append("\" comes before \"").append(str2).append("\" lexicographically.\n");
                            } else {
                                output.append("\"").append(str1).append("\" comes after \"").append(str2).append("\" lexicographically.\n");
                            }
                        } else {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Invalid input type for atiCompareStringsLexicographically(string, string). Both inputs must be enclosed in double or single quotes.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: atiCompareStringsLexicographically expects 2 arguments.\n");
                    }
                }
                else if (statement.startsWith("atiStringContainsAnotherString(") && statement.endsWith(")")) {
                    String args = statement.substring(31, statement.length() - 1).trim();
                    String[] parts = args.split(",", 2);
                    if (parts.length == 2) {
                        String str1 = parts[0].trim();
                        String str2 = parts[1].trim();
                
                        if (((str1.startsWith("\"") && str1.endsWith("\"")) || (str1.startsWith("'") && str1.endsWith("'"))) &&
                            ((str2.startsWith("\"") && str2.endsWith("\"")) || (str2.startsWith("'") && str2.endsWith("'")))) {
                
                            str1 = str1.substring(1, str1.length() - 1);
                            str2 = str2.substring(1, str2.length() - 1);
                
                            boolean contains = str1.contains(str2);
                            output.append(contains ? "true\n" : "false\n");
                        } else {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Invalid input type for atiStringContainsAnotherString(string, string). Both inputs must be enclosed in double or single quotes.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: atiStringContainsAnotherString expects 2 arguments.\n");
                    }
                }
                else if (statement.startsWith("atiLargestArrayNumber(")) {
                    String arrRaw = statement.substring(22, statement.length() - 1).trim();
                    if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                        arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                        try {
                            int max = Integer.MIN_VALUE;
                            String[] elements = arrRaw.split(",");
                            for (String el : elements) {
                                el = el.trim();
                                int num = Integer.parseInt(el);
                                if (num > max) max = num;
                            }
                            output.append(max + "\n");
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: All elements in the array for atilargestarraynumber must be integers.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiLargestArrayNumber(array).\n");
                    }
                }
                else if (statement.startsWith("atiArraySum(")) {
                    String arrRaw = statement.substring(12, statement.length() - 1).trim();
                    if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                        arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                        try {
                            int sum = 0;
                            String[] elements = arrRaw.split(",");
                            for (String el : elements) {
                                el = el.trim();
                                int num = Integer.parseInt(el);
                                sum += num;
                            }
                            output.append(sum + "\n");
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: All elements in the array for atiarraysum must be integers.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiArraySum(array).\n");
                    }
                }
                else if (statement.startsWith("atiSmallestArrayNumber(")) {
                    String arrRaw = statement.substring(23, statement.length() - 1).trim();
                    if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                        arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                        try {
                            int min = Integer.MAX_VALUE;
                            String[] elements = arrRaw.split(",");
                            for (String el : elements) {
                                el = el.trim();
                                int num = Integer.parseInt(el);
                                if (num < min) min = num;
                            }
                            output.append(min + "\n");
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: All elements in the array for atiSmallestArrayNumber must be integers.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiSmallestArrayNumber(array).\n");
                    }
                }
                else if (statement.startsWith("atiArrayMul(")) {
                    String arrRaw = statement.substring(12, statement.length() - 1).trim();
                    if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                        arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                        try {
                            int product = 1;
                            String[] elements = arrRaw.split(",");
                            for (String el : elements) {
                                el = el.trim();
                                int num = Integer.parseInt(el);
                                product *= num;
                            }
                            output.append(product + "\n");
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: All elements in the array for atiArrayMul must be integers.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiArrayMul(array).\n");
                    }
                }
                else if (statement.startsWith("atiAverageOfArrayElements(")) {
                    String arrRaw = statement.substring(26, statement.length() - 1).trim();
                    if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                        arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                        try {
                            String[] elements = arrRaw.split(",");
                            int sum = 0;
                            int count = 0;
                            for (String el : elements) {
                                el = el.trim();
                                int num = Integer.parseInt(el);
                                sum += num;
                                count++;
                            }
                            if (count == 0) {
                                g=1;
                        output.setLength(0);
                                output.append("Error: Cannot compute average of an empty array.\n");
                            } else {
                                double average = (double) sum / count;
                                output.append(average + "\n");
                            }
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: All elements in the array for atiAverageOfArrayElements must be integers.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiAverageOfArrayElements(array).\n");
                    }
                }
                else if (statement.startsWith("atiDivideAllArrayElementsByANumber(")) {
                    String params = statement.substring(35, statement.length() - 1).trim();
                    int commaIndex = params.lastIndexOf("],");
                    if (commaIndex != -1) {
                        String arrRaw = params.substring(0, commaIndex + 1).trim();
                        String divisorRaw = params.substring(commaIndex + 2).trim();
                
                        if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                            arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                            try {
                                int divisor = Integer.parseInt(divisorRaw);
                                if (divisor == 0) {
                                    g=1;
                        output.setLength(0);
                                    output.append("Error: Division by zero is not allowed.\n");
                                } else {
                                    String[] elements = arrRaw.split(",");
                                    StringBuilder result = new StringBuilder("[");
                                    for (int i = 0; i < elements.length; i++) {
                                        String el = elements[i].trim();
                                        int num = Integer.parseInt(el);
                                        double divided = (double) num / divisor;
                                        result.append(divided);
                                        if (i < elements.length - 1) {
                                            result.append(", ");
                                        }
                                    }
                                    result.append("]");
                                    output.append(result.toString() + "\n");
                                }
                            } catch (NumberFormatException e) {
                                g=1;
                        output.setLength(0);
                                output.append("Error: All elements in the array and the divisor must be integers.\n");
                            }
                        } else {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Invalid input type for atiDivideAllArrayElementsByANumber(array).\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid syntax for atiDivideAllArrayElementsByANumber(array, number).\n");
                    }
                }
                else if (statement.startsWith("atiSearchOfNumberInArray(")) {
                    String params = statement.substring(25, statement.length() - 1).trim();
                    int commaIndex = params.lastIndexOf("],");
                    if (commaIndex != -1) {
                        String arrRaw = params.substring(0, commaIndex + 1).trim();
                        String searchValueRaw = params.substring(commaIndex + 2).trim();
                
                        if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                            arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                            try {
                                int searchValue = Integer.parseInt(searchValueRaw);
                                String[] elements = arrRaw.split(",");
                                boolean found = false;
                                for (String el : elements) {
                                    int num = Integer.parseInt(el.trim());
                                    if (num == searchValue) {
                                        found = true;
                                        break;
                                    }
                                }
                                output.append(found + "\n");
                            } catch (NumberFormatException e) {
                                g=1;
                        output.setLength(0);
                                output.append("Error: All elements in the array and the search value must be integers.\n");
                            }
                        } else {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Invalid input type for atiSearchOfNumberInArray(array).\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid syntax for atiSearchOfNumberInArray(array, number).\n");
                    }
                }
                else if (statement.startsWith("atiArrayCheckSortAscending(")) {
                    String arrRaw = statement.substring(27, statement.length() - 1).trim();
                    if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                        arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                        try {
                            String[] elements = arrRaw.split(",");
                            boolean isSorted = true;
                            for (int i = 0; i < elements.length - 1; i++) {
                                int num1 = Integer.parseInt(elements[i].trim());
                                int num2 = Integer.parseInt(elements[i + 1].trim());
                                if (num1 > num2) {
                                    isSorted = false;
                                    break;
                                }
                            }
                            output.append(isSorted + "\n");
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: All elements in the array for atiArrayCheckSortAscending must be integers.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiArrayCheckSortAscending(array).\n");
                    }
                }
                else if (statement.startsWith("atiArrayCheckSortDescending(")) {
                    String arrRaw = statement.substring(28, statement.length() - 1).trim();
                    if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                        arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                        try {
                            String[] elements = arrRaw.split(",");
                            boolean isSorted = true;
                            for (int i = 0; i < elements.length - 1; i++) {
                                int num1 = Integer.parseInt(elements[i].trim());
                                int num2 = Integer.parseInt(elements[i + 1].trim());
                                if (num1 < num2) {
                                    isSorted = false;
                                    break;
                                }
                            }
                            output.append(isSorted + "\n");
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: All elements in the array for atiArrayCheckSortDescending must be integers.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiArrayCheckSortDescending(array).\n");
                    }
                }
                else if (statement.startsWith("atiPrintArrayReverse(")) {
                    String arrRaw = statement.substring(21, statement.length() - 1).trim();
                    if (arrRaw.startsWith("[") && arrRaw.endsWith("]")) {
                        arrRaw = arrRaw.substring(1, arrRaw.length() - 1);
                        try {
                            String[] elements = arrRaw.split(",");
                            int[] nums = new int[elements.length];
                            for (int i = 0; i < elements.length; i++) {
                                nums[i] = Integer.parseInt(elements[i].trim());
                            }
                            for (int i = nums.length - 1; i >= 0; i--) {
                                output.append(nums[i]);
                                if (i > 0) output.append(" ");
                            }
                            output.append("\n");
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: All elements in the array for atiPrintArrayReverse must be integers.\n");
                        }
                    } else {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiPrintArrayReverse(array).\n");
                    }
                }
                else if (statement.startsWith("atiCelsiusToFarhenheit(")) {
                    String valueRaw = statement.substring(23, statement.length() - 1).trim();
                    try {
                        double celsius = Double.parseDouble(valueRaw);
                        double fahrenheit = (9.0 / 5.0 * celsius) + 32;
                        output.append(fahrenheit + "\n");
                    } catch (NumberFormatException e) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiCelsiusToFarhenheit(number).\n");
                    }
                }
                else if (statement.startsWith("atiFarhenheitToCelsius(")) {
                    String valueRaw = statement.substring(23, statement.length() - 1).trim();
                    try {
                        double fahrenheit = Double.parseDouble(valueRaw);
                        double celsius = 5.0 / 9.0 * (fahrenheit - 32);
                        output.append(celsius + "\n");
                    } catch (NumberFormatException e) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiFarhenheitToCelsius(number).\n");
                    }
                }
                else if (statement.startsWith("atiAreaOfCircle(")) {
                    String valueRaw = statement.substring(16, statement.length() - 1).trim();
                    try {
                        double radius = Double.parseDouble(valueRaw);
                        if (radius < 0) {
                            output.append("Error: Radius cannot be negative for atiAreaOfCircle.\n");
                        } else {
                            double area = Math.PI * radius * radius;
                            output.append(area + "\n");
                        }
                    } catch (NumberFormatException e) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiAreaOfCircle(number).\n");
                    }
                }
                else if (statement.startsWith("atiPerimeterOfCircle(")) {
                    String valueRaw = statement.substring(21, statement.length() - 1).trim();
                    try {
                        double radius = Double.parseDouble(valueRaw);
                        if (radius < 0) {
                            output.append("Error: Radius cannot be negative for atiPerimeterOfCircle.\n");
                        } else {
                            double perimeter = 2 * Math.PI * radius;
                            output.append(perimeter + "\n");
                        }
                    } catch (NumberFormatException e) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiPerimeterOfCircle(number).\n");
                    }
                }
                else if (statement.startsWith("atiAreaOfSquare(")) {
                    String valueRaw = statement.substring(16, statement.length() - 1).trim();
                    try {
                        double side = Double.parseDouble(valueRaw);
                        if (side < 0) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Side length cannot be negative for atiAreaOfSquare.\n");
                        } else {
                            double area = side * side;
                            output.append(area + "\n");
                        }
                    } catch (NumberFormatException e) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiAreaOfSquare(number).\n");
                    }
                }
                else if (statement.startsWith("atiPerimeterOfSquare(")) {
                    String valueRaw = statement.substring(21, statement.length() - 1).trim();
                    try {
                        double side = Double.parseDouble(valueRaw);
                        if (side < 0) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Side length cannot be negative for atiPerimeterOfSquare.\n");
                        } else {
                            double perimeter = 4 * side;
                            output.append(perimeter + "\n");
                        }
                    } catch (NumberFormatException e) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiPerimeterOfSquare(number).\n");
                    }
                }
                else if (statement.startsWith("atiAreaOfRectangle(")) {
                    String valueRaw = statement.substring(19, statement.length() - 1).trim();
                    String[] values = valueRaw.split(",");
                    if (values.length != 2) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: atiAreaOfRectangle requires two numeric arguments (length, width).\n");
                    } else {
                        try {
                            double length = Double.parseDouble(values[0].trim());
                            double width = Double.parseDouble(values[1].trim());
                            if (length < 0 || width < 0) {
                                g=1;
                        output.setLength(0);
                                output.append("Error: Length and width cannot be negative for atiAreaOfRectangle.\n");
                            } else {
                                double area = length * width;
                                output.append(area + "\n");
                            }
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Invalid input type for atiAreaOfRectangle(length, width).\n");
                        }
                    }
                }
                else if (statement.startsWith("atiPerimeterOfRectangle(")) {
                    String valueRaw = statement.substring(24, statement.length() - 1).trim();
                    String[] values = valueRaw.split(",");
                    if (values.length != 2) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: atiPerimeterOfRectangle requires two numeric arguments (length, width).\n");
                    } else {
                        try {
                            double length = Double.parseDouble(values[0].trim());
                            double width = Double.parseDouble(values[1].trim());
                            if (length < 0 || width < 0) {
                                g=1;
                        output.setLength(0);
                                output.append("Error: Length and width cannot be negative for atiPerimeterOfRectangle.\n");
                            } else {
                                double perimeter = 2 * (length + width);
                                output.append(perimeter + "\n");
                            }
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Invalid input type for atiPerimeterOfRectangle(length, width).\n");
                        }
                    }
                }
                else if (statement.startsWith("atiAreaOfTriangle(")) {
                    String valueRaw = statement.substring(18, statement.length() - 1).trim();
                    String[] values = valueRaw.split(",");
                    if (values.length != 2) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: atiAreaOfTriangle requires two numeric arguments (base, height).\n");
                    } else {
                        try {
                            double base = Double.parseDouble(values[0].trim());
                            double height = Double.parseDouble(values[1].trim());
                            if (base < 0 || height < 0) {
                                g=1;
                        output.setLength(0);
                                output.append("Error: Base and height cannot be negative for atiAreaOfTriangle.\n");
                            } else {
                                double area = 0.5 * base * height;
                                output.append(area + "\n");
                            }
                        } catch (NumberFormatException e) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Invalid input type for atiAreaOfTriangle(base, height).\n");
                        }
                    }
                }
                else if (statement.startsWith("atiVolumeOfCube(")) {
                    String valueRaw = statement.substring(16, statement.length() - 1).trim();
                    try {
                        double side = Double.parseDouble(valueRaw);
                        if (side < 0) {
                            g=1;
                        output.setLength(0);
                            output.append("Error: Side length cannot be negative for atiVolumeOfCube.\n");
                        } else {
                            double volume = Math.pow(side, 3);
                            output.append(volume + "\n");
                        }
                    } catch (NumberFormatException e) {
                        g=1;
                        output.setLength(0);
                        output.append("Error: Invalid input type for atiVolumeOfCube(side).\n");
                    }
                }
                else if (statement.equals("atiAbout()")) {
                    output.append("This compiler was developed by Atiksh Sah under the mentorship of Dr. Saurav Shanu as part of the Compiler Design Lab Project.\n");
                }
                else if(statement.equals("atiBestFootballer()")){
                    output.append("That's Ronaldo like why would you even ask\n");
                }                
                else {
                    g=1;
                    output.setLength(0);
                    output.append("Error: Invalid syntax for statement: ").append(statement).append("\n");
                }
            } catch (NumberFormatException e) {
                g=1;
                        output.setLength(0);
                output.append("Error: Arguments must be integers for statement: ").append(statement).append("\n");
            } catch (Exception e) {
                g=1;
                output.append("Error: ").append(e.getMessage()).append(" for statement: ").append(statement).append("\n");
            }
        }
    
        // Display the output in the JTextArea

        outputDisplay.setText(output.toString().trim());
    }

    private int fibonacci(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1, c;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    private int factorial(int n) {
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private String primesInRange(int start, int end) {
        StringBuilder primes = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                primes.append(i).append(" ");
            }
        }
        return primes.toString().trim();
    }

    private void saveCode(JTextArea editor) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(editor.getText());
                JOptionPane.showMessageDialog(null, "File saved successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
