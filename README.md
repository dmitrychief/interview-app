#### Тестовое задание для интерью компании ДомКлик

автор: Dmitry Mikhailov (dmitrymikhailov13@gmail.com)

1. Сборка - <b>`./gradlew build`</b>
2. Сборка docker образа для локального запуска - <b>`./gradlew jibDockerBuild`</b>
3. Запуск docker-compose вместе контейнером postgresql - <b>`docker-compose up`</b>


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