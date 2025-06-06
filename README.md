# BDD Automation Project – SauceDemo

Automated UI and DB tests for [saucedemo.com](https://www.saucedemo.com/) using Carina, Cucumber, Selenium, and MyBatis.

## Tech Stack

- Java 21
- Maven
- Carina Framework
- Cucumber (BDD)
- Selenium WebDriver
- MyBatis + MySQL
- Lombok
- TestNG
- Log4j

## ⚙️ Setup

1. Clone the project:
    ```bash
    git clone https://github.com/your-username/bdd-cucumber-saucedemo.git
    cd bdd-cucumber-saucedemo
    ```

2. Create the file:
    ```
    src/main/resources/_database.properties
    ```
   And paste:

    ```properties
    db.url=jdbc:mysql://localhost:3306/saucedemo_db
    db.driver=com.mysql.cj.jdbc.Driver
    db.user=root
    db.password=
    ```

3. Open the project in **IntelliJ IDEA** and wait for Maven to download dependencies.

4. Make sure the `saucedemo_db` database is running and has a `users` table with:
    - `username`, `password`, `first_name`, `last_name`, `zip_code`



 