package com.init.products.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*le indicamos a java que sera una configuración*/
@Configuration
/*Con esto, habilitamos el modulo de swagger*/
@EnableSwagger2
public class SwaggerConfig {
	
	/*1.- Metodo Docket es para que JAVA identifique que sera una configuración swagger, para documentar el codigo*/
	/*para que Docket funcione, debes importar la libreria de SWAGGER*/
	
	@Bean /*Esto le dice a JAVA que sera un BEAN que se debe configurar*/
	public Docket api() {
		//Lo que hacemos es decirle que el metodo api va regresar una documentación con SWAGGER 2
		return new Docket(DocumentationType.SWAGGER_2)
		//inicializa un builder (patron de diseño)
		.select()
		//con apis, lo que decimos es, cuales son las clases de tu proyecto que vas a documentar
		// porque ponemos esta dirección? porqué los servicios estan en rest, es decir, lo que podemos hacer esta ahí
		.apis(RequestHandlerSelectors.basePackage("com.init.products.rest"))
		//los paths son todos los que utilizas para tus metodos, como: @DeleteMapping, @RequestMapping, etc
		// se pone any, porque any indica que seran todos
		.paths(PathSelectors.any())
		.build();
		
		
		
	}

}
