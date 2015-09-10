package Model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "ModelData")
@XmlAccessorType(XmlAccessType.FIELD)
public class ModelData {

	@XmlElement(name="person")
	public List<PersonData> persons = new ArrayList<PersonData>();

	@XmlElement(name="marriage")
	public List<MarriageData> marriages = new ArrayList<MarriageData>();
	
}
