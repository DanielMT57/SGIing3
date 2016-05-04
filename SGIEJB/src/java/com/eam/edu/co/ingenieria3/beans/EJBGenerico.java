/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eam.edu.co.ingenieria3.beans;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
/**
 *
 * @author german
 */

public class EJBGenerico <T> implements Serializable {
    
    
    @PersistenceContext(name = "PFinalClienteJPAPU")
    private EntityManager em;

	protected  DAOGenerico dao;

	@PostConstruct
	public void postConstruct() {            
		dao = new DAOGenerico(em);
	}
        
        	/**
	 * Crea la entidad.
	 * 
	 * @param entidad
	 */
	public void crear(T entidad) {
		dao.persistir(entidad);
	}

	/**
	 * Metodo para buscar.
	 * 
	 * @param pk
	 *            , llave primaria de la entidad.
	 * @return, el objeto o null si no existe.
	 */
	public T buscar(Object pk) {
		return dao.encontrarPorId(getType(), pk);
	}

	/**
	 * Metodo para eliminar una entidad.
	 * 
	 * @param entidad
	 */
	public void eliminar(T entidad) {
		T obj = buscar(getId(entidad));
		// em.refresh(entidad);
		if (obj != null) {
			dao.eliminar(obj);
		}
	}

	/**
	 * Metodo para Editar una entidad.
	 * 
	 * @param entidad
	 */
	public void editar(T entidad) {
		dao.actualizar(entidad);
	}

	/**
	 * MEtodo para listar todos los objetos de la entidad manejada por el EJB.
	 * 
	 * @return
	 */
	public List<T> listarTodos() {
                System.out.print("entro");
		return dao.listarTodos(getType());
	}

	/**
	 * Utilitario para saber el tipo de EJB que es esta instancia.
	 * 
	 * @return el class de este EJB:
	 * @throws Exception
	 */
	private Class<T> getType() {
		ParameterizedType superclass = (ParameterizedType) getClass()
				.getGenericSuperclass();

		return (Class<T>) superclass.getActualTypeArguments()[0];
	}

	/**
	 * Metodo para determinar el Id de una entidad
	 * 
	 * @param obj
	 * @return
	 */
	private Object getId(T obj) {
		Class clase = obj.getClass();
		Field id = null;
		inicio: while (id == null && !clase.equals(Object.class)) {
			for (Field campo : clase.getDeclaredFields()) {
				if (campo.isAnnotationPresent(Id.class)
						|| campo.isAnnotationPresent(EmbeddedId.class)) {
					id = campo;
					break inicio;
				}
			}
			clase = clase.getSuperclass();
		}
		try {
			if (id != null) {
				String nombremetodo = "get"
						+ id.getName().toUpperCase().charAt(0)
						+ id.getName().substring(1);
				Method m = obj.getClass().getMethod(nombremetodo);

				return m.invoke(obj);
			}
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getCanonicalName()).info(
					e.getMessage());
		}
		return null;
	}

}
