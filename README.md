<p align="center">
  <h3 align="center">Meu Rolê</h3>

  <p align="center">
    Trabalho de conclusão do curso de Android
    <br>
    <br>
    FIAP - MBA - MOBILE DEVELOPMENT
    <br>
    APPS, IOT, CHATBOTS & VIRTUAL ASSISTANTS
    <br>
  </p>
</p>


## Indice

- [Integrantes](#integrantes)
- [Professor](#professor)
- [Requisitos](#requisitos)
- [Demonstração](#demonstração)
- [Freepik](#freepik)

## Integrantes
- [Helton Isac](https://github.com/helton-isac)
- [Helton Souza](https://github.com/heltonss)
- [Lyan Masterson](https://github.com/lyanmaster)
- [Ricardo Kerr](https://github.com/RicardoKerr)

## Professor
- [Heider Lopes](https://github.com/heiderlopes)

## Requisitos

### Tela Splash Screen
Encontrada ao abrir o app

### Tela de Login
Enquanto o usuário ainda não estiver logado, logo após a splash ele sera levado para tela de Login

### Tela Principal
Após o login usuario será levado para tela principal, onde podera escolher entre buscar roteiros, criar um roteiro ou fazer uma pesquisa no mapa (Google Maps).

### Tela de Cadastro
Na tela de login o usuário tem a possibilidade de criar um novo usuario para acessar a aplicação, caso ainda nao tenha um.

### Tela de exibição de dados
Roteiros cadastrados serão apresentados na tela de busca por roteiros.
Na tela de criação de roteiros, pontos de interesse criados aparecerão como parte do roteiro.

### Tela de edição de dados
O usuario pode editar um ponto de interesse criado ainda dentro da tela de criação de roteiros ou editar um roteiro quando olhando a lista de roteiros.

### Tela de Sobre
O usuario pode encontrar informações dos desenvolvedores e sobre o aplicativo na tela de sobre. Ela fica na aba de perfil.

### Integração de recursos
É possível realizar ligação pelo telefone cadastrado no ponto de interesse.
É possível tambem realizar compartilhamento do aplicativo na tela de sobre.

### Push Notifications
O aplicativo receberá push notifications para encorajar o usuario de encontrar um novo roteiro.

### Analytics
O aplicativo esta configurado para realizar coletas de dados de uso do aplicativo.

### Crashlytics
Todos os crashes que acontecerem no aplicativo aparecerão no painel do Crashlytics tanto em prod como em dev.

### Firebase App Distribution
Foi adicionado o professor como tester do aplicativo.

### Product Flavors
Flavors foram criados para separar o ambiente de desenvolvimento e produção.

### Criaçao de uma biblioteca
Foi criada uma biblioteca para realizar a biometria

### Remote Config


## Demonstração


## Freepik

<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

Para o mapa, adicionar no arquivo local.properties:
>MAPS_API_KEY=secret-key

https://developers.google.com/maps/documentation/android-sdk/get-api-key