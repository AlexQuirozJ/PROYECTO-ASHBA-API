package com.init.products.dao;

import java.util.ArrayList;


/*D) */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.init.products.entitys.Product;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;





public interface ProductsDAO extends JpaRepository<Product, Long>{
	
	//METODO POR CATEGORIAS
	//public abstract ArrayList<Product> findByCategoria(String categoria);
	
	//METODO DE BARRA DE BUSQUEDA - ORDER BY DESC
	@Query("select n from Product n where n.name like %?1% order by precio")
	public abstract ArrayList<Product> findByNameLikeIgnoreCase(String name, Sort sort);
	
	
	@Query ("select c from Product c where c.categoria like %?1% order by precio")
	 public abstract ArrayList<Product> findByOrderByCategoriaDesc(String categoria, Sort sort);
		
	//METODO DE BARRA DE BUSQUEDA - AUTOCOMPLETADO - SIN ORDER BY	
	//@Query("select n from Product n where n.name like %?1%")
	//public abstract ArrayList<Product> findByNameLikeIgnoreCase(String name);
	
//PRUEBA PARA GIT
}
