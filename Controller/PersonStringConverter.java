package Controller;

import org.folg.gedcom.model.Person;

import javafx.util.StringConverter;
import model.wrappers.NGedcom;
import model.wrappers.NPerson;

public class PersonStringConverter extends StringConverter<NPerson>
{
    
    private NGedcom gedcom;

    public PersonStringConverter(NGedcom gedcom)
    {
        this.gedcom = gedcom;
    }

    @Override
    public String toString(NPerson person)
    {
        return person.getName();
    }

    @Override
    public NPerson fromString(String string)
    {
        for(NPerson person : gedcom.getPeople())
        {
            if(person.getName().toLowerCase().contains(string))
            {
                return person;
            }
        }
        return null;
    }

}
