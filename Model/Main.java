package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import de.nixosoft.jlr.JLROpener;

public class Main {

	public static void main(String[] args) {

		JAXBContext jc;
		try {

			// JAXBContext jaxbContext = JAXBContext.newInstance(Pair.class);

			// Anne
			PersonData inger = new PersonData();
			inger.id = "11";
			inger.name = "Inger Lindgren";
			inger.birthDate = "1935";

			// Anne
			PersonData arnold = new PersonData();
			arnold.id = "22";
			arnold.name = "Arnold Lindgren";
			arnold.birthDate = "1933";
			
			// Anne
			PersonData anne = new PersonData();
			anne.id = "1";
			anne.name = "Anne Lindgren";
			anne.birthDate = "1967-10-13";

			// Thomas
			PersonData thomas = new PersonData();
			thomas.id = "2";
			thomas.name = "Arnold Thomas Lindgren";
			thomas.birthDate = "1965-01-17";
			thomas.addResidence("Piongatan", "1965", "1985");
			thomas.addResidence("Olofshöjd", "1985-1990", null);
			thomas.addResidence("Träringen", "1994-10-30", null);
			thomas.addResidence("Studiegången", null, "2000");
			thomas.addResidence("Anneberg", "2000", "2000");
			thomas.addResidence("Sågmästaregatan 1E", "2000", "2013-09");
			thomas.father = arnold;
			thomas.mother = inger;

			MarriageData anneAndThomas = new MarriageData();
			anneAndThomas.firstPerson = anne;
			anneAndThomas.secondPerson = thomas;
			anneAndThomas.id = "01";
			anneAndThomas.marriageDate = "2005-07";

			anne.marriages.add(anneAndThomas);
			thomas.marriages.add(anneAndThomas);

			// Johannes
			PersonData johannes = new PersonData();
			johannes.id = "3";
			johannes.name = "Johannes Arnold Thomas Lindgren";
			johannes.birthDate = "1994-10-30";
			johannes.father = thomas;
			johannes.mother = anne;
			johannes.addResidence("Träringen", "1994-10-30", null);
			johannes.addResidence("Studiegången", null, "2000");
			johannes.addResidence("Anneberg", "2000", "2000");
			johannes.addResidence("Sågmästaregatan 1E", "2000", "2013-09");

			// Elin
			PersonData elin = new PersonData();
			elin.id = "4";
			elin.name = "Elin Hanna Ingegärd Lindgren";
			elin.birthDate = "2001-10-13";
			elin.father = thomas;
			elin.mother = anne;

			ModelData modelData = new ModelData();
			modelData.marriages.add(anneAndThomas);
			modelData.persons.add(anne);
			modelData.persons.add(thomas);
			modelData.persons.add(johannes);
			modelData.persons.add(elin);

			File dataFile = new File("/home/johannes/workspace/AncestryModel/data.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ModelData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(modelData, dataFile);
			jaxbMarshaller.marshal(modelData, System.out);

			//
			// jc = JAXBContext.newInstance(ModelData.class);
			//
			// Unmarshaller unmarshaller = jc.createUnmarshaller();
			// ModelData tests = (ModelData) unmarshaller.unmarshal(dataFile);
			//
			// Marshaller marshaller = jc.createMarshaller();
			// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// marshaller.marshal(tests, System.out);
			
			latexToPdf(thomas);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void latexToPdf(PersonData person) {
		File workingDirectory = new File("/home/johannes/workspace/AncestryModel");

		File template = new File(workingDirectory.getAbsolutePath() + File.separator + "latex_template.tex");

		File tempDir = new File(workingDirectory.getAbsolutePath() + File.separator + "personaktTest");
		if (!tempDir.isDirectory()) {
			tempDir.mkdir();
		}

		File invoice1 = new File(tempDir.getAbsolutePath() + File.separator + "johannes_personakt.tex");

		try {
			JLRConverter converter = new JLRConverter(workingDirectory);

			converter.replace("name", person.name);
			converter.replace("birth_date", person.birthDate);
			converter.replace("father", person.father.name + " (\\#" + person.father.id + ")");
			converter.replace("mother", person.mother.name + " (\\#" + person.mother.id + ")");

			// Residence
			ArrayList<ArrayList<String>> services;
			services = new ArrayList<ArrayList<String>>();
			for(ResidenceData residence : person.residences)
			{
				ArrayList<String> subservice = new ArrayList<String>();

				String place = nullToEmptyStr(residence.place);
				String timeInterval = nullToEmptyStr(residence.inMigrationDate) + " -- " + nullToEmptyStr(residence.outMigrationDate);
				
				subservice.add(place);
				subservice.add(timeInterval);
				
				services.add(subservice);
			}
			converter.replace("residences", services);

			// Marriages
			services = new ArrayList<ArrayList<String>>();
			for(MarriageData marriage : person.marriages)
			{
				ArrayList<String> subservice = new ArrayList<String>();

				PersonData partner = person.getPartner(marriage);
				String name = nullToEmptyStr(partner.name + " (\\#" + partner.id + ")");
				String timeInterval = nullToEmptyStr(marriage.marriageDate) + " -- " + nullToEmptyStr(marriage.separationDate);
				
				subservice.add(name);
				subservice.add(timeInterval);
				
				services.add(subservice);
			}
			converter.replace("marriages", services);


			if (!converter.parse(template, invoice1)) {
				System.out.println(converter.getErrorMessage());
			}

	

			File desktop = new File(workingDirectory.getAbsolutePath());

			JLRGenerator pdfGen = new JLRGenerator();

			if (!pdfGen.generate(invoice1, desktop, workingDirectory)) {
				System.out.println(pdfGen.getErrorMessage());
			}

			JLROpener.open(pdfGen.getPDF());

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private static String nullToEmptyStr(String str) {
		if(str == null)
		{
			return "";
		}
		return str;
	}

}
