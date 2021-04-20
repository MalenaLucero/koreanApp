# Korean App

This is a Spring Boot REST API that looks up Korean words in a PostgreSQL database and returns authentic usage examples with English translations. 

The login was made with Spring Security and JWT.

The unitary and integration tests were made with Junit.

It's hosted on Heroku. The database URL and token secrey key come from environmental variables. Most endpoints need authorization, but here are some public GET endpoints to test on Postman or any other client (except the browser, it doesn't work there):

https://koreanapp.herokuapp.com/api/public/artist

https://koreanapp.herokuapp.com/api/public/search/lyric?word=오늘&idArtist=5

https://koreanapp.herokuapp.com/api/public/search/video?word=저녁&idArtist=3&type=RADIO

https://koreanapp.herokuapp.com/api/public/search/text?word=그런데&idArtist=3






