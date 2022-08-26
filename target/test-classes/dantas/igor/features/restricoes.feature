#language: pt

@API
@Restriction
Funcionalidade:
  O endpoint de Restrições tem a finalidade de consultar o CPF informado, retornando se ele possui ou não uma restrição.

  Esquema do Cenario:  Consulta de cpfs com restrição
    Quando consulto <cpf> com restricao
    Entao deve retornar mensagem indicando que <cpf> possui restrição
    E statusCode deve ser 200
    Exemplos:
      | cpf         |
      | 97093236014 |
      | 60094146012 |
      | 84809766080 |
      | 62648716050 |
      | 26276298085 |
      | 01317496094 |
      | 55856777050 |
      | 19626829001 |
      | 24094592008 |
      | 58063164083 |

  Esquema do Cenario: Consulta de cpfs sem restrição
    Quando consulto <cpf> sem restrição
    Entao statusCode deve ser 204
    Exemplos:
      | cpf         |
      | 02676129074 |
      | 68167414043 |
