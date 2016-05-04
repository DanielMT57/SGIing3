/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eam.edu.co.ingenieria3.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author german
 */
@SuppressWarnings({ "unchecked" })
public class DAOGenerico implements Serializable{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
     /**
         * Constante de serialización.
         */
        private static final long serialVersionUID = -3106939302290740868L;

        /**
         * Entitymanager.
         */

        private EntityManager entityManager;

        /**
         * Constructor de la clase.
         * 
         * @param entityManager
         *            , manejador de entidades.
         */
        public DAOGenerico(EntityManager entityManager) {
                super();
                this.entityManager = entityManager;
        }

        /**
         * Constructor de la clase sin parametros.
         * 
         * @author <br>
         *         Nelson Mejía Laverde <br>
         *         Email: nmejia@swatit.com<br>
         * @version 1.0
         */
        public DAOGenerico() {

        }

        /**
         * 
         * Método encargado de persistir una instancia de la entidad <T> en el
         * sistema.
         * 
         * @param instancia
         *            a persistir
         */
        public void persistir(Object instancia) {

                // Se almacena la entidad.
                entityManager.persist(instancia);

        }

        /**
         * Método encargado de encontrar un registro mediante su id de
         * 
         * @param id
         * @return instancia del registro con el identificador indicado
         */
        public <T> T encontrarPorId(Class<T> clase, Object pk) {
                return entityManager.find(clase, pk);
        }

        /**
         * 
         * Método encargado de obtener la referencia para la entidad.
         * 
         * @param id
         *            del registro para ser referenciado en bd
         * @return referencia al objeto por medio de la instancia de la entidad
         */
        public <T> T obtenerReferencia(Class<T> clase, Object id) {
                return entityManager.getReference(clase, id);
        }

        /**
         * Método encargado de listar todos los registros de la entidad <T>.
         * 
         * @return listado con todos los registros
         */
        public <T> List<T> listarTodos(Class<T> clase) {
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clase);
                criteriaQuery.from(clase);
                TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
                return typedQuery.getResultList();
        }

        /**
         * 
         * Método encargado de realizar la consulta perezosa a la base de datos.
         * 
         * @param first
         *            offset o primer resultado de la consulta
         * @param maxResults
         *            cantidad de resultados que debe arrojar la consulta
         * @param sortField
         *            campo por el que se va a ordernar la consulta
         * @param ascending
         *            <code>true</code> en caso de ser ascendente,
         *            <code>false</code> si es descendente, y <code>null</code> para
         *            no ordernar
         * @param filters
         *            mapa con los filtros de la consulta, estos se hacen mediante
         *            un like
         * @return resultado de la consulta
         */
        public <T> List<T> cargar(Class<T> clase, Integer first,
                        Integer maxResults, String sortField, Boolean ascending,
                        Map<String, Object> filters) {
                /* se crea el criteria query */
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clase);

                /*
                 * se obtiene el root donde se parte para hacer las demas condiciones de
                 * la consulta
                 */
                Root<T> root = criteriaQuery.from(clase);
                criteriaQuery.select(root);
                /* se crean los predicados para los filtros */
                if (filters != null && !filters.keySet().isEmpty()) {
                        Predicate[] predicates = construirPredicados(filters,
                                        criteriaBuilder, root);

                        criteriaQuery.where(predicates);
                }

                /* si hay que ordernar se ordena */
                if (sortField != null && !sortField.isEmpty() && ascending != null) {
                        Order order;
                        Path<Object> path = root.get(sortField);
                        if (ascending) {
                                order = criteriaBuilder.asc(path);
                        } else {
                                order = criteriaBuilder.desc(path);
                        }

                        criteriaQuery.orderBy(order);
                }
                /*
                 * se crean las restricciones sobre la cantidad de resultados a retornar
                 * y desde donde
                 */
                TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
                typedQuery.setFirstResult(first);
                typedQuery.setMaxResults(maxResults);
                return typedQuery.getResultList();
        }

        /**
         * 
         * Método encargado de realizar la consulta perezosa a la base de datos.
         * 
         * @param first
         *            offset o primer resultado de la consulta
         * @param maxResults
         *            cantidad de resultados que debe arrojar la consulta
         * @param sortField
         *            campo por el que se va a ordernar la consulta
         * @param ascending
         *            <code>true</code> en caso de ser ascendente,
         *            <code>false</code> si es descendente, y <code>null</code> para
         *            no ordernar
         * @param filters
         *            mapa con los filtros de la consulta, estos se hacen mediante
         *            un like
         * @return resultado de la consulta
         */
        public <T> List<T> cargar(Class<T> clase, Map<String, Object> filters) {
                /* se crea el criteria query */
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clase);
                /*
                 * se obtiene el root donde se parte para hacer las demas condiciones de
                 * la consulta
                 */
                Root<T> root = criteriaQuery.from(clase);
                criteriaQuery.select(root);
                /* se crean los predicados para los filtros */
                if (filters != null && !filters.keySet().isEmpty()) {
                        Predicate[] predicates = construirPredicados(filters,
                                        criteriaBuilder, root);

                        criteriaQuery.where(predicates);
                }

                /*
                 * se crean las restricciones sobre la cantidad de resultados a retornar
                 * y desde donde
                 */
                TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
                return typedQuery.getResultList();
        }

        /**
         * 
         * Método que cuenta la cantidad de registros del sistema de la entidad <T>.
         * 
         * @param filters
         *            opcional, para realizar restricciones en la consulta
         * @return cantidad de registros encontrados que cumplan las restricciones
         *         si las hay
         */
        public <T> Long contarRegistros(Class<T> clase, Map<String, Object> filters) {
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> criteriaQuery = criteriaBuilder
                                .createQuery(Long.class);
                Root<T> root = criteriaQuery.from(clase);

                /* se crean los predicados para los filtros */
                if (filters != null && !filters.keySet().isEmpty()) {
                        /* se crea la lista vacia */
                        Predicate[] predicates = construirPredicados(filters,
                                        criteriaBuilder, root);
                        criteriaQuery.where(predicates);
                }

                /* se agrupa mediante la operación count */
                CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder
                                .count(root));

                TypedQuery<Long> typedQuery = entityManager.createQuery(select);
                return typedQuery.getSingleResult();
        }

        /**
         * Método encargado de eliminar un registro en la base de datos con el id
         * especificado. la eliminacion es logica, ya que solo se desactiva la
         * entidad.
         * 
         * @param id
         *            del registro a ser destruido
         */
        public <T extends Serializable> T eliminar(Class<T> clase, Object pk) {
                Serializable instance = encontrarPorId(clase, pk);

                eliminar(instance);

                return (T)instance;
        }

        /**
         * Metodo para eliminar fisicamente una entidad.
         * 
         * 
         * @author <br>
         *         Camilo Andrés Ferrer Bustos <br>
         *         Email: @.com<br>
         * @date 28/04/2013 22:42:49
         * @version 1.0
         * 
         * @param entidad
         */
        public void eliminar(Object entidad) {
                entityManager.remove(entidad);
        }

        
        /**
         * 
         * Método encargado de realizar un update en el sistema con la entidad
         * actualizada proporcionada.
         * 
         * @param updatedInstance
         *            edidad con la información actualizada
         * @return registro actualizado
         */
        public <T> T actualizar(Object updatedInstance) {               
                
                T obj = (T) entityManager.merge(updatedInstance);

                return obj;
        }

        /**
         * 
         * Construye el arreglo de predicados de acuerdo a un mapa de filtros
         * 
         * @param filters
         * @param criteriaBuilder
         * @return arreglo de predicados segun filtros
         */
        private Predicate[] construirPredicados(Map<String, Object> filters,
                        CriteriaBuilder criteriaBuilder, Root<?> root) {
                /* se crea la base del arreglo */
                List<Predicate> predicates = new ArrayList<Predicate>();

                /* se itera por cada uno de los valores */
                for (Map.Entry<String, Object> map : filters.entrySet()) {
                        Object value = map.getValue();
                        if (value == null) {
                                predicates.add(criteriaBuilder.isNull(root.get(map.getKey())));
                        } else {
                                if (value instanceof String) {
                                        if (!value.toString().isEmpty()) {
                                                predicates.add(criteriaBuilder.equal(
                                                                root.get(map.getKey()), value));
                                        }
                                } else if (value instanceof Serializable) {
                                        Serializable newValue = (Serializable) value;
                                        predicates.add(criteriaBuilder.equal(
                                                        root.get(map.getKey()), newValue));
                                } else {
                                        predicates.add(criteriaBuilder.equal(
                                                        root.get(map.getKey()), value));
                                }
                        }
                }

                Predicate[] array = new Predicate[predicates.size()];
                return predicates.toArray(array);
        }

        /**
         * Metodo para ejecutar una NamedQuery con parametros.
         * 
         * @author <br>
         *         Camilo Andr�s Ferrer Bustos <br>
         *         Email: @.com<br>
         * @date 2/05/2013 10:00:13
         * @version 1.0
         * 
         * @param nombreNamedQuery
         *            , nombre de la NamedQuery.
         * @param params
         *            , parametros.
         * @return, la lista de entidades.
         */
        public <T> List<T> ejecutarNamedQuery(String nombreNamedQuery,
                        Object... params) {
                Query q = entityManager.createNamedQuery(nombreNamedQuery);
                if (params != null) {
                        for (int i = 0; i < params.length; i++) {
                                Object object = params[i];
                                q.setParameter(i + 1, object);

                        }

                }
                return q.getResultList();

        }

        /**
         * Metodo para ejecutar una NamedQuery con parametros y limite.
         * 
         * @author <br>
         *         Camilo Andr�s Ferrer Bustos <br>
         *         Email: @.com<br>
         * @date 2/05/2013 10:00:13
         * @version 1.0
         * 
         * @param nombreNamedQuery
         *            , nombre de la NamedQuery.
         * @param params
         *            , parametros.
         * @return, la lista de entidades.
         */
        public Query queryFromNamedQuery(String nombreNamedQuery, Object... params) {
                Query q = entityManager.createNamedQuery(nombreNamedQuery);
                if (params != null) {
                        for (int i = 0; i < params.length; i++) {
                                Object object = params[i];
                                q.setParameter(i + 1, object);

                        }

                }

                return q;

        }

        /**
         * Metodo para ejecutar una namedQuery.
         * 
         * @param nombreNamedQuery
         * @param params
         * @return
         */
        public <T> List<T> ejecutarNamedQuery(String query,
                        Map<String, Object> params) {

                Query q = entityManager.createNamedQuery(query);

                if (params != null) {
                        for (Map.Entry<String, Object> par : params.entrySet()) {
                                q.setParameter(par.getKey(), par.getValue());
                        }
                }

                List<T> resultList = q.getResultList();

                return resultList;
        }

        /**
         * Metodo para ejecutar una Query.
         * 
         * @param nombreNamedQuery
         * @param params
         * @return
         */
        public <T> List<T> ejecutarQuery(String query, Map<String, Object> params) {

                Query q = entityManager.createQuery(query);

                if (params != null) {
                        for (Map.Entry<String, Object> par : params.entrySet()) {
                                q.setParameter(par.getKey(), par.getValue());
                        }
                }

                List<T> resultList = q.getResultList();

                return resultList;
        }

        /**
         * Metodo para ejecutar una Query Nativa.
         * 
         * @param nombreNamedQuery
         * @param params
         * @return
         */
        public <T> List<T> ejecutarNativeQuery(String query, Object... params) {

                Query q = entityManager.createNativeQuery(query);

                if (params != null) {
                        for (int i = 0; i < params.length; i++) {
                                Object par = params[i];
                                q.setParameter(i + 1, par);

                        }
                }

                List<T> resultList = q.getResultList();

                return resultList;
        }

        
        /**
         * Forzar el envio a la BD del cambio.
         * 
         * @author <br>
         *         Andres Felipe Rios<br>
         *         email: arios@.co<br>
         * 
         * @date 12/09/2013
         * @version 1.0
         * 
         */
        public void flush() {
                entityManager.flush();
        }

    
}
