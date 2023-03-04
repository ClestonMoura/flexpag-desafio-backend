# Automated Payment Scheduler

API de agendamento de pagamentos automatizados. 
Aplicação para fins de avaliação do desafio Flexpag Back end.

A solução desenvolvida segue o contexto e fluxo esperado estabelecidos no desafio.
Além disto, a aplicação realiza o pagamento de agendamentos de forma automática checando
a `data:hora` de cada pagamento salvo.
Os detalhes mais complexos da solução se resumem a criação de `excepiton handling`
customizados.

## Entidades

A API possui apenas uma entidade: _PAYMENT_

| Campo        | Tipo                   |
|--------------|------------------------|
| _ID_         | BIGINT                 |
| _AMOUNT_     | DOUBLE PRECISION       |
| _CLIENT_     | CHARACTER VARYING(255) |
| _PAY_DATE_   | TIMESTAMP              |
| _PAY_STATUS_ | CHARACTER VARYING(255) |

## API

As seguintes rotas estão disponíveis pela API:

### `GET /api/payments`
* Retorna uma lista de todos os _payments_.

### `GET /api/payments/{id}`
* Retorna um _payment_ identificado pelo parâmetro de caminho _id_.
* Esta rota deve ser usada para verificar o `paymentStatus`.

### `POST /api/payments`
* Cria um novo agendamento com `client`, `amount` e `payDate` especificados.
* Retorna o _payment_ criado.
* Exemplo de envio do json:
```JSON
{
  "client": "client1",
  "amount": 2000.65,
  "payDate": "2023-03-04T14:27:00"
}
```

### `PUT /api/payments/{id}?newDate={newDate}`
* Modifica o `paydate` de um _payment_ salvo.
* Retorna o _payment_ modificado. 
Uma _exception_ será retornada caso a nova data seja inválida ou se o 
`paymentStatus` seja `PAID`.

### `DELETE /api/payments/{id}`
* Deleta um _payment_ existente identificado pelo parâmetro de caminho _id_.
* Uma _exception_ será retornada caso o `paymentStatus` seja `PAID`.

## Pagamentos automatizados

A API mudará os status de pagamento para `paid` automaticamente quando os prazos
de cada pagamento vencer. Em virtude disto, não há a implementação de uma rota
para modificar o status manualmente.

A automatização foi implementada usando as ferramentas padrões de `scheduling` do
Spring Boot, e a lógica da implementação se encontra na camada de serviço. O 
desenpenho da implementação leva em consideração apenas os pagamentos que já
venceram e os de status `PENDNG`.

