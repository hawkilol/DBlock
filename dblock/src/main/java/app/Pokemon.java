package app;
import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Pokemon implements Serializable{
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		
		private Integer id;
		private String name;
		//private List<String> moves;
		private String gender;
		private Float height;
		private String weight;
		//private List<Type> type;
		//@OneToMany(targetEntity=Pokemon.class, mappedBy="pokemon", fetch=FetchType.EAGER)
		@ElementCollection
		private List<String> type;
		@ElementCollection
		private List<String> weaknesses;
	  
	  //Stats
	  //moves


	  // public empty constructor needed for retrieving the POJO
	  public Pokemon() {
	  }

	  public Pokemon(Integer id, String name, String gender, Float height, List<String> type, List<String> weaknesses) {
		    //this.id = number;
		  	super();
		    this.id = id;
		    this.name = name;
		    this.gender = gender;
		    this.height = height;
		    //this.weight = weight;
		    this.type = type;
		    this.weaknesses = weaknesses;
	    
	  }
	  
	  public Pokemon(Integer id, String name) {
		    //this.id = number;
		  	super();
		    this.id = id;
		    this.name = name;
	  }
	  
	  // public Pokemon(
	  //     String name,
	  //     List<String> moves,
	  //     String sex,
	  //     Boolean shiny,
	  //     Float height,
	  //     Integer level,
	  //     Integer speed,
	  //     Integer defense,
	  //     Integer hp,
	  //     Integer attackPower,
	  //     Integer type,
	  //     String region) {
	  //   this.name = name;
	  //   this.moves = moves;
	  //   this.sex = sex;
	  //   this.shiny = shiny;
	  //   this.height = height;
	  //   this.level = level;
	  //   this.speed = speed;
	  //   this.defense = defense;
	  //   this.hp = hp;
	  //   this.attackPower = attackPower;
	  //   this.type = type;

	  // }

	  public Integer getId() {
	    return id;
	  }

	  public void setId(Integer id) {
	    this.id = id;
	  }

	  
	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getGender() {
	    return gender;
	  }

	  public void setGender(String gender) {
	    this.gender = gender;
	  }

	  public Float getHeight() {
	    return height;
	  }

	  public void setHeight(Float height) {
	    this.height = height;
	  }

	  // public String getWeigth() {
	  //   return weight;
	  // }

	  // public void setWeight(String weight) {
	  //   this.weight = weight;
	  // }

	  public List<String> getType() {
	    return type;
	  }

	  public void setType(List<String> type) {
	    this.type = type;
	  }
	  
	  public List<String> getWeaknesses() {
	    return weaknesses;
	  }

	  public void setWeaknesses(List<String> weaknesses) {
	    this.weaknesses = weaknesses;
	  }

	  
	  @Override
	  public String toString() {
	    return "Pokemon [id=" + id + ", name=" + name + ", gender="+ gender+", height=" + height + ", type=" + type +", weaknesses=" + weaknesses
	        + "]";
	  }
	}