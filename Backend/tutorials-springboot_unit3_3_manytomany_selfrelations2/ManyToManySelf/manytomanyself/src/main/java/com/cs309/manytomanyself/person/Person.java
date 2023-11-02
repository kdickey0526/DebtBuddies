package com.cs309.manytomanyself.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

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
	private int whack;
	private int WarWon;
	private int WarLost;
	private int Profile;


	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="friends_with",
	joinColumns={@JoinColumn(name="person_id")},
	inverseJoinColumns={@JoinColumn(name="friend_id")})
	@JsonIgnore
	private Set<Person> friends = new HashSet<Person>();
	
	@ManyToMany(mappedBy="friends")
	@JsonIgnore
	private Set<Person> friendsOf = new HashSet<Person>();

	public Person(String userName, String email, String password) {
		this.name = userName;
		this.Coins = 0;
		this.email = email;
		this.password = password;
		this.isOnline = true;
		this.whack = 0;
		this.WarWon = 0;
		this.WarLost = 0;
		this.Profile = 0;
	}

	public Person() {
		/*friend = new ArrayList<>();*/
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
	}

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
	public int getWhack(){
		return whack;
	}
	public void setWhack(int Whack){
		this.whack = Whack;
	}
	public int getWarWon(){
		return WarWon;
	}
	public void setWarWon(int WarWon){
		this.WarWon = WarWon;
	}
	public int getWarLost(){
		return WarLost;
	}
	public void setWarLost(int email){
		this.WarLost = WarLost;
	}
	public int getProfile(){
		return Profile;
	}
	public void setProfile(int Profile){
		this.Profile = Profile;
	}

}
