package br.com.jardelplk.around.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import br.com.jardelplk.around.Email;

public class EmailDAO implements InterfaceDAO<Email> {

	@Override
	public void persist(Email t) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();//Para finalizar o ultimo transaction
			Email original = get(t.getAssunto());
			em.getTransaction().begin();
			original.setDestinatario(t.getDestinatario());
			original.setMensagem(t.getMensagem());
			em.getTransaction().commit();
		}
		
	}

	@Override
	public void remove(Email t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}
	
	@Override
	public Email get(Object pk) {
		return UtilDB.getEntityManager().find(Email.class, pk);
	}

	@Override
	public List<Email> getAll() {
		return UtilDB.getEntityManager().createQuery("SELECT e FROM Email e", Email.class).getResultList();
	}

}
