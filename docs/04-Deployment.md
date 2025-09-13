1. Предварительные требования
- Java 17
- Maven
- PostgreSQL (для основной БД)

2. Подготовьте базу данных PostgreSQL:
Создайте БД с именем recommendations и пользователя с правами на нее.

3. Настройте переменные среды:

### Список необходимых переменных

| Переменная | Обязательное | Описание | Пример значения |
| :--- | :--- | :--- | :--- |
| `SPRING_DATASOURCE_URL` | Да | URL для подключения к PostgreSQL | `jdbc:postgresql://localhost:5432/recommendations` |
| `SPRING_DATASOURCE_USERNAME` | Да | Имя пользователя БД | `team_worker` |
| `SPRING_DATASOURCE_PASSWORD` | Да | Пароль пользователя БД | `team` |
| `TELEGRAM_BOT_TOKEN` | Нет (если бот не нужен) | Токен для доступа к Telegram Bot API | `1234567890:ABCDEFGhijklmnopqrstuvwxyz` |

### Способы задания конфигурации

**Способ 1: Файл `application.properties` (Для разработки)**
Просто отредактируйте файл `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/recommendations
spring.datasource.username=team_worker
spring.datasource.password=team
telegram.bot.token=your_telegram_bot_token_here
```
*Это наименее безопасный способ, подходит только для локальной разработки.*

4. Соберите приложение:
mvn clean package

Запустите приложение:
java -jar target/teamWork2-0.0.1-SNAPSHOT.jar

Приложение будет доступно на http://localhost:8080.