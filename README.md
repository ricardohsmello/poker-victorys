![Issues](https://img.shields.io/github/issues/ricardohsmello/poker-score-microservices) 
![Forks](https://img.shields.io/github/forks/ricardohsmello/poker-score-microservices) 
![Stars](https://img.shields.io/github/stars/ricardohsmello/poker-score-microservices) 
![Release Version](https://img.shields.io/github/release/ricardohsmello/poker-score-microservices)

# poker-score

Poker score é um projeto que tem como objetivo simplesmente cadastrar e pontuar um jogador de poker
Ex:
- João, 15 pontos
- Rubens, 12 pontos ..

Para este exemplo, foi utilizada uma arquitetura de microservices usando Spring-cloud e mensageria.

![Quick Start quarkus](https://i.ibb.co/ZGQ2dtk/flow-poker.png)

# Built With
- Java 11
- Spring-cloud 
- RabbitMQ

# Prerequisites
 - Docker
 - JVM 11 
 - Postgres
 - RabbitMQ
 
# Rodando o projeto
## Subindo o banco e o RabbitMQ
É necessário rodar o RabbitMQ e uma instância do Postgres. Com o arquivo, docker-compose.yml é possível sanar essas necessidades
Na raíz:
`docker-compose up`.

## Subindo o config-server, eureka-service, zuul, score-consumer e score-registry
Dentro de cada pasta respectivamente, rodar:
`mvn spring-boot:run`.

## Subindo o player
Vamos rodar o serviço de player para que possamos cadastrar alguns jogadores.
Dentro do projeto de player:
`mvn spring-boot:run -Dskiptests`.

Se tudo ocorrer bem, teremos um endpoint pronto para cadastrar jogadores:
### POST /api/player
```json 
    
     {
       "name": "Joao",
       "code": "cod205"
     }
 
```

## Subindo o score-producer
Dentro do projeto score-producer:
`mvn spring-boot:run -Dskiptests`.
Se tudo ocorrer bem, já podemos testar nossos serviços:

### POST /api/score
```json 
    
  {
	"playerCode": "cod205",
	"value": 5
  }
 
```

# FLOW
- O client somente tem acesso ao zuul na porta 5555 do endpoint /api/score.
- Após isso, o zuul reconhece todos serviços registrados no Eureka
- O projeto score-producer registra na fila do Rabbit uma mensagem com um valor (pontuação) e o código do jogador.
- Após isso, o consumer lê das filas do rabbit todas mensagens pendentes. 
- Após ler a mensagem com o valor e código, o consumer faz uma chamada ao serviço de player para consultar o nome do usuário (Spring Feign)
- Na sequência, outra chamada para um terceiro serviço: score-registry. Nesse ponto, é chamado o serviço para persistir os dados com o nome e pontuação.
