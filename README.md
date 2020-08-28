# AESProject
In this project, different modes of AES encryption and decryption have been implemented from the scratch. It supports ECB (Electronic Codebook) and CBC (Cipher Blocker Chaining) modes. The user-friendly GUI allows the user to choose the file to be encrypted or decrypted. The GUI also lets the user choose different modes of AES encryption (CBC and ECB). 
To verify the correctness of the implementation, the encryption and decryption functionalities using Java Security package from Java Cryptography Architecture (JCA) is also provided in the GUI. 

HOW TO RUN THE PROJECT
1) Create a java project in any of your prefered java IDE (e.g., Netbeans, Intellij, Eclipse etc.)
2) Include the source code provided in the src folder
3) Run the ControlUI.java file

HOW TO USE THE SOFTWARE

A) Selecting Encryption Parameters/Configuration:
1) Select Appropriate key size (currently supports only 128 bits) and give the input key in the text field
2) Select the mode and give IV if CBC is selected.
3) Select if padding will be added. If yes, then input padding string. Please note that, if the file size is not multiple of block size and padding is not selected, the code automatically makes it multiple of block size by adding a marker followed by all 0’s.

B) Selecting Input/Output File:
1) You can choose any type of file by clicking on the “Choose File” button from the GUI.
2) Give a name for the output file.

Encrypt/Decrypt using our implemented AES
1) After following step A and B, you can perform encryption or decryption by clicking the dedicated button. You can give whatever name you want to the output file while you are encrypting. However, the decryption file name should be with the proper extension in order to open it with the appropriate application. For example, if you are encrypting a file named abc.docx, a good name during encryption can be abc.docx.enc. During decryption, you can name it abc.docx so that it can be open with MS Word application.
2) Output file will be generated in the default project location.

Encrypt/Decrypt using library AES
The process is same as the implemented AES.

Things to Note:
1) Library implementation and manual implementation will give the same output (both encryption and decryption) as long as
ECB mode is selected. Its irrespective of padding.
2) Library implementation only work in ECB mode. CBC mode does not work in library mode even if selected from GUI.

FUTURE EXTENSION

The plan for the future is to implement public key cryptography such as RSA and compare with AES.

TECHNOLOGY USED

Language: Java
IDE: Netbeans
Package: java.security
