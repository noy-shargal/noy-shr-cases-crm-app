# Support Aggregation Hub - Installation and Setup Guide

This guide provides step-by-step instructions on how to install and run the Support Aggregation Hub application, including the installation of the MariaDB database, configuring the application properties, performing the Maven build, and running the Spring Boot application.

## Prerequisites

Before proceeding with the installation, ensure that the following prerequisites are met:

1. Java Development Kit (JDK) 17 or later is installed on your system.
2. Apache Maven is installed on your system.
3. MariaDB database server is installed.

## Database Installation

1. Install MariaDB on your system by following the official documentation: [MariaDB Installation Guide](https://mariadb.com/kb/en/getting-installing-and-upgrading-mariadb/).

2. Once MariaDB is installed, create a new database for the Support Aggregation Hub application.

   ```bash
   $ mysql -u root -p
   Enter password: <Enter your MySQL root password>
   
   MariaDB [(none)]> CREATE DATABASE support_aggregation_hub;
   MariaDB [(none)]> GRANT ALL PRIVILEGES ON support_aggregation_hub.* TO 'your_username'@'localhost' IDENTIFIED BY 'your_password';
   MariaDB [(none)]> FLUSH PRIVILEGES;
   MariaDB [(none)]> EXIT;
   ```

## Application Configuration

1. Open the `application.properties` file located in the application's resource directory (`src/main/resources`).

2. Configure the database connection properties by modifying the following lines:

   ```properties
   spring.datasource.url=jdbc:mariadb://localhost:3306/support_aggregation_hub
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

   Replace `your_username` and `your_password` with the appropriate credentials for your MariaDB installation.

## Building the Application

1. Open a command-line interface or terminal and navigate to the root directory of the Support Aggregation Hub application.

2. Execute the following Maven command to build the application:

   ```bash
   $ mvn clean install
   ```

   This command compiles the source code, resolves dependencies, and generates the executable JAR file.

## Running the Application

1. After the Maven build completes successfully, execute the following command to run the Spring Boot application:

   ```bash
   $ mvn spring-boot:run
   ```

   The application will start, and you should see log output indicating a successful startup.

2. Open a web browser and access the application using the following URL: [http://localhost:8080](http://localhost:8080)

   The Support Aggregation Hub application should now be up and running.

## Additional Configuration

If you need to customize any additional configuration options, refer to the `application.properties` file in the application's resource directory. This file contains various properties that can be modified according to your specific requirements.

## Conclusion

Congratulations! You have successfully installed and configured the Support Aggregation Hub application. By following this guide, you can now run the application locally and begin aggregating and managing CRM cases effectively. Should you encounter any issues during the installation or setup process, please refer to the troubleshooting section or seek assistance from the support team.