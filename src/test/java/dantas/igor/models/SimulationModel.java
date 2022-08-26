package dantas.igor.models;


import lombok.Data;

@Data
public class SimulationModel {
    private Long id;
    private String nome = "Nome1";
    private String cpf = "34815466025";
    private String email = "email@email.com";
    private Number valor = 1000;
    private Integer parcelas = 3;
    private Boolean seguro = true;
}
