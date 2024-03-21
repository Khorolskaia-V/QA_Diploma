# Дипломный проект по профессии «Тестировщик»

Основная задача - автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

Приложение - это веб-сервис, который предлагает купить тур по определённой цене двумя способами:
1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.

![Screenshot_31.png](..%2F..%2F..%2F%FF%EC%E0%2FScreenshot_31.png)

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

+ для MySQL:

    + В консоле ввести команду:
 ```
 java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
   ```
+ для PostgreSQL:
    + В консоле ввести команду:

```
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
```

3. В новой вкладке терминала запустить приложение командой:
```
java -jar ./artifacts/aqa-shop.jar
```

4. Убедиться в готовности системы. Приложение должно быть доступно по адресу:
```
http://localhost:8009/
```
### Запуск тестов
В новой вкладке терминала запустить тесты:
```
./gradlew clean test
```
### Формирование отчёта о тестировании
Для формирования отчётности через Allure, в новой вкладке терминала вводим команду
```
./gradlew allureReport
```
### Перезапуск тестов и приложения
Для остановки приложе