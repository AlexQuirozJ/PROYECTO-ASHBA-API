package com.init.products.dao;

import java.awt.print.Pageable;



import java.util.ArrayList;

import org.springframework.boot.SpringApplication;

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
	
	//METODO POR CATEGORIA CON ORDER BY
	@Query ("select c from Product c where c.categoria like %?1% order by precio")
	 public abstract ArrayList<Product> findByOrderByCategoriaDesc(String categoria, Sort sort);
		
	//METODO PARA BUSCAR POR CATEGORIA, BETWEEN Y ORDER BY
	//COMPUTADORAS
	@Query ("select c from Product c where c.categoria like %?1% AND precio BETWEEN 4000 AND 10000 order by precio ")
	 public abstract ArrayList<Product> findByOrderByCategoriaBetweenDesc(String categoria, int precio, int precio2, Sort sort);
		
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 10001 AND 20000 order by precio ")
	public abstract ArrayList<Product> findByOrderByCategoria1BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 20001 AND 50000 order by precio ")
	public abstract ArrayList<Product> findByOrderByCategoria12BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	//CELULARES
	@Query ("select c from Product c where c.categoria like %?1% AND precio BETWEEN 2000 AND 6000 order by precio ")
	 public abstract ArrayList<Product> findByOrderByCategoriaC1BetweenDesc(String categoria, int precio, int precio2, Sort sort);
		
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 6001 AND 12000 order by precio ")
	public abstract ArrayList<Product> findByOrderByCategoriaC2BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 12001 AND 50000 order by precio ")
	public abstract ArrayList<Product> findByOrderByCategoriaC3BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	//TABLETS
	@Query ("select c from Product c where c.categoria like %?1% AND precio BETWEEN 2000 AND 5000 order by precio ")
	public abstract ArrayList<Product> findByOrderByTablets1BetweenDesc(String categoria, int precio, int precio2, Sort sort);
			
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 5001 AND 9000 order by precio ")
	public abstract ArrayList<Product> findByOrderByTablets2BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 9001 AND 50000 order by precio ")
	public abstract ArrayList<Product> findByOrderByTablets3BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	//PANTALLAS
	@Query ("select c from Product c where c.categoria like %?1% AND precio BETWEEN 3000 AND 6000 order by precio ")
	public abstract ArrayList<Product> findByOrderByPantallas1BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 6001 AND 10000 order by precio ")
	public abstract ArrayList<Product> findByOrderByPantallas2BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 10001 AND 50000 order by precio ")
	public abstract ArrayList<Product> findByOrderByPantallas3BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	//IMPRESORAS
	@Query ("select c from Product c where c.categoria like %?1% AND precio BETWEEN 1000 AND 2000 order by precio ")
	public abstract ArrayList<Product> findByOrderByImpresoras1BetweenDesc(String categoria, int precio, int precio2, Sort sort);
		
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 2001 AND 4000 order by precio ")
	public abstract ArrayList<Product> findByOrderByImpresoras2BetweenDesc(String categoria, int precio, int precio2, Sort sort);
		
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 4001 AND 50000 order by precio ")
	public abstract ArrayList<Product> findByOrderByImpresoras3BetweenDesc(String categoria, int precio, int precio2, Sort sort);
	
	
	//AURICULARES
	@Query ("select c from Product c where c.categoria like %?1% AND precio BETWEEN 100 AND 300 order by precio ")
	public abstract ArrayList<Product> findByOrderByAuriculares1BetweenDesc(String categoria, int precio, int precio2, Sort sort);
			
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 301 AND 800 order by precio ")
	public abstract ArrayList<Product> findByOrderByAuriculares2BetweenDesc(String categoria, int precio, int precio2, Sort sort);
			
	@Query("select c from Product c where c.categoria like %?1% AND precio BETWEEN 801 AND 5000 order by precio ")
	public abstract ArrayList<Product> findByOrderByAuriculares3BetweenDesc(String categoria, int precio, int precio2, Sort sort);
			
	
	
	}

	

	

	


