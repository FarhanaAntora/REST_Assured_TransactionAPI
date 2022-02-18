# REST_Assured_TransactionAPI
## Prerequisites
 1. Install jdk 8 or any LTS version
 2. Configure JAVA_HOME and GRADLE_HOME
 3. Download Allure 2.17.2 and configure environment path
 4. Stable internet connection

## How to run this project
1. Clone the repo
2. Open terminal
3. Give following commands:

```
gradle clean test
```

```
allure generate allure-results --clean -o allure-report
```

```
allure serve allure-results
```

![6](https://user-images.githubusercontent.com/96485899/154740229-8e058c87-46c0-447f-b0c2-5ec91a4d95fc.PNG)
