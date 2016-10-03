package kg.freesms.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

abstract class GenericDAO<T> implements Serializable {

	private static final long serialVersionUID = -1071644144646792819L;

	private static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("afreesmsPU");
	protected EntityManager em;
	private Class<T> entityClass;

	public EntityManager getEntityManager() {
		return em;
	}

	public void beginTransaction() {
		em = emf.createEntityManager();

		em.getTransaction().begin();
	}

	public void commit() {
		em.getTransaction().commit();
	}

	public void rollback() {
		em.getTransaction().rollback();
	}

	public void closeTransaction() {
		em.close();
	}

	public void commitAndCloseTransaction() {
		commit();
		closeTransaction();
	}

	public void flush() {
		em.flush();
	}

	public void joinTransaction() {
		em = emf.createEntityManager();
		em.joinTransaction();
	}

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void save(T entity) {
		em.persist(entity);
	}

	public void delete(Object id, Class<T> classe) {
		T entityToBeRemoved = em.getReference(classe, id);

		em.remove(entityToBeRemoved);
	}

	public T update(T entity) {
		return em.merge(entity);
	}

	public T find(int entityID) {
		return em.find(entityClass, entityID);
	}

	public T findReferenceOnly(int entityID) {
		return em.getReference(entityClass, entityID);
	}

	// Using the unchecked because JPA does not have a
	// em.getCriteriaBuilder().createQuery()<T> method
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	// Using the unchecked because JPA does not have a
	// query.getSingleResult()<T> method
	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			System.out
					.println("No result found for named query: " + namedQuery);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	public List<T> findByProperty(String property, Object value) {
		String query = new StringBuilder("select entity from ")
				.append(getEntityClass().getSimpleName())
				.append(" as entity where entity." + property + " = :property")
				.toString();
		System.out.println("====query====" + query);
		return getEntityManager().createQuery(query, getEntityClass())
				.setParameter("property", value).getResultList();
	}

	public List<T> findByPropertyWithFields(String property, Object value, String[] fields) {
		StringBuffer buffer = new StringBuffer("SELECT DISTINCT entity FROM ");
		buffer.append(getEntityClass().getSimpleName());
		buffer.append(" AS entity");
		for (String string : fields) {
			buffer.append(" LEFT JOIN FETCH entity." + string);
		}
		buffer.append(" where entity." + property + " = :property");
		return getEntityManager()
				.createQuery(buffer.toString(), getEntityClass())
				.setParameter("property", value).getResultList();
	}
	
	public List<T> loadLazilyByProperty(int from, int to, String property, Object value) {
		
		String buffer = new StringBuilder("select entity from ")
				.append(getEntityClass().getSimpleName())
				.append(" as entity where entity." + property + " = :property")
				.toString();
		System.out.println("====query====" + buffer);
		
		TypedQuery<T> query = getEntityManager().createQuery(buffer, getEntityClass());
		query.setParameter("property", value);
		
		return query.setFirstResult(from).setMaxResults(to).getResultList();
	}
	
	public Long countByProperty(String property, Object value) {
		
		StringBuffer buffer = new StringBuffer("SELECT COUNT(DISTINCT entity) FROM " + getEntityClass().getCanonicalName() + " entity ");
		buffer.append("WHERE entity." + property + " = :property");
		
		System.out.println("====query====" + buffer);
		
		TypedQuery<Long> query = getEntityManager().createQuery(buffer.toString(), Long.class);
		query.setParameter("property", value);
		
		return query.setMaxResults(1).getSingleResult();
	}
	
	private void populateQueryParameters(Query query,
			Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	protected Class<T> getEntityClass() {
		return entityClass;
	}
}
