package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.folg.gedcom.model.Header;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;

import model.wrappers.NGedcom;
import model.wrappers.NPerson;

public class EditorFrame extends JFrame implements PersonSelectedListener{
	
	private NGedcom gedcom;

	private PersonEditorOld personEditor;
	
	private PagePanel pdfPanel;
	
	private File personalFile = new File("/home/johannes/workspace/AncestryModel/personalFile.pdf");

    private AutoCompleteBox<NPerson> personBox;
    
	public EditorFrame(NGedcom gedcom)
	{
		super("GEDCOM Editor Pro");
		this.gedcom = gedcom;
		
		setBackground(Color.WHITE);

        personBox = new AutoCompleteBox<NPerson>(gedcom.getPeople(), (p) -> p.getName(), (p) -> personSelected(p));
        
		personEditor = new PersonEditorOld(gedcom);

        Component headerPanel = createHeaderPanel(gedcom);
        
		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(headerPanel);
        leftPanel.add(personBox);
		leftPanel.add(personEditor);
        JScrollPane scrollPane = new JScrollPane(leftPanel);
        scrollPane.setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
        pdfPanel = new PagePanel();
        pdfPanel.setOpaque(true);
        pdfPanel.setBackground(Color.WHITE);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, pdfPanel);
		splitPane.setOpaque(false);
		splitPane.setDoubleBuffered(true);
		splitPane.setFocusable(false);
		
		this.add(splitPane);
        
//		this.setPreferredSize(new Dimension(600, 700));
		pack();

        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        splitPane.setResizeWeight(1);
        
        addWindowListener(new WindowAdapter(
                )
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                // TBD Auto-generated method stub
                System.out.println("Save and exit...");
                try
                {
                    gedcom.save(new File("/home/johannes/Downloads/test.ged"));
                    System.out.print("saved...");
                } catch (IOException e1)
                {
                    System.out.print("failed with exception:\n");
                    e1.printStackTrace();
                }
            }
        });
        
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private static Component createHeaderPanel(NGedcom gedcom) {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		
		panel.setBorder(BorderFactory.createTitledBorder("Filinformation"));
		panel.setLayout(new GridLayout(0, 2));
		Header header = gedcom.getHeader();
		
		panel.add(new JLabel("Fil:"));
		panel.add(new JLabel(header.getFile()));
		
		panel.add(new JLabel("Gedcom version:"));
		panel.add(new JLabel(header.getGedcomVersion().getVersion()));

		panel.add(new JLabel("Skapad av"));
		panel.add(new JLabel(gedcom.getSubmitter().getName()));

		panel.add(new JLabel("den "));
		panel.add(new JLabel(header.getDateTime()
		        .getValue()));

		panel.add(new JLabel("Generator:"));
		panel.add(new JLabel(header.getGenerator().getName()));

		panel.add(new JLabel("Submitter:"));
		String str = header.getSubmitter() == null ? "?" : header.getSubmitter().getName();
		panel.add(new JLabel(str));

		panel.add(new JLabel("Språk:"));
		panel.add(new JLabel(header.getLanguage()));

		panel.add(new JLabel("Character set:"));
		panel.add(new JLabel(header.getCharacterSet().getValue()));
		
		return panel;
	}
	
	private void updatePdf(NPerson person)
	{
	    pdfPanel.showPage(PersonalFile.getPersonalFile(gedcom, person));
	}

    public void personSelected(NPerson person)
    {
        updatePdf(person);
        personEditor.setPerson(person);
    }

}
