package br.com.jardelplk.around.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import br.com.jardelplk.around.entities.Aplicativo;

public class AplicativoDAO implements InterfaceDAO<Aplicativo> {

	@Override
	public void persist(Aplicativo t) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();
			Aplicativo original = get(t.getNome());
			em.getTransaction().begin();
			original.setDescricao(t.getDescricao());
			em.getTransaction().commit();
		}
	}

	@Override
	public void remove(Aplicativo t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}

	@Override
	public Aplicativo get(Object pk) {
		return UtilDB.getEntityManager().find(Aplicativo.class, pk);
	}

	@Override
	public List<Aplicativo> getAll() {
		return UtilDB.getEntityManager().createQuery("SELECT a FROM Aplicativo a", Aplicativo.class).getResultList();
	}
}
