# Teste Luiza Labs

### Descrição do projeto

O objetivo deste projeto realizar a leitura de 2 arquivos onde contem os pedidos e os mesmos serão armazenados em um Banco H2 e posteriormente serão exibidos via api conforme demanda.

### Pré-requisitos
Antes de começar, certifique-se de que você tenha as seguintes dependências instaladas em seu ambiente de desenvolvimento:


| Dependência | Versão  | Link                                                                                                                    | 
|-------------|---------|-------------------------------------------------------------------------------------------------------------------------|
| Java 17     | 17      | [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)                                 |
| Maven       | 3.8.8 + | [Maven](https://maven.apache.org/download.cgi])                                                                         |

## Configuração do Projeto

1. Clone o repositório para o seu ambiente de desenvolvimento:

   ```bash
   git clone [https://gitlab.dbserver.com.br/matheus.muller/prova-sicredi.git](https://github.com/lucianobuttarello/integracao.git)
   ```

2. Navegue até o diretório raiz do projeto:

   ```bash
   cd integracao
   ```

3. Compile o projeto e baixe as dependências usando o Maven:

   ```bash
   mvn install
   ```

4. Executando o Projeto Pelo Postman, apesar de ter configurado o Swagger a api de Upload não funciona corretamente
   Link Swagger = http://localhost:8080/swagger-ui/index.html#/

  Curl para fazer o upload do arquivo:
  curl --location 'http://localhost:8080/api/upload' \
--header 'accept: */*' \
--form 'file=@"postman-cloud:///1ef234e6-e42a-4f60-b68a-e1c62aaab673"'

![image](https://github.com/lucianobuttarello/integracao/assets/52016220/58af603a-3a01-49bf-a6ab-02668532f4b4)


   Curl para a consulta de Registros
   curl --location 'http://localhost:8080/api' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--data '{
  "idPedido": 753,
  "dataInicio": "2020-10-28",
  "dataFim": "2023-10-28",
  "pagina": 0,
  "tamanho": 100
}'
![image](https://github.com/lucianobuttarello/integracao/assets/52016220/c45aac13-5652-485c-9333-51ffec2f881b)

exemplod do body da consulta:
{
  "idPedido": 753,
  "dataInicio": "2020-10-28",
  "dataFim": "2023-10-28",
  "pagina": 0,
  "tamanho": 100
}
para pesquisar todos os pedidos basta passar null, da mesma maneira para data, tamanho minimo da pagina é 1



   
