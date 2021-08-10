package com.init.products.rest;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.init.products.dao.ListasDAO;

import com.init.products.entitys.Listas;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("listas") 
public class listasREST {
	

			@Autowired
			private ListasDAO listasDAO;
			
			//------METODO PARA TRAER TODO LO DE LA BASE DE DATOS------
			//COMPROBADO
			@GetMapping
			public ResponseEntity<List<Listas>> getListas(){ 
				List<Listas> listas=listasDAO.findAll();
				return ResponseEntity.ok(listas);
				
				
			}
			
			//-----METODO PARA TRAER POR EL ID DEL PRODUCTO----------
			//COMPROBADO
			
			@RequestMapping(value="{listaId}") 
			public ResponseEntity<Listas> getListasById(@PathVariable("listaId")Long listaId){
				Optional<Listas> optionalLista= listasDAO.findById(listaId);
				if(optionalLista.isPresent()) {
					return ResponseEntity.ok(optionalLista.get());
				}else {
					return ResponseEntity.noContent().build();
				}	
			}
			
			//-------------METODO PARA INSERTAR DATOS A LA DB Y DESPUES CONSULTAR POR FINDALL--------
			// COMPROBADO
			
			//Le indicamos que va consultar de la URL localhost:8080/usuarios pero por el metodo POST 
			@PostMapping
			public ResponseEntity<Listas> createListas(@RequestBody Listas lista){
				Listas newLista = listasDAO.save(lista);
				return ResponseEntity.ok(newLista);
				
			}
			
			
			//-------------METODO PARA ELIMINAR DATOS--------
			//COMPROBADO 
				//Le indicamos que va consultar de la URL localhost:8080/usuarios pero por el metodo DELETE
				@DeleteMapping(value="{listasId}") 
				public ResponseEntity<Void> deleteLista(@PathVariable("listasId")Long listasId){
					listasDAO.deleteById(listasId);
					return ResponseEntity.ok(null);
					
				}
				
				
				//----------METODO PARA ACTUALIZAR DATOS  ------------------------------------
				//COMPROBADO
				@PutMapping
				public ResponseEntity<Listas> updateUsuarios(@RequestBody Listas lista){
						Optional<Listas> optionalListas= listasDAO.findById(lista.getId());
						if(optionalListas.isPresent()) {
							Listas updateListas = optionalListas.get();
							updateListas.setName(lista.getName());
							//Solo esta actualizando el nombre, debes anexar todas las propiedades
							listasDAO.save(updateListas);
							return ResponseEntity.ok(updateListas);
						
						}else {
							return ResponseEntity.notFound().build();
						}	
					}

	}


