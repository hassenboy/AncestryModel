package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

public class MarriageData extends Unique{

	@XmlIDREF
	@XmlElement(name="first_person")
	public PersonData firstPerson;

	@XmlIDREF
	@XmlElement(name="second_person")
	public PersonData secondPerson;

	@XmlElement(name="marriage_date")
	public String marriageDate;

	@XmlElement(name="separation_date")
	public String separationDate;
	
	
}
