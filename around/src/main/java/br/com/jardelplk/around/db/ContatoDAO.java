package br.com.jardelplk.around.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import br.com.jardelplk.around.Contato;

public class ContatoDAO implements InterfaceDAO<Contato> {

	@Override
	public void persist(Contato t) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();//Para finalizar o ultimo transaction
			Contato original = get(t.getUsername());
			em.getTransaction().begin();
			em.getTransaction().commit();
		}
		
	}

	@Override
	public void remove(Contato t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}
	
	@Override
	public Contato get(Object pk) {
		return UtilDB.getEntityManager().find(Contato.class, pk);
	}

	@Override
	public List<Contato> getAll() {
		return UtilDB.getEntityManager().createQuery("SELECT u FROM Contato u", Contato.class).getResultList();
	}

}
