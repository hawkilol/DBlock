package app;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

import dom.Pokemon;

public class Db {
	
	public static void main(String[] args) {
		Pokemon pokemon = new Pokemon(null, "Bulbasaur", "male/female",
				0.7f, Arrays.asList(new String[] {"Grass", "Poison"}),
				Arrays.asList(new String[] {"Fire",
						"Psychic","Flying","Ice"}));
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
//		em.getTransaction().begin();
//		
//		em.persist(pokemon);
//		em.getTransaction().commit();
//		
//		System.out.println("Done, PagMan!");
//		em.close();
//		emf.close();
		
		Pokemon pok = em.find(Pokemon.class,1);
		//em.getTransaction().begin();
		
		
		//Student resultStudent = entityManager.find(Student.class, studentId);
		em.lock(pok, LockModeType.PESSIMISTIC_WRITE);
		//https://www.baeldung.com/jpa-pessimistic-locking
		//Pokemon resultPokemon = em.find(Pokemon.class, 1);
		//em.lock(resultPokemon, LockModeType.PESSIMISTIC_WRITE);
		
		//em.getTransaction().commit();
		//em.close();
		//emf.close();
		
		System.out.println(pok);
		
		
		
		
	}

}
