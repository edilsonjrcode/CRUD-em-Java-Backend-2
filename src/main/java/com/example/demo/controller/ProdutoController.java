package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Produto;
import com.example.demo.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Produto produto){
        Produto prod = produtoService.insert(produto);
        return prod != null? 
        new ResponseEntity<>("Produto criado com sucesso", HttpStatus.CREATED) 
        :
        new ResponseEntity<>("Erro ao criar produto", HttpStatus.BAD_REQUEST);   
    }

    @GetMapping
    public ResponseEntity<List<Produto>> findAll(){
        List<Produto> prods = produtoService.findAll();
        return new ResponseEntity<>(prods, HttpStatus.OK);
    }

    @GetMapping("/buscar-produto/{id}")
    public ResponseEntity<Optional<Produto>> findById(@PathVariable Integer id){
        Optional<Produto> prod = produtoService.findById(id);
        return prod != null ?
        new ResponseEntity<>(prod, HttpStatus.OK)
        :
        new ResponseEntity<>(prod, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/apagar-produto/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        produtoService.delete(id);
        Optional<Produto> prod = produtoService.findById(id);
        return !prod.isPresent() ?
        new ResponseEntity<>("Produto deletado!", HttpStatus.OK)
        :
        new ResponseEntity<>("Houve um problema ao deletar o produto!", HttpStatus.BAD_REQUEST);

    }

}
