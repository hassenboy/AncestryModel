package model.wrappers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.folg.gedcom.model.Family;
import org.folg.gedcom.model.Gedcom;
import org.folg.gedcom.model.Header;
import org.folg.gedcom.model.Media;
import org.folg.gedcom.model.Note;
import org.folg.gedcom.model.Person;
import org.folg.gedcom.model.Repository;
import org.folg.gedcom.model.Source;
import org.folg.gedcom.model.Submission;
import org.folg.gedcom.model.Submitter;
import org.folg.gedcom.model.Visitor;
import org.folg.gedcom.parser.ModelParser;
import org.folg.gedcom.visitors.GedcomWriter;
import org.xml.sax.SAXParseException;

public class NGedcom {

	private Gedcom gedcom;

	private List<NPerson> persons = new ArrayList<NPerson>();
	private List<NFamily> families = new ArrayList<NFamily>();

	private Map<String, NPerson> personIndex = new HashMap<String, NPerson>();
	private Map<String, NFamily> familyIndex = new HashMap<String, NFamily>();

	public NGedcom(File file) throws SAXParseException, IOException
	{
        this(loadUnwrappedGedcom(file));
	}
	
	public NGedcom(Gedcom gedc)
	{
		this.gedcom = gedc;
		
		for(Person p : gedcom.getPeople())
		{
			addPerson(new NPerson(p));
		}

		for(Family f : gedcom.getFamilies())
		{
			addFamily(new NFamily(f));
		}
	}
	
	public List<NPerson> getPeople() {
		return persons;
	}
	
	//
	// =======================================Delegate methods ========================================================
	//
	
	public Map<String, Object> getExtensions() {
		return gedcom.getExtensions();
	}

	public Object getExtension(String key) {
		return gedcom.getExtension(key);
	}

	public Header getHeader() {
		return gedcom.getHeader();
	}

//	public List<Person> getPeople() {
//		return gedcom.getPeople();
//	}

	public NPerson getPerson(String id) {
		return personIndex.get(id);
	}

	public void addPerson(NPerson person) {
		persons.add(person);
		personIndex.put(person.getId(), person);
	}

	public List<NFamily> getFamilies() {
		return families;
	}

	public NFamily getFamily(String id) {
		return familyIndex.get(id);
	}

	public void addFamily(NFamily family) 
	{
		families.add(family);
		familyIndex.put(family.getId(), family);
	}

	public List<Media> getMedia() {
		return gedcom.getMedia();
	}

	public Media getMedia(String id) {
		return gedcom.getMedia(id);
	}

	public void addMedia(Media m) {
		gedcom.addMedia(m);
	}

	public List<Note> getNotes() {
		return gedcom.getNotes();
	}

	public Note getNote(String id) {
		return gedcom.getNote(id);
	}

	public void addNote(Note note) {
		gedcom.addNote(note);
	}

	public void addSource(Source source) {
		gedcom.addSource(source);
	}

	public void addRepository(Repository repository) {
		gedcom.addRepository(repository);
	}

	public void createIndexes() {
		gedcom.createIndexes();
	}

	public void accept(Visitor visitor) {
		gedcom.accept(visitor);
	}

	public boolean equals(Object obj) {
		return gedcom.equals(obj);
	}

	public void putExtension(String id, Object extension) {
		gedcom.putExtension(id, extension);
	}

	public void setHeader(Header head) {
		gedcom.setHeader(head);
	}

	public void visitContainedObjects(Visitor visitor) {
		gedcom.visitContainedObjects(visitor);
	}
	
	public List<Source> getSources() {
		return gedcom.getSources();
	}

	public Source getSource(String id) {
		return gedcom.getSource(id);
	}

	public List<Repository> getRepositories() {
		return gedcom.getRepositories();
	}

	public Repository getRepository(String id) {
		return gedcom.getRepository(id);
	}

	public void setRepositories(List<Repository> repositories) {
		gedcom.setRepositories(repositories);
	}

	public Submitter getSubmitter() {
		return gedcom.getSubmitter();
	}

	public Submission getSubmission() {
		return gedcom.getSubmission();
	}

	public int hashCode() {
		return gedcom.hashCode();
	}

	public void setSubmitter(Submitter subm) {
		gedcom.setSubmitter(subm);
	}

	public void setSubmission(Submission subn) {
		gedcom.setSubmission(subn);
	}

	public String toString() {
		return gedcom.toString();
	}
	
	public Gedcom getGedcom()
	{
		return gedcom;
	}

	private static Gedcom loadUnwrappedGedcom(File gedcomFile) throws SAXParseException, IOException
	{
        ModelParser modelParser = new ModelParser();
        
        Gedcom gedcom = modelParser.parseGedcom(gedcomFile);
        gedcom.createIndexes();
        return gedcom;
	}
	
    public static NGedcom loadGedcom(File gedcomFile) throws SAXParseException, IOException
    {
        return new NGedcom(loadUnwrappedGedcom(gedcomFile));
    }
    
    public static void saveGedcom(NGedcom gedcom, File saveFile) throws IOException
    {
        GedcomWriter writer = new GedcomWriter();
        writer.write(gedcom.getGedcom(), saveFile);
    }

    public void save(File file) throws IOException
    {
        saveGedcom(this, file);
    }
	
}
