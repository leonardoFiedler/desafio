Tiny-URL

Objetivo do desafio
Implementação de uma API Rest capaz de encurtar uma URL de modo que a mesma possa ser expirada depois de algum tempo.
Para esta aplicação, o tempo definido para a expiração da URL é definido em minutos e possui o valor 5 como padrão.

Setup do Projeto e Tecnologias Utilizadas

- Java + Spring + JPA
- MySQL

Instalação e execução do MySQL localmente
A instalação do MySQL localmente é feita a partir do Docker-Compose. O arquivo de compose (docker-compose.yml) está localizado 
na pasta resources deste projeto. Abaixo são dadas as instruções para executá-lo. 

1) Para iniciar com o docker-compose:
docker-compose up

2) Para finalizar com o docker-compose:
docker-compose down

Configurações adicionais


URL de inclusão de Url's encurtadas
Rota: GET /tiny-url/shorten
Parâmetro: link = String (São removidos quais caracteres diferentes de letras e números, 
também são removidos prefixos como: http/https).

Conclusões e limitações
É possível efetuar o redirecionamento de URL(s) dos mais variados tipos, no entanto, por conta de limitações
do HttpServletResponse, na classe UrlFilter foi necessário definir uma constante de schema padrão, adotando o HTTPS
como o protocolo padrão. Logo, sites que aceitam apenas o protocolo HTTP em seu redirecionamento, poderão enfrentar 
problemas de redirecionamento.