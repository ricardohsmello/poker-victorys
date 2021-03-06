package br.com.ricas.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Score {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int value;
	private String playerName;
	
	public void setValue(int value) {
		this.value = value;
	}

}
