#### Тестовое задание 

автор: Dmitry Mikhailov (dmitrymikhailov13@gmail.com)

###### Задача:
Надо написать веб-приложение, цель которого - операции со счетами пользователей.
Должно быть 3 API (RESTful) - перевод денег с одного счёта на другой, положить деньги на счёт, снять деньги со счёта. Счет не может уйти в овердрафт(минусовой остаток счета).
В качестве хранилища можно использовать любую реляционную БД.
Исходный код должен собираться с помощью maven или gradle в исполняемый jar. Решение должно быть на Java или Kotlin и должно быть покрыто тестами.

###### Решение:
Приложение реализовано с использованием Spring Boot. БД - postgresql. 
Для доступа в базу используется Spring Data Jdbc 
(для данной задачи нет необходимости использовать Data Jpa вместе с Hibernate)
Основные сущности представлены как User и Account (пользователь и его счет(а)). 
Каждая сущность представлена соответствующим контроллером, сервисом и репозитарием.
Для простоты реализации мы не используем Dto и мапперы (например mapstruct), которые будут преобразовывать entity<->dto.

Для создания схемы БД предусмотрены скрипты миграции на liquibase. 
Для простоты они находятся в модуле приложения src/main/resources/db/changelog/db.changelog-master.yaml 
и запускаются автоматически на старте приложения

Тесты написаны с использованием junit5, mockito, spring-test, для тестов бд используется dbunit и testcontainers.

Операции со счетами пользователей описаны в AccountService как методы 
1. transferIn (внесение средств)
2. transferOut (списание средств)
3. transferTo (перевод средств)

В реализации transferTo мы указали уровень изоляции транзакций REPEATABLE_READ, 
т.к. в данном методе мы можем получить неповторяющееся чтение (при ситуации перевода между одним и тем же счетом пользователя)
Остальные операции используют дефолтный для postgresql уровень изоляции транзакций READ_COMMITTED 
(позволяет предотвратить lost update и dirty read)

Также для простоты реализации в приложении не реализована авторизация, но подразумевается, 
что пользователь может выполнять операции только от своего пользователя и только со своими счетами 
(снять со своего счета, зачислить на свой, перевести со своего счета на любой другой счет любого пользователя).
Для примера можно настроить spring security, чтобы аутентифицировать пользователя (генерация пароля при создании пользователя)
через http-basic и предоставлять Principal в каждый метод контроллера, из которого мы сможем получить userName.
Далее с помощью userName в сервисе мы сможем указать имя пользователя при запросах в БД, для того, чтобы проверить, 
является ли данный пользователь владельцем сущности.   

Также build.gradle.kts написан максимально просто, основа сгенерирована через start.spring.io. 
Библиотеки и плагины не вынесены в отдельный файл, а указаны напрямую в скрипте.

PS
Приложение написано на kotlin, но написано по привычке, с которой пишутся java приложения :) , 
т.е. не используются какие-то конкретные особенности работы со spring на kotlin

##### Сборка и запуск
1. Сборка - <b>`./gradlew build`</b>
2. Сборка docker образа для локального запуска - <b>`./gradlew jibDockerBuild`</b> (собрать jar можно командой `./gradlew bootJar`)
3. Запуск docker-compose вместе контейнером postgresql - <b>`docker-compose up`</b>

##### Взаимодействие с api

<pre>
проверка доступности приложения
curl localhost:8080/actuator/health

проверим, что пользователей пока нет
curl localhost:8080/user
[]

создадим пару пользователей
curl -X POST localhost:8080/user?name=alice
{"id":1,"name":"alice"} 
curl -X POST localhost:8080/user?name=bob
{"id":2,"name":"bob"}

создадим счета для данных пользователей
curl -X POST localhost:8080/account?userId=1
{"id":1,"userId":1,"amount":0} 
curl -X POST localhost:8080/account?userId=2
{"id":2,"userId":2,"amount":0}

внесение средств на счет
curl -X PUT 'localhost:8080/account/transfer/in?userId=1&id=1&amount=3'
{"id":1,"userId":1,"amount":3}

снятие средств со счета
curl -X PUT 'localhost:8080/account/transfer/out?userId=1&id=1&amount=1'
{"id":1,"userId":1,"amount":2}

попытки снять отрицательную сумму, сумму приводящую к овердрафту и попытка снятия с несуществующего счета
curl -X PUT 'localhost:8080/account/transfer/out?userId=1&id=1&amount=-111'
{"timestamp":"2020-10-11T18:13:23.834+00:00","status":404,"error":"Not Found","message":"","path":"/account/transfer/out"} 
curl -X PUT 'localhost:8080/account/transfer/out?userId=1&id=1&amount=111'
{"timestamp":"2020-10-11T18:13:32.285+00:00","status":404,"error":"Not Found","message":"","path":"/account/transfer/out"} 
curl -X PUT 'localhost:8080/account/transfer/out?userId=1&id=12222&amount=111'
{"timestamp":"2020-10-11T18:13:36.889+00:00","status":404,"error":"Not Found","message":"","path":"/account/transfer/out"}

перевод средств со счета на счет
curl -X PUT 'localhost:8080/account/transfer/to?userId=1&id=1&amount=1&toUserId=2&toAccountId=2'
{"id":1,"userId":1,"amount":1}
</pre>