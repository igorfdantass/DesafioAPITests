#language: pt

@API
@Simulation
Funcionalidade: Validar gerenciamento de simulações de crédito
  A simulação é um cadastro que ficará registrado informações importantes
  sobre o crédito como valor, parcelas, dados de contato, etc...

  Cenario: Consultar todas as simulações existentes
    Dado que existem simulações
    Quando consultar todas as simulações realizadas
    Entao deve retornar lista de simulações

  Esquema do Cenario: Inserir nova simulação válida
    Dado que tenho os dados:
    * Nome: <nome>
    * Cpf: <cpf>
    * Email: <email>
    * Valor: <valor>
    * Parcelas: <parcelas>
    * Seguro: <seguro>
    Quando cadastro simulação
    Entao retorna status <status>
    E retorna id da simulação

    Exemplos:
      | nome           | cpf         | email                  | valor | parcelas | seguro | status |
      | "SimulacaoOK1" | 02676129074 | SimulacaoOK1@email.com | 1000  | 2        | true   | 201    |
      | "SimulacaoOK2" | 17444393036 | SimulacaoOK2@email.com | 5000  | 36       | true   | 201    |
      | "SimulacaoOK3" | 00812656032 | SimulacaoOK3@email.com | 40000 | 48       | true   | 201    |

  Esquema do Cenario: Inserir simulação inválida
    Dado que tenho os dados:
    E Nome: <nome>
    E Cpf: <cpf>
    E Email: <email>
    E Valor: <valor>
    E Parcelas: <parcelas>
    E Seguro: <seguro>,
    Quando cadastro simulação
    Entao retorna status <status>
    E retorna <mensagem> de erro

    Exemplos:
      | nome               | cpf            | email                     | valor | parcelas | seguro | status | mensagem                                |
      | "InfoFaltante"     |                | InfoFaltante@email.com    | 1200  | 10       | true   | 400    | 'Cpf nao pode ser vazio'                |
      | "CpfDuplicado"     | 02676129074    | CpfDuplicado@email.com    | 1500  | 2        | false  | 409    | CPF duplicado                           |
      | "FormatoInvalido"  | 982.916.950-24 | FormatoInvalido@email.com | 9900  | 10       | true   | 400    | 'Formato invalido'                      |
      | "ValorInferior"    | 17635877038    | ValorInferior@email.com   | 999   | 10       | true   | 400    | Erro                                    |
      | "ValorSuperior"    | 60939658054    | ValorSuperior@email.com   | 40001 | 10       | true   | 400    | Erro                                    |
      | "Parcela Inferior" | 56810993002    | ValorSuperior@email.com   | 1200  | 1        | true   | 400    | Parcelas deve ser igual ou maior que 2  |
      | "Parcela Inferior" | 37045020066    | ValorSuperior@email.com   | 1200  | 49       | true   | 400    | Parcelas deve ser igual ou menor que 48 |

  Cenario: Consultar simulações por cpf
    Dado que tenha simulação cadastrada
    Quando consultar simulaçao por cpf
    Entao retorna status 204

  Cenário: Consultar simulações de cpfs sem simulação
    Dado que cpf '97093236014' não tem simulação
    Quando consultar simulaçao por cpf
    Entao retorna status 404

  Cenario: Editar simulação existente corretamente
    Dado que tenha simulação cadastrada
    Quando alterar todos os dados corretamente
    E editar simulação
    Entao deve retornar simulação com novos dados

  Cenario: Erro ao editar simulação existente para valor abaixo do mínimo permitido
    Dado que tenha simulação cadastrada
    Quando alterar para valor abaixo do minimo permitido
    E editar simulação
    Entao deve retornar mensagem "Valor deve ser igual ou maior que 1000"

  Cenario: Erro ao editar simulação existente para valor acima do máximo permitido
    Dado que tenha simulação cadastrada
    Quando alterar para valor acima do máximo permitido
    E editar simulação
    Entao deve retornar mensagem "Valor deve ser igual ou menor que 40000"

  Cenario: Erro ao editar simulação existente para quantidade de parcelas abaixo do mínimo permitido
    Dado que tenha simulação cadastrada
    Quando alterar parcelas para quantidade abaixo do mínimo permitido
    E editar simulação
    Entao deve retornar mensagem "Parcelas deve ser igual ou maior que 2"

  Cenario: Erro ao editar simulação existente para quantidade de parcelas acima do máximo permitido
    Dado que tenha simulação cadastrada
    Quando alterar parcelas para quantidade acima do máximo permitido
    E editar simulação
    Entao deve retornar mensagem "Parcelas deve ser igual ou menor que 48"

  @Bug
  Cenario: Remover simulação pelo Id
    Dado que tenha simulações previamente cadastradas
    Quando remover simulacao existente
    Entao retorna status 204

  @Bug
  Cenario: Erro ao tentar remover simulação inexistente
    Quando tentar remover simulacao inexistente
    Entao retorna status 404

