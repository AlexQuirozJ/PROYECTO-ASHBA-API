package com.init.products.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="listas")
public class Listas {		
	
	@Id
	@Column(name="id")
	// le deimos que va ser auto- incrementable
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private long Id;
	
	@Column(name="list_name", nullable=false, length=255)
	private String list_name;
	
	@Column(name="usuario_id", nullable=false, length=255)
	private String usuario_id;
	
	@Column(name="name", nullable=false, length=255)
	private String name;
			
	@Column(name="marca", nullable=false, length=255)
	private String marca;
			
	@Column(name="precio", nullable=false, length=255)
	private int precio;
	
	@Column(name="tienda", nullable=false, length=255)
	private String tienda;
	
	@Column(name="link_pagina", nullable=false, length=255)
	private String link_pagina;
	
	@Column(name="imagen", nullable=false, length=255)
	private String imagen;
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getList_name() {
		return list_name;
	}

	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public String getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(String usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public String getTienda() {
		return tienda;
	}

	public void setTienda(String tienda) {
		this.tienda = tienda;
	}

	public String getLink_pagina() {
		return link_pagina;
	}

	public void setLink_pagina(String link_pagina) {
		this.link_pagina = link_pagina;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}	
