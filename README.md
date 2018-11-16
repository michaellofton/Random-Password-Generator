# Random Password Generator
This program is a simple desktop application designed to help people easily generate, copy, and save random strings that can be used as passwords.

<img src="/img/make-pass.png" style="text-align: center">

## Features:
- Portable (works on desktop and laptop machines that have Java (AKA the Java Runtime Environment (JRE)) installed).
  - Download Java here: https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
- Save Passwords to a specified location on hard disk or removable disk.
- Generate passwords up to 1024 characters long.
- Change the type of characters used in the password (lowercase, uppercase, special, and numbers).

## Uses:      
Possible applications and usage for this program include:
- Creating a strong password for account registration. 
- Statistically analyzing the distribution of the randomly chosen characters.
- Other applications involving the need for random string generation.

## Design Features
The majority of the functionality of the program was written in Java while parts of the graphical user interface and event handling were made using JavaFX. 

The program was created using the Model-view-controller design pattern, which creates a communication of data from the model (the part that generates the password), the view (what the user sees) and the controller (the part that updates the view using information from the model). 

Modularity was incorporated by allowing each module (java class file) to be modified individually and to remain separated from the other modules. This affects the program's changeability, allowing it to be adaptable to any necessary changes in the future. 

The program also uses a label to inform the user if any bad input was detected, and changes the label to help the user know which type of input to provide depending on the error.

The mechanism that makes this program "random" is the SecureRandom class from the java.security package. The class uses a cryptographically secure pseudo random number generating (PRNG) algorithm to generate a random number. This random number is then transformed into an index to choose a character.
