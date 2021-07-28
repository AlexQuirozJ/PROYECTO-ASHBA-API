package com.init.products.rest;

import java.util.ArrayList;

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
		
		
		//--------------------METODO PARA CONSULTAR POR CATEGORIAS Y POR ORDER BY------------------------
		//localhost:8080/products/query?Categoria=BOCINAS
		@GetMapping("/query")
		public ArrayList<Product>getProductByCategoria(@RequestParam (value="Categoria", defaultValue="")String categoria, Sort sort){
			return productDAO.findByOrderByCategoriaDesc(categoria, sort);
		}
		
		//---------METODO PARA QUE BUSQUE EN LA BARRA Y AUTOCOMPLETE Y ORDENE POR MENOR PRECIO-----------
		//localhost:8080/products/query/?name=kaiser
		@GetMapping("/query/")
		public ArrayList<Product> getProductByNameIgnoreCase(@RequestParam (value="name", defaultValue="")String name, Sort sort){
			return productDAO.findByNameLikeIgnoreCase(name, sort);
		}
		
			
			
							//-----PRUEBAS DE CODIGO--------//
		
		// -----------------METODO PARA QUE BUSQUE POR CATEGORIAS- FUNCIONA PERO SIN ORDER BY -----------
		// localhost:8080/products/query?Categoria=BOCINAS
		//@GetMapping("/query")
		//public ResponseEntity<List<Product>> getByCategoria(@RequestParam(value="Categoria", defaultValue="")String categoria){
		//List<Product> Categoria=productDAO.findByCategoria(categoria);
		//return ResponseEntity.ok(Categoria);
		//}
		
		//-----------------METODO PARA BUSQUEDA POR BARRA DE BUSQUEDA - AUTOCOMPLETADO SIN ORDER BY -----
		//localhost:8080/products/query/?name=a...
		//@GetMapping("/query/")
		//public ArrayList<Product> getProductByNameIgnoreCase(@RequestParam (value="name", defaultValue="")String name){
			//return productDAO.findByNameLikeIgnoreCase(name);
		//}
		
				
		//-----------------METODO PARA BUSQUEDA POR BARRA- ------------------------------
		//localhost:8080/products/query/?name=Bocina Inalámbrica JBL PartyBox 200
		//@GetMapping("/query/")
		//public ArrayList<Product> getProductByName(@RequestParam(value="name", defaultValue="")String name){
			//return productDAO.findByName(name);
		//}
		
		
	}


