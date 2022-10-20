# Drone Feeder

Drone Feeder é uma API de armazenamento de informações relativas a entregas e drones, além de servir como um ponto de comunicação entre um front-end e múltiplos drones, onde é possível:
- Criar, buscar, atualizar e remover uma entrega;
- Criar, buscar, atualizar e remover um drone;
- Associar um drone a múltiplas entregas;
- Enviar, baixar e listar videos de entrega; e
- Associar um vídeo do drone a uma entrega.

Este projeto foi desenvolvido como um desafio final da Aceleração em Java na Trybe em parceria com a Wipro.


# Sumário

- [Contexto](#contexto)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Dependências](#dependências)
- [Como rodar a aplicação](#como-rodar-a-aplicação)
- [Como rodar os testes](#como-rodar-os-testes)
- [Documentação da API](#documentação-da-api)
  - [Drone](#drone)
  - [Delivery](#delivery)
  - [Video](#video)
- [Próximos passos](#próximos-passos)
- [Autores](#autores)


# Contexto 

A empresa "FutuereH" conseguiu uma nova patente que permitirá a entrega de pacotes com drones. Isso mesmo: o futuro já chegou para FutuereH! Com essa patente registrada, a empresa tem ao todo três meses para iniciar os testes. Sua equipe é responsável por montar o serviço Back-end, que vai fornecer informações aos drones.

Depois de muitas discussões entre os arquitetos e o CTO da empresa, o Back-end foi batizado como Drone Feeder. A Stack escolhida para a criação dele foi a linguagem Java, o banco de dados MySQL, e tudo deve rodar em containers Docker.

A arquitetura da parte do sistema em que o Drone Feeder vai atuar foi entregue à sua equipe pelo CTO e seus arquitetos com o seguinte desenho:

<img  src="assets/alttext.png" />

Confome visto, o Drone Feeder será uma aplicação REST em que um sistema Front-end vai exibir algumas informações dos drones, tais como latitude e longitude, data e horário da entrega ou retirada do pacote. Essas informações serão armazenadas no banco de dados MySQL.

O drone, por sua vez, sincronizará informações com o Drone Feeder sempre que ele tiver uma conexão com a internet. O drone ainda vai informar se a entrega foi efetuada junto a data, horário e vídeo gravado do momento da entrega.

O CTO da FutuereH confia muito na sua equipe e deu liberdade para eventuais melhorias e modificações na arquitetura proposta.


# Tecnologias utilizadas

- **Ferramentas:** Slack, VS Code, IntelliJ, Maven, Git e GitHub.
- **Linguagem:** Java.
- **Frameworks, bibliotecas e plugins:** Spring Boot, Spring Web, Spring Data JPA, Spring Boot Dev Tools, JUnit, CheckStyle e JaCoCo.
- **Banco de dados:** MySQL e H2 (somente nos testes).


# Dependências

Você precisará de [Git](https://git-scm.com/downloads), [Docker](https://docs.docker.com/engine/install/) e [Docker Compose](https://docs.docker.com/compose/install/) instalados em sua máquina para executar a aplicação.


# Como rodar a aplicação

1. No terminal, clone o repositório:
```sh
git clone https://github.com/nataelienai/drone-feeder.git
```

2. Entre na pasta do repositório clonado:
```sh
cd drone-feeder
```

3. Inicialize a aplicação com Docker Compose:
```sh
docker compose up
```

- Na primeira execução, a inicialização da aplicação pode levar alguns minutos. Aguarde até que apareça a mensagem:

```
api  | 2022-10-20 03:58:19.613  INFO 48 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
api  | 2022-10-20 03:58:19.618  INFO 48 --- [  restartedMain] i.g.n.d.DroneFeederApplication           : Started DroneFeederApplication in 0.892 seconds (JVM running for 497.837)
```


# Como rodar os testes

1. Com a aplicação já em execução, execute no terminal:
```sh
docker compose exec api mvn test
```

- Ao final da execução, o resultado dos testes será exibido.

2. Para gerar o code coverage dos testes, execute:
```sh
mvn jacoco:report
```

3. Por fim, abra em um navegador o arquivo do diretório `target/site/jacoco/index.html` para visualizar o code coverage.


# Documentação da API

## **Drone**

#### Cria o drone

```http
  POST /drone
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `null` | `null` | **Não é obrigatório** |

#### Retorna todos os items

```http
  GET /drone
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `null` | `null` | **Não é obrigatório** |

#### Retorna um item

```http
  GET /drone/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id` | `long` | **Obrigatório**. O ID do item que você quer |

#### Altera um item

```http
  PUT /drone/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id` | `long` | **Obrigatório**. O ID do item que você quer |

#### Deleta um item

```http
  DELETE /drone/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id` | `long` | **Obrigatório**. O ID do item que você quer |

## **Delivery**

#### Cria o delivery

```http
  POST /
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `null` | `null` | **Não é obrigatório** |

#### Retorna todos os items

```http
  GET /
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `null` | `null` | **Não é obrigatório** |


#### Altera um item

```http
  PUT /{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id` | `long` | **Obrigatório**. O ID do item que você quer |

#### Altera um drone

```http
  PUT /{id}/{droneId}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id, droneId` | `long` | **Obrigatório**. O ID do item que você quer |

#### Deleta um item

```http
  DELETE /{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id` | `long` | **Obrigatório**. O ID do item que você quer |

## **Video**

#### Retorna todos os videos

```http
  GET /video
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `null` | `null` | **Não é obrigatório** |


#### Envia um item

```http
  POST video/upload/delivery/{deliveryId}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `deliveryId` | `file` | **Obrigatório**. O video que você quer |

#### Faz o donwload do arquivo

```http
  GET /download/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id` | `long` | **Obrigatório**. O ID do item que você quer |

# Próximos passos

* Deploy 

* Implementar o Front-End

* Swagger 

* Implementar arquitetura de microsserviços

# Autores

[<img src="https://avatars.githubusercontent.com/u/88905074?v=4" width=115><br><sub>Felipe Vahia</sub>](https://github.com/felipemalli) | [<img src="https://avatars.githubusercontent.com/u/68663436?v=4" width=115><br><sub>Natã Elienai</sub>](https://github.com/nataelienai) |  [<img src="https://avatars.githubusercontent.com/u/66336767?v=4" width=115><br><sub>Raphael</sub>](https://github.com/Raph2ll) |
| :---: | :---: | :---: |