# Дипломный проект по профессии «Тестировщик»

Основная задача - автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

Приложение - это веб-сервис, который предлагает купить тур по определённой цене двумя способами:
1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.


Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

- сервису платежей, далее Payment Gate;
- кредитному сервису, далее Credit Gate.

Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.

Подробно с дипломным заданием можно ознакомиться по [ссылке](https://github.com/netology-code/qa-diploma)

## Тестовая документация
[План тестирования](https://github.com/Khorolskaia-V/QA_Diploma/blob/main/documents/Plan.md)
[Отчёт по итогам тестирования](https://github.com/Khorolskaia-V/QA_Diploma/blob/main/documents/Report.md)
[Отчёт по итогам автоматизации](https://github.com/Khorolskaia-V/QA_Diploma/blob/main/documents/Summary.md)

## Запуск приложения
### Подготовительный этап
1. Установить и запустить IntelliJ IDEA;
2. Установать и запустить Docker Desktop;
3. Скопировать репозиторий с Github [по ссылке](https://github.com/Khorolskaia-V/QA_Diploma).
4. Открыть проект в IntelliJ IDEA.

### Запуск тестового приложения (SUT)

1. Запускаем контейнеры с помощью команды в терминале
```
 `docker-compose up`
```
2. Запускаем SUT:

+  В консоли ввести команду для MySQL:
    
 ```
 java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar --server.port=6009
   ```
+ В консоли ввести команду для PostgreSQL:
    
```
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar --server.port=6009

```
4. Убедиться в готовности системы. Приложение должно быть доступно по адресу:
```
http://localhost:6009/
```
### Запуск тестов
В новой вкладке терминала запустить тесты.
+ для MySQL:
```
./gradlew clean test "-Ddatasource.url=jdbc:mysql://localhost:3306/app"
```
+ для PostgreSQL:
```
./gradlew clean test "-Ddatasource.url=jdbc:postgresql://localhost:5432/app"
```
### Формирование отчёта о тестировании
Для формирования отчётности через Allure, в новой вкладке терминала вводим команду
```
./gradlew allureReport
```
### Перезапуск тестов и приложения
Для остановки приложения в окне терминала нужно ввести команду `Ctrl+С` и повторить необходимые действия из предыдущих разделов.