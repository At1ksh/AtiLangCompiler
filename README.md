# AtiLang Syntax Manual

---

## 1. Arithmetic Operations

- **`atiAdd(a, b)`**  
  **Syntax:** `atiAdd(5,3);`  
  **Explanation:** Returns the sum of `a` and `b`.

- **`atiSub(a, b)`**  
  **Syntax:** `atiSub(5,3);`  
  **Explanation:** Returns the difference of `a` and `b`.

- **`atiMul(a, b)`**  
  **Syntax:** `atiMul(5,3);`  
  **Explanation:** Returns the product of `a` and `b`.

- **`atiDiv(a, b)`**  
  **Syntax:** `atiDiv(5,3);`  
  **Explanation:** Returns the quotient of `a` divided by `b`.

- **`atiRemainder(a, b)`**  
  **Syntax:** `atiRemainder(5,3);`  
  **Explanation:** Returns the remainder when `a` is divided by `b`.

- **`atiFactorial(n)`**  
  **Syntax:** `atiFactorial(5);`  
  **Explanation:** Returns the factorial of `n`.

- **`atiFibo(n)`**  
  **Syntax:** `atiFibo(7);`  
  **Explanation:** Returns the `n`-th Fibonacci number.

- **`atiOneDigitNumber(n)`**  
  **Syntax:** `atiOneDigitNumber(5);`  
  **Explanation:** Returns true if `n` is a single-digit number.

---

## 2. Number Theory & Math Functions

- **`atiEvenOrOdd(n)`**  
  **Syntax:** `atiEvenOrOdd(4);`  
  **Explanation:** Returns `"Even"` or `"Odd"`.

- **`atiMax(a, b)`**  
  **Syntax:** `atiMax(5,3);`  
  **Explanation:** Returns the maximum of `a` and `b`.

- **`atiMin(a, b)`**  
  **Syntax:** `atiMin(5,3);`  
  **Explanation:** Returns the minimum of `a` and `b`.

- **`atiExp(base, exponent)`**  
  **Syntax:** `atiExp(2,3);`  
  **Explanation:** Returns `base^exponent`.

- **`atiRoot(n, degree=2)`**  
  **Syntax:** `atiRoot(25,2);`  
  **Explanation:** Returns the degree root of `n`.

- **`atiGCD(a, b)`**  
  **Syntax:** `atiGCD(24,36);`  
  **Explanation:** Returns the greatest common divisor of `a` and `b`.

- **`atiLCM(a, b)`**  
  **Syntax:** `atiLCM(12,18);`  
  **Explanation:** Returns the least common multiple of `a` and `b`.

- **`atiPrimeNumber(n)`**  
  **Syntax:** `atiPrimeNumber(17);`  
  **Explanation:** Returns true if `n` is prime.

- **`atiPrimeInRange(start, end)`**  
  **Syntax:** `atiPrimeInRange(10,50);`  
  **Explanation:** Returns prime numbers in the given range.

---

## 3. String Operations

- **`atiVowels(s)`**  
  **Syntax:** `atiVowels("hello");`  
  **Explanation:** Returns the count of vowels in `s`.

- **`atiRevString(s)`**  
  **Syntax:** `atiRevString("hello");`  
  **Explanation:** Reverses the string `s`.

- **`atiStringPalindrome(s)`**  
  **Syntax:** `atiStringPalindrome("madam");`  
  **Explanation:** Returns true if `s` is a palindrome.

- **`atiStringToUppercase(s)`**  
  **Syntax:** `atiStringToUppercase("hello");`  
  **Explanation:** Converts all letters in `s` to uppercase.

- **`atiStringToLowercase(s)`**  
  **Syntax:** `atiStringToLowercase("HELLO");`  
  **Explanation:** Converts all letters in `s` to lowercase.

- **`atiFirstLetterCapital(s)`**  
  **Syntax:** `atiFirstLetterCapital("hello world");`  
  **Explanation:** Capitalizes the first letter of each word in `s`.

- **`atiReplaceSpaceWithUnderscore(s)`**  
  **Syntax:** `atiReplaceSpaceWithUnderscore("hello world");`  
  **Explanation:** Replaces spaces with underscores in `s`.

- **`atiCountNumberOfWords(s)`**  
  **Syntax:** `atiCountNumberOfWords("hello world");`  
  **Explanation:** Returns the number of words in `s`.

- **`atiLengthOfString(s)`**  
  **Syntax:** `atiLengthOfString("hello");`  
  **Explanation:** Returns the length of `s`.

- **`ati2StringsSame(s1, s2)`**  
  **Syntax:** `ati2StringsSame("hello", "hello");`  
  **Explanation:** Returns true if `s1` and `s2` are the same.

- **`ati2StringsSameIgnoreCase(s1, s2)`**  
  **Syntax:** `ati2StringsSameIgnoreCase("Hello", "hello");`  
  **Explanation:** Returns true if `s1` and `s2` are the same, ignoring case.

- **`atiCompareStringsLexicographically(s1, s2)`**  
  **Syntax:** `atiCompareStringsLexicographically("apple", "banana");`  
  **Explanation:** Compares `s1` and `s2` lexicographically.

- **`atiStringContainsAnotherString(s1, s2)`**  
  **Syntax:** `atiStringContainsAnotherString("hello world", "world");`  
  **Explanation:** Returns true if `s1` contains `s2`.

---

## 4. Array Operations

- **`atiLargestArrayNumber(arr)`**  
  **Syntax:** `atiLargestArrayNumber([1,2,3]);`  
  **Explanation:** Returns the largest number in `arr`.

- **`atiSmallestArrayNumber(arr)`**  
  **Syntax:** `atiSmallestArrayNumber([1,2,3]);`  
  **Explanation:** Returns the smallest number in `arr`.

- **`atiArraySum(arr)`**  
  **Syntax:** `atiArraySum([1,2,3]);`  
  **Explanation:** Returns the sum of all elements in `arr`.

- **`atiArrayMul(arr)`**  
  **Syntax:** `atiArrayMul([1,2,3]);`  
  **Explanation:** Returns the product of all elements in `arr`.

- **`atiAverageOfArrayElements(arr)`**  
  **Syntax:** `atiAverageOfArrayElements([1,2,3]);`  
  **Explanation:** Returns the average of elements in `arr`.

- **`atiDivideAllArrayElementsByANumber(arr, n)`**  
  **Syntax:** `atiDivideAllArrayElementsByANumber([10,20], 2);`  
  **Explanation:** Divides each element in `arr` by `n`.

- **`atiSearchOfNumberInArray(arr, x)`**  
  **Syntax:** `atiSearchOfNumberInArray([1,2,3], 2);`  
  **Explanation:** Returns true if `x` is found in `arr`.

- **`atiArrayCheckSortAscending(arr)`**  
  **Syntax:** `atiArrayCheckSortAscending([1,2,3]);`  
  **Explanation:** Returns true if `arr` is sorted in ascending order.

- **`atiArrayCheckSortDescending(arr)`**  
  **Syntax:** `atiArrayCheckSortDescending([3,2,1]);`  
  **Explanation:** Returns true if `arr` is sorted in descending order.

- **`atiPrintArrayReverse(arr)`**  
  **Syntax:** `atiPrintArrayReverse([1,2,3]);`  
  **Explanation:** Prints the elements of `arr` in reverse order.

---

## 5. Geometry & Conversions

- **`atiCelsiusToFarhenheit(c)`**  
  **Syntax:** `atiCelsiusToFarhenheit(25);`  
  **Explanation:** Converts Celsius to Fahrenheit using `F = (9/5 * C) + 32`.

- **`atiFarhenheitToCelsius(f)`**  
  **Syntax:** `atiFarhenheitToCelsius(77);`  
  **Explanation:** Converts Fahrenheit to Celsius using `C = 5/9 * (F - 32)`.

- **`atiAreaOfCircle(r)`**  
  **Syntax:** `atiAreaOfCircle(5);`  
  **Explanation:** Returns the area using `A = πr²`.

- **`atiPerimeterOfCircle(r)`**  
  **Syntax:** `atiPerimeterOfCircle(5);`  
  **Explanation:** Returns the circumference using `P = 2πr`.

- **`atiAreaOfSquare(side)`**  
  **Syntax:** `atiAreaOfSquare(4);`  
  **Explanation:** Returns the area using `A = side²`.

- **`atiPerimeterOfSquare(side)`**  
  **Syntax:** `atiPerimeterOfSquare(4);`  
  **Explanation:** Returns the perimeter using `P = 4 * side`.

- **`atiAreaOfRectangle(l, w)`**  
  **Syntax:** `atiAreaOfRectangle(5,3);`  
  **Explanation:** Returns the area using `A = l * w`.

---
