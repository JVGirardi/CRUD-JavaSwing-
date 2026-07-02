<!--- # "Can be a image or a gift from the project pages" -->
<p align="center">
  <img src="src/main/resources/images/1_logorodape.png" alt="Library Management System">
</p>

# Library Management System (CRUD Java Swing)

A complete desktop application for library management. This system provides full CRUD operations to manage authors (`Autor`), clients (`Client`), books (`Livro`), and loans (`Emprestimo`). It also includes a secure login system (`LoginPanel`) and automated PDF report generation (`RelatorioView`).

## Tech Stack

<!--- # "Verify icons availability here https://github.com/tandpfun/skill-icons" -->
[![My Skills](https://skillicons.dev/icons?i=java,hibernate,maven,eclipse,postgres)](https://skillicons.dev)

* **Language & GUI:** Java with Swing architecture, utilizing structured panels like `FormPanel` and `TablePanel`.
* **ORM Framework:** Hibernate, fully configured for data persistence.
* **Reporting Tool:** JasperReports to generate `.jasper` and `.jrxml` loan reports.
* **Dependency Management:** Maven for project builds and library management.

## Getting Started

1. **Clone project**: `git clone https://github.com/JVGirardi/CRUD-JavaSwing.git`
2. **Install Dependencies**: Run `mvn clean install` to download all required dependencies listed in the `pom.xml`.
3. **Database Setup**: Configure your database connection properties inside the `src/main/resources/hibernate.cfg.xml` file.
4. **Start Developing**: Execute the `com.biblioteca.Main` class to launch the application and open the main interface.

## Contribute

1. **Clone project**: `git clone https://github.com/JVGirardi/CRUD-JavaSwing.git`
2. **Create feature/branch**: `git checkout -b feature/NAME`
3. **Commit your changes**: `git commit -m 'Add new feature'`
4. **Push to the branch**: `git push origin feature/NAME`
5. **Open a Pull Request**

## License

This software is available under the following licenses:

- [MIT](https://rem.mit-license.org)
