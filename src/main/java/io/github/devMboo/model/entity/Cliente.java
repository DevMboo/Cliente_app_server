package io.github.devMboo.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    /*
    * Criação dos getters e setters
    * com o Spring framework, assim
    * como a validação dos campos para
    * boa utilização dos serviços pelos usuários
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(nullable = false, length = 11)
    @NotNull(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;
    /*
    * Validação na coluna em caso de atualização
    * garantindo que em caso de um update, o campo
    * data não receba o valor igual a null
    * */
    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

    /*
    * Pre verificação se está sendo
    * introduzido a data corretamente,
    * em caso de não introdução do metodo
    * set, ele garante uma data cadastrada
    * no banco de dados.
    **/
    @PrePersist
    public void prePersist(){
        setDataCadastro(LocalDate.now());
    }
}
