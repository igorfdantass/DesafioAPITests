package dantas.igor.models;

import lombok.Data;

@Data
public class ResponseModel {
    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private Number valor;
    private Integer parcelas;
    private Boolean seguro;
}
