package io.github.devMboo.rest;

import io.github.devMboo.model.entity.Cliente;
import io.github.devMboo.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClientController {

    private final ClienteRepository repository;

    @Autowired
    public ClientController(ClienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar( @RequestBody @Valid Cliente cliente){
        return repository.save(cliente);
    }


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
