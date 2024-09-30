# QR Scanner Payment Application
CashSend App enables users to make payments by scanning QR codes using their mobile devices. It is built using Kotlin in Android Studio and integrates with the Payfast payment gateway.

## Commit History for CashSend App
Please refere to the repo provided below for the repository used for majority of the creation of the CashSend Application.:
https://github.com/ST10092118/CashSend.git

## Youtube Video:
https://youtu.be/-0SThnBl9M8

## Prerequisites
Android Studio (v4.0+)
Java Development Kit (JDK) (v11+)
Kotlin Plugin
Microsoft SQL Server for backend database
Payfast API Credentials
Setup
Clone the Repository:

## bash
Copy code
git clone https://github.com/your-repo/qr-scanner-payment-app.git
Configure API Keys:
Update strings.xml with your Payfast API credentials.

## Set Up Database:
Modify the database connection string in DatabaseHelper.kt with your SQL Server details.

## How to use CashSend App
Build & Run
Sync Gradle Files and Build the Project in Android Studio.
Connect your Android device, enable USB Debugging, and click Run.
Usage
Open the app and tap Scan QR to scan a QR code.
Confirm payment details and tap Confirm.
View the payment status message upon completion.
