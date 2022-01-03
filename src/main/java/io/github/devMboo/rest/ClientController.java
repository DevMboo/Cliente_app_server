package io.github.devMboo.rest;

import io.github.devMboo.model.entity.Cliente;
import io.github.devMboo.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
/*
* Annotation @CrossOrigin permite requisições
* exclusivas da aplicação angular no momento
* em que ela envia suas requisições,
* ele entende que se aquela deteminada url
* executou uma ação que ele tem em seus
* parametros ele pode excutar suas funções
* sem nenhum tipo de resposta.
* */
@CrossOrigin("http://localhost:4200")
public class ClientController {
    private final ClienteRepository repository;

    @Autowired
    public ClientController(ClienteRepository repository) {
        this.repository = repository;
    }

    /*
    * Retorna todos os items
    * do banco de dados já em formato
    * lista.
    * */
    @GetMapping
    public List<Cliente> obterTodos(){
        return repository.findAll();
    }

    /*
     * Função salvar cliente
     * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar( @RequestBody @Valid Cliente cliente){
        return repository.save(cliente);
    }

    /*
    * Função listar clientes
    * */
    @GetMapping("{id}")
    public Cliente acharPorId( @PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro usuário não encontrado!"));
    }
    /*
    * Metodo delete, que faz
    * a função delete direto no
    * banco de dados.
    * */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id){
         repository
                .findById(id)
                 .map( cliente -> {
                     repository.delete(cliente);
                     return Void.TYPE;
                 })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro usuário não encontrado!"));
    }
    /*
    * Metodo de atualização
    * utilizando o formato put
    * do spring framework, que serve
    * para atualizar completamente
    * uma informação.
    * */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id, @RequestBody Cliente clienteAtualizado){
        repository
                .findById(id)
                .map( cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    clienteAtualizado.setId((cliente.getId()));
                    return repository.save(clienteAtualizado);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro usuário não encontrado!"));
    }

}
