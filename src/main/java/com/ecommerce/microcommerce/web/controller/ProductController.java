package com.ecommerce.microcommerce.web.controller;

import java.util.List;

import javax.validation.Valid;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api( description="API pour es opérations CRUD sur les produits.")
@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;

    //Récupérer la liste des produits
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }


    //Récupérer un produit par son 
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        Product product = productDao.findById(id);
        if(product==null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");

        return product;
    }

    @GetMapping(value = "/produits/prixmin/{prixLimit}")
    public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(prixLimit);
    }

    @GetMapping(value = "/produits/produitCher/{prixLimit}")
    public List<Product> testSqlQuery(@PathVariable int prixLimit) {
        return productDao.chercherUnProduitCher(prixLimit);
    }


    @GetMapping(value = "produits/namelike/{recherche}")
    public List<Product> testeDeRequetes(@PathVariable String recherche) {
        return productDao.findByNomLike("%"+recherche+"%");
    }

    @PostMapping(value = "/Produits")
    public void ajouterProduit(@Valid @RequestBody Product product) {
         productDao.save(product);
    }
    
    @DeleteMapping (value = "/Produits/{id}")
   public void supprimerProduit(@PathVariable int id) {

       productDao.delete(id);
   }
   
   @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product) {

        productDao.save(product);
    }

    // @Autowired
    // private ProductDaoImpl productDao;;
    
    // @RequestMapping(value="/Produits", method=RequestMethod.GET)
    // public List<Product> listeProduits() {
    //     return productDao.findAll();
    // }

    // @RequestMapping(value = "/Produits/{id}", method = RequestMethod.GET)
    // public Product afficherUnProduit(@PathVariable int id) {
    //     return productDao.findById(id);
    // }

    // @PostMapping(value = "/Produits")
    // public void ajouterProduit(@RequestBody Product product) {
    //      productDao.save(product);
    // }
}

