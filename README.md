# verifyuserdata
User Data verification Service

Настройки сервиса задаются в файле свойств src/main/resources/application.properties.
Необходимо задать следующие параметры запроса проверки данных Паспорта сервиса DaData:
dadata.apiKey=
dadata.secretKey=

Запуск сервиса на локальной машине осуществляется командой: gradlew bootRun

Сервис реализован в виде автономного приложения. В качестве контейнера сервлетов используется встроенный Tomcat.
