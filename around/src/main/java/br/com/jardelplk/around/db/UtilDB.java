package br.com.jardelplk.around.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.jardelplk.around.AlertUtil;
import br.com.jardelplk.around.Contato;
import br.com.jardelplk.around.Email;
import br.com.jardelplk.around.User;
import javafx.scene.control.Alert;

public class UtilDB {

	private static EntityManagerFactory entityManagerFactory;//Fabrica de entitysManagers que vai construir
	private static EntityManager entityManager;//Gerenciador de entidades
	
	//Criar o entityManagerFActory apartir do persistence
	public static EntityManagerFactory getEntityManagerFactory() {
		if(entityManagerFactory == null)
			entityManagerFactory = Persistence.createEntityManagerFactory("teste");
		
		return entityManagerFactory;
	}
	
	//Método para trabalhar com as entidades no banco de dados
	public static EntityManager getEntityManager() {
		if(entityManager == null)
			entityManager = getEntityManagerFactory().createEntityManager();
		return entityManager;
	}
	
	//Fechar conexões
	public static void closeConn() {
		if(entityManagerFactory != null)
			entityManagerFactory.close();
		if(entityManager != null)
			entityManager.close();
	}
	
	public static void initDB() {
		for(User u : consumeAPI(consultAPI()))
			new UserDAO().persist(u);
		
		User u = new User("admin", "teste");
		new UserDAO().persist(u);
		
		Email e1 = new Email("Auditoria", u.getUsername(), "joao@gmail.com", "Auditoria marcada para ás 15:00 horas.");
		Email e2 = new Email("Palestra", u.getUsername(), "daniel@hotmail.com", "Palestra de hoje cancelada.");
		
		new EmailDAO().persist(e1);
		new EmailDAO().persist(e2);
		
		u.getEmails().add(e1);
		new UserDAO().persist(u);
		
		Contato c1 = new Contato("jardel@gmail.com");
		
		new ContatoDAO().persist(c1);
		u.getContatos().add(c1);
		new UserDAO().persist(u);
		

	}
	
	private static List<User> consumeAPI(List<String> users) {
		 List<User> result = new ArrayList<User>();
		 for(int lineIndex = 0; lineIndex < users.size(); lineIndex++) {
	    	String line = users.get(lineIndex);
	    	if(line.contains("username")) {
				//Processamos o nome do usuário
	    		String username = processJSONLine(line);
	    		System.out.println(username);
	    			
	    		//Vamos para a próxima linha
	    		lineIndex++;
	    		line = users.get(lineIndex);
	    			
	    		//Processamos o password
	    		String password = processJSONLine(line);
	    		System.out.println(password);
	    			
	    		User user = new User(username, password);

	    		//Adicionando o usuário que veio da API
	    		new UserDAO().persist(user);
	    			
	    			
	    		//Criando e adicionando um novo usuário para testes
	    		User user2 = new User("teste", "teste");

	    		new UserDAO().persist(user2);
	    			
	    		//Atualizando o usuário de testes
	    		UtilDB.getEntityManager().getTransaction().begin();

	    		UtilDB.getEntityManager().getTransaction().commit();
	    			
				/*
				 * em.getTransaction().begin();//Iniciar a trasação
				 * em.persist(user2);//Persistir/adicionar o usuário
				 * em.getTransaction().commit();//Commita-la
				 */    			
	  
	    		//Removendo o usuário de testes
	    		new UserDAO().remove(user2);
	    			
	    		//Buscando o usuário admin
	    		User admin = new UserDAO().get("admin");
	    		System.out.println(admin.getUsername());
	    			
				/*
				 * em.close();//Fechar a conexão
				 */    
	    			
	    	}
		}
		return result;
	}

		private static String processJSONLine(String line) {
			String[] dividedLine = line.split(":");//Quebrar a string em duas
			String username = dividedLine[1];
			username = username.replace(",", " ");//Alterar a virgula por espaço em branco
			username = username.replace("\"", " ");//Alterar as aspas por espaço em branco
			username = username.trim();//Eliminar espaços em branco
			return username;
		}
	    
	    private static List<String> consultAPI(){
			List<String> result = new ArrayList<>();
	    	try {
				URL url = new URL("http://www.lucasbueno.com.br/steam.json");
				URLConnection uc = url.openConnection();
				InputStreamReader input = new InputStreamReader(uc.getInputStream());
				BufferedReader in = new BufferedReader(input);
				String inputLine;
				
				while((inputLine = in.readLine()) != null) {
					result.add(inputLine);
				}
				
				in.close();
				
	    	} catch (Exception e) {
				Alert alert = AlertUtil.error("Erro", "Erro ao consumir a API!", "Erro ao consumir a API!", e);
				alert.showAndWait();
			}
	    	return result;	
	    }
	
}
