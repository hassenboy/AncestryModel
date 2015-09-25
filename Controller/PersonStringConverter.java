package Controller;

import org.folg.gedcom.model.Person;

import Wrappers.NGedcom;
import Wrappers.NPerson;
import javafx.util.StringConverter;

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
