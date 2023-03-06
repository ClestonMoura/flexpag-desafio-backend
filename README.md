# Automated Payment Scheduler

API de agendamento de pagamentos automatizados. 
Aplicação para fins de avaliação do desafio Flexpag Back end.

A solução desenvolvida segue o contexto e fluxo esperado estabelecidos na descrição do
desafio. Além disso, a aplicação possui a funcionalidade extra de pagamentos automáticos
e a implementação de _custom exception handling_ para melhores messagens de erro ao usuário.

## Entidades

A API possui apenas uma entidade: _PAYMENT_

| Campo            | Tipo                   |
|------------------|------------------------|
| _ID_             | BIGINT                 |
| _AMOUNT_         | DOUBLE PRECISION       |
| _TITLE_          | CHARACTER VARYING(255) |
| _PAYMENT_DATE_   | TIMESTAMP              |
| _PAYMENT_STATUS_ | CHARACTER VARYING(255) |
| _PAYMENT_TYPE_   | CHARACTER VARYING(255) |

## API

As seguintes rotas estão disponíveis pela API:

### `GET /api/payments`
* Retorna uma lista de todos os _payments_.
* Um paremetro opcional do tipo `paymentStatus` pode ser adicionado para retorna 
lista de pagamentos especificos.

### `POST /api/payments`
* Cria agendamento com `client`, `amount`, `paymentDate` e `paymentType` especificados.
* Retorna o _payment_ criado.
* Exemplo de envio do json:
```JSON
{
  "tilte": "payment1",
  "amount": 2000.65,
  "paymentDate": "2023-03-04T14:27:00",
  "paymentType": "AUTO"
}
```

### `GET /api/payments/{id}`
* Retorna um _payment_ identificado pelo parâmetro de caminho _id_.
* Esta rota deve ser usada para verificar o `paymentStatus`.


### `PUT /api/payments/date/{id}?newDate={newDate}`
* Modifica o `paymentDate` de um _payment_ salvo.
* Retorna o _payment_ modificado. 
Uma _exception_ será retornada caso a nova data seja inválida ou se o 
`paymentStatus` seja `PAID`.

### `PUT /api/payments/type/{id}`
* Modifica o `paymentType` para `AUTO` ou `MANUAL` dependendo do _payment_ salvo.
* Retorna o _payment_ modificado.
  Uma _exception_ será retornada caso o `paymentStatus` seja `PAID`.

### `PUT /api/payments/status/{id}`
* Modifica o `paymentStatus` para `PAID` de um _payment_ salvo.
* Retorna o _payment_ modificado.
  Uma _exception_ será retornada caso o `paymentStatus` seja `PAID`.


### `DELETE /api/payments/{id}`
* Deleta um _payment_ existente identificado pelo parâmetro de caminho _id_.
* Uma _exception_ será retornada caso o `paymentStatus` seja `PAID`.

## Pagamentos automatizados

A API tem a funcionalidade de atualizar automaticamente o `paymentStatus` de um
pagamento para `PAID` caso o `paymentType` seja `AUTO`. Este recurso funciona checando
os pagamentos válidos em um derterminado espaço de tempo, que pode ser modificado
dependendo do escopo da aplicação.

