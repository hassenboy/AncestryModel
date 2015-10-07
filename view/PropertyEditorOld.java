package view;

import java.awt.BorderLayout;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

public class PropertyEditorOld extends JTextArea
{

    private Supplier<String> getter;
    private Consumer<String> setter;
    
    public PropertyEditorOld(Supplier<String> getter, Consumer<String> setter)
    {
        this.setter = setter;
        this.getter = getter;
        
        setText(getter.get());
//        textComp.setBorder(BorderFactory.createEmptyBorder());
        setBorder(BorderFactory.createEtchedBorder());
        
//        this.add(textComp, BorderLayout.CENTER);
        setOpaque(false);
        
        getDocument().addDocumentListener(new DocumentListener()
        {
            
            @Override
            public void removeUpdate(DocumentEvent e)
            {
                setter.accept(getText());
            }
            
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                setter.accept(getText());
            }
            
            @Override
            public void changedUpdate(DocumentEvent e)
            {
            }
        });
    }
    
}
