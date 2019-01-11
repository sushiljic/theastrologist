package com.theastrologist.domain.individual;

import com.theastrologist.domain.Degree;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Individuals")
public class Individual {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "VARCHAR(255)")
	private UUID id;

	private String name;
	private DateTime date;
	private String location;
	private String country;
	private Degree latitude;
	private Degree longitude;
	private Gender gender;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Degree getLatitude() {
		return latitude;
	}

	public void setLatitude(Degree latitude) {
		this.latitude = latitude;
	}

	public Degree getLongitude() {
		return longitude;
	}

	public void setLongitude(Degree longitude) {
		this.longitude = longitude;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
