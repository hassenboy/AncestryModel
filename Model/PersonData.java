package Model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;

public class PersonData extends Unique{

	// Owned data
	@XmlElement(name="name")
	public String name;

	@XmlElement(name="birth_date")
	public String birthDate;

	@XmlElement(name="death_date")
	public String deathDate;
	
	@XmlIDREF
	@XmlElement(name="father")
	public PersonData father;
	
	@XmlIDREF
	@XmlElement(name="mother")
	public PersonData mother;
	
	@XmlElement(name="residence")
	public List<ResidenceData> residences = new ArrayList<ResidenceData>();
	
	// Shared data

	@XmlTransient  
	public List<MarriageData> marriages = new ArrayList<MarriageData>();

	public void addResidence(String place, String inMigrationDate, String outMigrationDate) {
		residences.add(new ResidenceData(place, inMigrationDate, outMigrationDate));
	}
	
	public PersonData getPartner(MarriageData m)
	{
		// Assume a reference to this object lies in the structure
		if(m.firstPerson != null && m.firstPerson != this && m.secondPerson == this)
		{
			return m.firstPerson;
		}
		else if(m.secondPerson != null && m.secondPerson != this && m.firstPerson == this)
		{
			return m.secondPerson;
		}
		return null;
	}
	
	public ArrayList<PersonData> getPartners()
	{
		ArrayList<PersonData> arr = new ArrayList<PersonData>();
		for(MarriageData m : this.marriages)
		{
			if(m.firstPerson != null && m.firstPerson != this && m.secondPerson == this)
			{
				arr.add(m.firstPerson);
			}
			else if(m.secondPerson != null && m.secondPerson != this && m.firstPerson == this)
			{
				arr.add(m.secondPerson);
			}
			// else: something wrong with datastructure m
		}
		return arr;
	}
}
