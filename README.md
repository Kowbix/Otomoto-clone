# Otomoto-clone

# Polish Version

Projekt Aplikacji Ogłoszeniowej jest klonem popularnego serwisu ogłoszeniowego Otomoto, który umożliwia użytkownikom dodawanie, przeglądanie i subskrybowanie ogłoszeń dotyczących samochodów. Aplikacja została zbudowana w języku Java z wykorzystaniem technologii Spring Boot, Spring Security, Hibernate oraz PostgreSQL.

### Funkcje

- Dodawanie ogłoszeń samochodowych
- Subskrybowanie ogłoszeń po marce, modelu i generacji
- Automatyczne wysyłanie maili z nowymi ofertami dla subskrybentów
- Zarządzanie ogłoszeniami przez użytkowników oraz administratora
- Integracja z AWS S3 do przechowywania zdjęć i opisów ogłoszeń

### Wymagania Systemowe

- Java 17 lub nowsza
- Maven
- Baza danych PostgreSQL
- Konta AWS S3 do przechowywania danych
- Inny serwer SMTP do wysyłania maili (opcjonalnie)

### Instrukcja Uruchomienia

1. Sklonuj repozytorium
2. Zainstaluj zależności Maven: `mvn clean install`
3. Skonfiguruj dostęp do bazy danych PostgreSQL w pliku `application.properties`
4. Skonfiguruj dostęp do konta AWS S3 w pliku `application.properties`
5. Skonfiguruj dostęp do serwera mailowego w pliku `application.properties`

# English Version

The Classifieds Application Project is a clone of the popular classified ads service Otomoto, which allows users to add, browse, and subscribe to car advertisements. The application is built in Java using Spring Boot, Spring Security, Hibernate, and PostgreSQL technologies.

### Features

- Adding car advertisements
- Subscribing to advertisements by brand, model, and generation
- Automatic email sending with new offers for subscribers
- Managing advertisements by users and administrators
- Integration with AWS S3 for storing advertisement images and descriptions

### System Requirements

- Java 17 or newer
- Maven
- PostgreSQL database
- AWS S3 accounts for storing data
- Another SMTP server for sending emails (optional)

### Setup Instructions

1. Clone the repository
2. Install Maven dependencies: `mvn clean install`
3. Configure access to the PostgreSQL database in the `application.properties` file
4. Configure access to AWS S3 in the `application.properties` file
5. Configure access to the mail server in the `application.properties` file
