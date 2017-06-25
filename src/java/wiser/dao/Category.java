/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wiser.dao;

/**
 * This class represents a category assigned to a data service.
 *
 * @author Mattia Chiari
 * @version 1.0
 */
public class Category {
     /**
     * The IDentifier of the category.
     */
    private long id;
    /**
     * The name of the category (i.e., the term). 
     */
    private String nome;
    
    public Category() {

    }

    public long getId() {
        return id;
    }
    
     public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
