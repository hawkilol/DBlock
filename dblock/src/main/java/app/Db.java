package app;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
//Talvez randomizar os i's dos for's para buscas e atualizações
import javax.persistence.Query;

public class Db {
	public static long expPasso1(UtilDb utilDb, Pokemon pok1, Integer transacCount) throws InterruptedException, ExecutionException {
		int Threads = 2;
	    ExecutorService threadpool = Executors.newCachedThreadPool();
	    //threadpool.awaitTermination(1, TimeUnit.SECONDS);
	    Future<?> futureTask = threadpool.submit(() -> System.out.println("HI"));
	    long startTime = System.nanoTime();
		for (int i = 1; i<Threads; i++) {
	    	//futureTask = threadpool.submit(() ->  utilDb.update(em, pok1, 10));
	    	//Primeiro Nivel(100% das trans pedem shared lock)
	    	//Consulta
	    	futureTask = threadpool.submit(() ->  utilDb.buscaShared(pok1, transacCount/2));
	    	//Escrita
	    	futureTask = threadpool.submit(() ->  utilDb.updateShared(pok1, transacCount/2));
	    }
		while (!futureTask.isDone()) {
	    	//System.out.println("FutureTask is not finished yet..."); 
	    }
	
	    //long result = futureTask.get();
	    //System.out.println(counter.getLista1());
	    System.out.println(futureTask.get());    
	    threadpool.shutdown();
	    long endTime = System.nanoTime();

		long duration = (endTime - startTime);
	    return duration;
	}
	
	public static void expPasso2(UtilDb utilDb, Pokemon pok1, Integer transacCount) throws InterruptedException, ExecutionException {
		int Threads = 2;
	    ExecutorService threadpool = Executors.newCachedThreadPool();
	    //threadpool.awaitTermination(1, TimeUnit.SECONDS);
	    Future<?> futureTask = threadpool.submit(() -> System.out.println("HI"));
		for (int i = 1; i<Threads; i++) {
	    	
	    	//Segundo Nivel(Só trans de leitura pedem shared lock)
	    	//Consulta
	    	futureTask = threadpool.submit(() ->  utilDb.buscaShared(pok1, transacCount/2));
	    	//Escrita
	    	futureTask = threadpool.submit(() ->  utilDb.update(pok1, transacCount/2));
	
	    }
		while (!futureTask.isDone()) {
	    	//System.out.println("FutureTask is not finished yet..."); 
	    }
	
	    //long result = futureTask.get();
	    //System.out.println(counter.getLista1());
	    System.out.println(futureTask.get());    
	    threadpool.shutdown();
	}
	
	public static void expPasso3(UtilDb utilDb, Pokemon pok1, Integer transacCount) throws InterruptedException, ExecutionException {
		int Threads = 2;
	    ExecutorService threadpool = Executors.newCachedThreadPool();
	    //threadpool.awaitTermination(1, TimeUnit.SECONDS);
	    Future<?> futureTask = threadpool.submit(() -> System.out.println("HI"));
		for (int i = 1; i<Threads; i++) {
	    	
	    	//Terceiro Nivel(Só trans de escrita pedem exclusive lock)
	    	//Consulta
	    	futureTask = threadpool.submit(() ->  utilDb.busca(pok1, transacCount/2));
	    	//Escrita
	    	futureTask = threadpool.submit(() ->  utilDb.updateExclusive(pok1, transacCount/2));
	    	
	   
	    }
		while (!futureTask.isDone()) {
	    	//System.out.println("FutureTask is not finished yet..."); 
	    }
	
	    //long result = futureTask.get();
	    //System.out.println(counter.getLista1());
	    System.out.println(futureTask.get());    
	    threadpool.shutdown();
	}
	
	public static void expPasso4(UtilDb utilDb, Pokemon pok1, Integer transacCount) throws InterruptedException, ExecutionException {
		int Threads = 2;
	    ExecutorService threadpool = Executors.newCachedThreadPool();
	    //threadpool.awaitTermination(1, TimeUnit.SECONDS);
	    Future<?> futureTask = threadpool.submit(() -> System.out.println("HI"));
		for (int i = 1; i<Threads; i++) {
	    	
	    	//Quarto Nivel(Só todas trans de escrita pedem exclusive lock 
	    	//e todas as trans de leitura pedem shared lock)
	    	//Consulta
	    	futureTask = threadpool.submit(() ->  utilDb.buscaShared(pok1, transacCount));
	    	//Escrita
	    	futureTask = threadpool.submit(() ->  utilDb.updateExclusive(pok1, transacCount));
	    	
	    }
		while (!futureTask.isDone()) {
	    	//System.out.println("FutureTask is not finished yet..."); 
	    }
	
	    //long result = futureTask.get();
	    //System.out.println(counter.getLista1());
	    System.out.println(futureTask.get());    
	    threadpool.shutdown();
		
	}

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ArrayList<Integer> listaInt = new ArrayList<Integer>();
		MyCounter counter = new MyCounter();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		
		UtilDb utilDb = new UtilDb();
		
		//final Pokemon pok = new Pokemon(null, "Bulbasaur");
		//final Pokemon pok1 = new Pokemon(null, "Bulbasaur");
		
		final Pokemon pok = new Pokemon(null, "Ivysaur");
		final Pokemon pok1 = new Pokemon(null, "Venusaur");
		//Na verdade 1000 transações são 500 + 500 e não 1 até 499 e 500 até 1000
		//Então só precisa de 500 para 100 transações por exemplo
		
		
		
		
		//Atualiza os 10 primeiros para Ivysaur sem lock
//		pok = new Pokemon(null, "Ivysaur");
//		utilDb.update(em, pok, 10);
		
		
		//Atualiza os 10 primeiros para Ivysaur(talvez Venusaur) com lock exclusivo (escrita)
		
		//pok1 = new Pokemon(null, "Venusaur");
		//pok1 = new Pokemon(null, "Venusaur");
		//pok = new Pokemon(null, "Ivysaur");
		
		//utilDb.updateExclusiveLock(em, pok, 10);
		
		//Talvez fazer um novo EntityManager para cada trans
		//Talvez abrir uma nova sessão com o hibernate para cada trans
		
		long startTime = System.nanoTime();
		
		long endTime = System.nanoTime();

		long duration = (endTime - startTime);
		
		int Threads = 2;
	    //ExecutorService threadpool = Executors.newCachedThreadPool();
	    //threadpool.awaitTermination(1, TimeUnit.SECONDS);
	    //Future<?> futureTask = threadpool.submit(() -> System.out.println("HI"));
		Integer transacCount = 1000;
		//utilDb.populate(em, pok, transacCount/2);
		
		startTime = System.nanoTime();
	    expPasso1(utilDb, pok1, transacCount);
	    //expPasso2(utilDb, pok1, transacCount);
	    //expPasso3(utilDb, pok1, transacCount);
	    //expPasso4(utilDb, pok1, transacCount);
	    endTime = System.nanoTime();
	    duration = (endTime - startTime);
	    //duration = expPasso1(utilDb, pok1, transacCount);
	    System.out.println("Duration: "+duration);
	    
	    //utilDb.updateExclusive(pok1, transacCount);
	    //utilDb.buscaShared(pok1, transacCount);
//	    for (int i = 1; i<Threads; i++) {
//	    	//futureTask = threadpool.submit(() ->  utilDb.update(em, pok1, 10));
//	    	//Primeiro Nivel(100% das trans pedem shared lock)
//	    	//Consulta
//	    	futureTask = threadpool.submit(() ->  utilDb.buscaShared(pok1, transacCount));
//	    	//Escrita
//	    	futureTask = threadpool.submit(() ->  utilDb.updateExclusive(pok1, transacCount));
//	    	
//	    	//Segundo Nivel(Só trans de leitura pedem shared lock)
//	    	//Consulta
//	    	futureTask = threadpool.submit(() ->  utilDb.buscaShared(pok1, transacCount));
//	    	//Escrita
//	    	futureTask = threadpool.submit(() ->  utilDb.update(pok1, transacCount));
//	    	
//	    	//Terceiro Nivel(Só trans de escrita pedem exclusive lock)
//	    	//Consulta
//	    	futureTask = threadpool.submit(() ->  utilDb.busca(pok1, transacCount));
//	    	//Escrita
//	    	futureTask = threadpool.submit(() ->  utilDb.updateExclusive(pok1, transacCount));
//	    	
//	    	//Quarto Nivel(Só todas trans de escrita pedem exclusive lock 
//	    	//e todas as trans de leitura pedem shared lock)
//	    	//Consulta
//	    	futureTask = threadpool.submit(() ->  utilDb.buscaShared(pok1, transacCount));
//	    	//Escrita
//	    	futureTask = threadpool.submit(() ->  utilDb.updateExclusive(pok1, transacCount));
//	    	
//	    	//futureTask = threadpool.submit(() ->  utilDb.update(em, pok1, 10));
//	    	//futureTask = threadpool.submit(() ->  utilDb.update(em, pok1, 10));
//	    	//futureTask = threadpool.submit(() ->  utilDb.update(em, pok, 10));
//	    	//futureTask = threadpool.submit(() ->  utilDb.updateExclusive(pok1, 10));
//	    	//futureTask = threadpool.submit(() ->  utilDb.buscaShared(pok, 10));
//	    	
//	    	//utilDb.update2(em, pok, 10);
//	    	//futureTask = threadpool.submit(() ->  utilDb.salvar2(em, i));
//	    	
//	    	//futureTask = threadpool.submit(() ->  utilDb.salvar2(em, i));
////	    	em.getTransaction().begin();
////			Pokemon pokAux = em.find(Pokemon.class, i);
////			pokAux.setName("VenusaurPag");
////			em.persist(pokAux);
////			em.getTransaction().commit();
//	    	//utilDb.update2(em, pok1, 10);
//	    	//utilDb.updateExclusiveLock(em, pok, 10);
//	    	//utilDb.updateExclusiveLock(em, pok1, 10);
//	    	//futureTask = threadpool.submit(() ->  System.out.println("OI"));
//	    	//futureTask = threadpool.submit(() ->  System.out.println("OLA"));
//	    	
//	    }
	    //Aguardar a execução de todas as threads, talvez?
	    //https://www.baeldung.com/java-executor-wait-for-threads
	    

//	    while (!futureTask.isDone()) {
//	    	//System.out.println("FutureTask is not finished yet..."); 
//	    }
//	
//	    //long result = futureTask.get();
//	    //System.out.println(counter.getLista1());
//	    System.out.println(futureTask.get());    
//	    threadpool.shutdown();
	    
	    System.out.println(counter.getLista1());
	    System.out.println(counter.getLista());
	    
	    for (int i = 0; i<counter.getLista().size(); i++) {
	    	System.out.println(counter.getLista().get(i).getTime());
	    	
	    	
	    	
	    }
	    
	    //emf.accept(em);
	    //Pokemon pokAux = doInTransaction();
	    
	    //Funcionando com query ou lock 
//	    em.getTransaction().begin();
//	    Query query = em.createQuery("from Pokemon where id = :id");
//		query.setParameter("id", 1);
//		//@Lock(LockModeType.PESSIMISTIC_WRITE)
//		Pokemon pokAux = em.find(Pokemon.class, 1,LockModeType.PESSIMISTIC_READ);
//		//log("After acquiring read lock", pokAux);
//		//em.lock(pokAux, LockModeType.PESSIMISTIC_WRITE);
//		//query.setLockMode(LockModeType.PESSIMISTIC_READ);
//		
//		
//		System.out.println(pokAux.getName());
//	    em.getTransaction().commit();
//	    em.close();
	      
	        
	      
//		Pokemon pokemon = new Pokemon(null, "Bulbasaur", "male/female",
//				0.7f, Arrays.asList(new String[] {"Grass", "Poison"}),
//				Arrays.asList(new String[] {"Fire",
//						"Psychic","Flying","Ice"}));
//		
//		
//		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
//		EntityManager em = emf.createEntityManager();
//		
//		UtilDb utilDb = new UtilDb();
//		
//		Pokemon pok = new Pokemon(null, "Bulbasaur");
//		
//		//utilDb.populate(em, pok, 10);
//		
//		//Atualiza os 10 primeiros para Ivysaur sem lock
//		pok = new Pokemon(null, "Ivysaur");
//		utilDb.update(em, pok, 10);
//		
//		
//		//Atualiza os 10 primeiros para Ivysaur com lock exclusivo (escrita)
//		pok = new Pokemon(null, "Venusaur");
//		utilDb.updateExclusiveLock(em, pok, 10);
//		
//		
//		
//		//Busca varios sem lock
//		pok = new Pokemon(null, "Venusaur");
//		utilDb.search(em, pok, 10);
				
		//Busca varios com lock compartilhado (leitura)
//		pok = new Pokemon(null, "Venusaur");
//		utilDb.searchSharedLock(em, pok, 10);
		
		//Implemantar atualizar por nome ex: Bulbasaur -> Ivysaur
		
		
//		em.getTransaction().begin();
//		
//		em.persist(pokemon);
//		em.getTransaction().commit();
//		
//		System.out.println("Done, PagMan!");
//		em.close();
//		emf.close();
		
		//Pokemon pok = em.find(Pokemon.class,1);
		//em.getTransaction().begin();
		
		
		//Student resultStudent = entityManager.find(Student.class, studentId);
		//em.lock(pok, LockModeType.PESSIMISTIC_WRITE);
		//https://www.baeldung.com/jpa-pessimistic-locking
		//Pokemon resultPokemon = em.find(Pokemon.class, 1);
		//em.lock(resultPokemon, LockModeType.PESSIMISTIC_WRITE);
		
		//em.getTransaction().commit();
		//em.close();
		//emf.close();
		
		//System.out.println(pok);
		
		
		
		
	}
	private static void log(Object... msgs) {
	      System.out.println(LocalTime.now() + " - " + Thread.currentThread().getName() +
	              " - " + Arrays.toString(msgs));
	  }

}
