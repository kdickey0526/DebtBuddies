package debtbuddies.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import debtbuddies.GameScores.GameScore;
import debtbuddies.Settings.Setting;

@Entity
@Table(name="persons")
@Data
public class Person {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="person_id")
    private Long id;



	@NotNull
	@Size(max=100)
	@Column
	private String name;

	private boolean isOnline;
	private String email;
	private String password;
	private int Coins;
	private String Profile;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gameScore_id")
	private GameScore gameScore;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "settings_id")
	private Setting settings;

	/*
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="friends_with",
	joinColumns={@JoinColumn(name="person_id")},
	inverseJoinColumns={@JoinColumn(name="friend_id")})
	@JsonIgnore
	private Set<Person> friends*//* = new HashSet<Person>()*/;

	/*@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinTable(name = "friends_with", joinColumns = {@JoinColumn(name = "person_id")}, inverseJoinColumns = {@JoinColumn(name = "friend_id")})
	private Set<Person> friends;*/

	/*
	@ManyToMany(mappedBy="friends")
	@JsonIgnore
	private Set<Person> friendsOf*//* = new HashSet<Person>()*/;



		/*@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "person_id")
		private Long id;

		@NotNull
		@Size(max = 100)
		@Column(nullable = true) // This is optional since fields are nullable by default
		private String name;

		@Column(nullable = true) // Specify nullable for other fields as needed
		private boolean isOnline;

		@Column(nullable = true)
		private String email;

		@Column(nullable = true)
		private String password;

		@Column(nullable = true)
		private Integer Coins;

		@Column(nullable = true)
		private Integer whack;

		@Column(nullable = true)
		private Integer WarWon;

		@Column(nullable = true)
		private Integer WarLost;

		@Column(nullable = true)
		private String Profile;

		@ManyToMany(cascade = { CascadeType.ALL })
		@JoinTable(name = "friends_with", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
		private Set<Person> friends;

		@ManyToMany(mappedBy = "friends")
		private Set<Person> friendsOf;*/
	public Person(String userName, String email, String password) {
		this.name = userName;
		this.Coins = 0;
		this.email = email;
		this.password = password;
		this.isOnline = true;
		this.Profile = "icon0";
	}

	public Person() {
		this.name = "name";
		this.Coins = 0;
		this.email = "";
		this.password = "password";
		this.isOnline = true;
		this.Profile = "icon0";
	}
	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public int getCoins(){
		return Coins;
	}

	public void setCoins(int Coins){
		this.Coins = Coins;
	}/*

	public Boolean getIsOnline(){
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline){
		this.isOnline = isOnline;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getProfile(){
		return Profile;
	}
	public void setProfile(String Profile){
		this.Profile = Profile;
	}

	public GameScore getGameScore(){
		return gameScore;
	}

	public void setgamScore(GameScore gameScore){
		this.gameScore = gameScore;
	}


	public Setting getSettings(){
		return settings;
	}

	public void setSetting(Setting settings){
		this.settings = settings;
	}*/



}
