package br.com.jardelplk.around.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import br.com.jardelplk.around.entities.Email;

public class EmailDAO implements InterfaceDAO<Email> {

	@Override
	public void persist(Email email) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(email);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();
			Email original = get(email.getAssunto());
			em.getTransaction().begin();
			original.setDestinatario(original.getDestinatario());
			original.setMensagem(original.getMensagem());
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
