package Model;

import javax.xml.bind.annotation.XmlElement;

public class ResidenceData {

	// Owned data
	@XmlElement(name="place")
	public String place;

	@XmlElement(name="in_migration_date")
	public String inMigrationDate;
	
	@XmlElement(name="out_migration_date")
	public String outMigrationDate;

	public ResidenceData()
	{
	}

	public ResidenceData(String place, String inMigrationDate, String outMigrationDate) 
	{
		this.place = place;
		this.inMigrationDate = inMigrationDate;
		this.outMigrationDate = outMigrationDate;
	}
	
	
	
}
