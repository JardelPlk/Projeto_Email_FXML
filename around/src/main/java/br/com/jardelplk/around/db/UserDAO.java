package br.com.jardelplk.around.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import br.com.jardelplk.around.entities.Aplicativo;
import br.com.jardelplk.around.entities.Email;
import br.com.jardelplk.around.entities.User;

public class UserDAO implements InterfaceDAO<User> {

	@Override
	public void persist(User user) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();
			User original = get(user.getUsername());
			em.getTransaction().begin();
			original.setPassword(user.getPassword());
			original.getAplicativos().clear();
			for (Aplicativo a1 : user.getAplicativos())
				original.getAplicativos().add(a1);
			original.getEmails().clear();
			for(Email e1 : user.getEmails())
				original.getEmails().add(e1);
			em.getTransaction().commit();
		}
	}

	@Override
	public void remove(User t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}

	@Override
	public User get(Object pk) {
		return UtilDB.getEntityManager().find(User.class, pk);
	}

	@Override
	public List<User> getAll() {
		return UtilDB.getEntityManager().createQuery("SELECT u FROM User u", User.class).getResultList();
	}
}
