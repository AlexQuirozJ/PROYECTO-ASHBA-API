package com.init.products.rest;

import java.awt.print.Pageable;


import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.init.products.dao.ProductsDAO;
import com.init.products.entitys.Product;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;


@CrossOrigin(origins = "*")
/*A) 1.-Le indicamos a JAVA que sera un servicio REST*/
@RestController
/*2.- Le indicamos en que direccion se expondra el servicio de esta clase*/
//es decir, todos los metodos que tenemos abajo, estaran disponibles en localhost:8080/products
@RequestMapping("products") 
public class producsREST {
	
	/*E) 1- */
	/*E) 2- Realizar el proceso de inyeccion de dependencias con @Autowired, osea que inyecte un objeto real porque la interface no se puede instanciar*/
	@Autowired
	private ProductsDAO productDAO;
	
	//------METODO PARA TRAER TODO LO DE LA BASE DE DATOS------
	//COMPROBADO
	
	/*4.- Response Entity te permite regresar objetos, ya viene en la libreria de Springboot
	 * le decimos que va regresar un objeto de Product que es el archivo de los GET Y SET*/
	/*7- le indicamos que sera un getMapping y respondera en /products- SE SALTO AL 7 PORQUE EL PASO 5 Y 6 ERAN HARCORDEADOS*/
	@GetMapping
	public ResponseEntity<List<Product>> getProduct(){  /*8- Agregamos list para que indiquemos que nos muestre los productos en lista*/
		/*E) 3- Le decimos que genere el primer metodo para regresar todos los productos*/
		/*le estamos diciendo que consulte la DB y convierta los objetos o entidades de Products y los arroje en forma de lista*/
		List<Product> products=productDAO.findAll();
		//le decimos que regrese la entidad de productos
		return ResponseEntity.ok(products);
		//return ResponseEntity.ok(product);
		
	}
	
	//-----METODO PARA TRAER POR EL ID DEL PRODUCTO----------
	//COMPROBADO
	
	@RequestMapping(value="{productId}") //Cambiamos al RequestMapping porque así podemos utilizar variables de la URL
	// es decir, que el localhost:8080/products/ se va concatenar con id para quedar  localhost:8080/products/1 <- es el numero de id del producto
	//lo que decimos abajo es que "productId" almacene lo que se consulta en ProductiId
	public ResponseEntity<Product> getProductById(@PathVariable("productId")Long productId){ //E) 4- METODO PARA TRAER POR ID del producto, debes cambiar el nombre y decirle que el ID es Long y se llamara ProductId
		//Optional nos protege de un valor nulo
		Optional<Product> optionalProduct= productDAO.findById(productId);
		//sí optional product esta presente entonces
		if(optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		
		// sí esta presente entonces haz lo siguiente
		}else {
			return ResponseEntity.noContent().build();
		}	
	}
	
	//-------------METODO PARA INSERTAR DATOS A LA DB Y DESPUES CONSULTAR POR FINDALL--------
	// COMPROBADO
	
	//Le indicamos que va consultar de la URL localhost:8080/products pero por el metodo POST 
	@PostMapping
	//@RequestBody n
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		Product newProduct = productDAO.save(product);
		return ResponseEntity.ok(newProduct);
		
	}
	
	//-------------METODO PARA ELIMINAR DATOS--------
	//COMPROBADO 
	
		//Le indicamos que va consultar de la URL localhost:8080/products pero por el metodo DELETE
		@DeleteMapping(value="{productId}") //le decimos que vamos a eliminar por id
		//le indicamos que en el void eliminaremos el producto identificado por la varibale ID
		public ResponseEntity<Void> deleteProduct(@PathVariable("productId")Long productId){ // Pones el Path porque le vas a decir que id vas a eliminar
			productDAO.deleteById(productId);
			return ResponseEntity.ok(null);
			
		}
		
		//----------METODO PARA ACTUALIZAR DATOS  ------------------------------------
		//COMPROBADO
		
		@PutMapping
		public ResponseEntity<Product> updateProduct(@RequestBody Product product){
				Optional<Product> optionalProduct= productDAO.findById(product.getId());
				//sí optional product esta presente entonces
				if(optionalProduct.isPresent()) {
					
					// si si tiene respuesta, asignamos el valor a una variable  y seteale el nombre
					Product updateProduct = optionalProduct.get();
					updateProduct.setName(product.getName());
					//el save solo aplica en los put y update 
					productDAO.save(updateProduct);
					return ResponseEntity.ok(updateProduct);
				
				// sí no esta presente entonces haz lo siguiente
				}else {
					return ResponseEntity.notFound().build();
				}	
			}
		
		
		//---------METODO PARA QUE BUSQUE EN LA BARRA Y AUTOCOMPLETE Y ORDENE POR MENOR PRECIO-----------
		//localhost:8080/products/query/?name=kaiser
		@GetMapping("/query/")
		public ArrayList<Product> getProductByNameIgnoreCase(@RequestParam (value="name", defaultValue="")String name, Sort sort){
			return productDAO.findByNameLikeIgnoreCase(name, sort);
		}
						
				
		//--------------------METODO PARA CONSULTAR POR CATEGORIAS COMPUTADORAS - ORDER BY Y BETWEEN------------------------
		//localhost:8080/products/computadoras1?Categoria=COMPUTADORAS&precio=4000&precio2=10000
		@GetMapping("/computadoras1")
		public ArrayList<Product>getProductByCategoriaBetween(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
			return productDAO.findByOrderByCategoriaBetweenDesc(categoria, precio, precio2, sort);
		}	
		
		//localhost:8080/products/computadoras2?Categoria=COMPUTADORAS&precio=10001&precio2=20000
		@GetMapping("/computadoras2")
		public ArrayList<Product>getProductByCategoria1Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
			return productDAO.findByOrderByCategoria1BetweenDesc(categoria, precio, precio2, sort);
		}	
		
		//localhost:8080/products/computadoras3?Categoria=COMPUTADORAS&precio=20001&precio2=50000
		@GetMapping("/computadoras3")
		public ArrayList<Product>getProductByCategoria12Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByCategoria12BetweenDesc(categoria, precio, precio2, sort);
		}	
		
		
		//--------------------METODO PARA CONSULTAR POR CATEGORIAS CELULARES - ORDER BY Y BETWEEN------------------------
		//localhost:8080/products/celulares1?Categoria=CELULARES&precio=2000&precio2=6000
		@GetMapping("/celulares1")
		public ArrayList<Product>getProductByCategoriaC1Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByCategoriaC1BetweenDesc(categoria, precio, precio2, sort);
		}	
				
		//localhost:8080/products/celulares2?Categoria=CELULARES&precio=6001&precio2=12000
		@GetMapping("/celulares2")
		public ArrayList<Product>getProductByCategoriaC2Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByCategoriaC2BetweenDesc(categoria, precio, precio2, sort);
		}	
				
		//localhost:8080/products/celulares3?Categoria=CELULARES&precio=12000&precio2=50000
		@GetMapping("/celulares3")
		public ArrayList<Product>getProductByCategoriaC3Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByCategoriaC3BetweenDesc(categoria, precio, precio2, sort);
		}	
		
		
		//--------------------METODO PARA CONSULTAR POR CATEGORIAS TABLETAS - ORDER BY Y BETWEEN------------------------
		//localhost:8080/products/tablets1?Categoria=TABLETAS&precio=20001&precio2=6000
		@GetMapping("/tablets1")
		public ArrayList<Product>getProductByCategoriat1Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByTablets1BetweenDesc(categoria, precio, precio2, sort);
		}	
						
		//localhost:8080/products/tablets2?Categoria=TABLETAS&precio=20001&precio2=6000
		@GetMapping("/tablets2")
		public ArrayList<Product>getProductByCategoriat2Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByTablets2BetweenDesc(categoria, precio, precio2, sort);
		}	
						
		//llocalhost:8080/products/tablets3?Categoria=TABLETAS&precio=20001&precio2=6000
		@GetMapping("/tablets3")
		public ArrayList<Product>getProductByCategoriat3Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByTablets3BetweenDesc(categoria, precio, precio2, sort);
		}	
		
		//--------------------METODO PARA CONSULTAR POR CATEGORIAS PANTALLAS - ORDER BY Y BETWEEN------------------------
		//localhost:8080/products/pantallas1?Categoria=PANTALLAS&precio=20001&precio2=6000
		@GetMapping("/pantallas1")
		public ArrayList<Product>getProductByCategoriaP1Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByPantallas1BetweenDesc(categoria, precio, precio2, sort);
		}	
								
		//localhost:8080/products/pantallas2?Categoria=PANTALLAS&precio=20001&precio2=6000
		@GetMapping("/pantallas2")
		public ArrayList<Product>getProductByCategoriaP2Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByPantallas2BetweenDesc(categoria, precio, precio2, sort);
		}	
								
		//localhost:8080/products/pantallas3?Categoria=PANTALLAS&precio=20001&precio2=6000
		@GetMapping("/pantallas3")
		public ArrayList<Product>getProductByCategoriaP3Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByPantallas3BetweenDesc(categoria, precio, precio2, sort);
		}	
		
		
		//--------------------METODO PARA CONSULTAR POR CATEGORIAS IMPRESORAS - ORDER BY Y BETWEEN------------------------
		//localhost:8080/products/impresoras1?Categoria=IMPRESORAS&precio=20001&precio2=6000
		@GetMapping("/impresoras1")
		public ArrayList<Product>getProductByCategoriaI1Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByImpresoras1BetweenDesc(categoria, precio, precio2, sort);
		}	
										
		//localhost:8080/products/impresoras2?Categoria=IMPRESORAS&precio=20001&precio2=6000
		@GetMapping("/impresoras2")
		public ArrayList<Product>getProductByCategoriaI2Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByImpresoras2BetweenDesc(categoria, precio, precio2, sort);
		}	
										
		//localhost:8080/products/impresoras3?Categoria=IMPRESORAS&precio=20001&precio2=6000
		@GetMapping("/impresoras3")
		public ArrayList<Product>getProductByCategoriaI3Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByImpresoras3BetweenDesc(categoria, precio, precio2, sort);
		}	
		
		//--------------------METODO PARA CONSULTAR POR CATEGORIAS AURICULARES - ORDER BY Y BETWEEN------------------------
		//localhost:8080/products/auriculares1?Categoria=AURICULARES&precio=20001&precio2=6000
		@GetMapping("/auriculares1")
		public ArrayList<Product>getProductByCategoriaIABetween(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByAuriculares1BetweenDesc(categoria, precio, precio2, sort);
		}	
												
		//localhost:8080/products/auriculares2?Categoria=AURICULARES&precio=20001&precio2=6000
		@GetMapping("/auriculares2")
		public ArrayList<Product>getProductByCategoriaA2Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByAuriculares2BetweenDesc(categoria, precio, precio2, sort);
		}	
												
		//localhost:8080/products/auriculares3?Categoria=AURICULARES&precio=20001&precio2=6000
		@GetMapping("/auriculares3")
		public ArrayList<Product>getProductByCategoriaA3Between(@RequestParam (value="Categoria", defaultValue="")String categoria, int precio, int precio2, Sort sort){
		return productDAO.findByOrderByAuriculares3BetweenDesc(categoria, precio, precio2, sort);
		}
		

	}
		
		
		
			
			
							

		
		
	


