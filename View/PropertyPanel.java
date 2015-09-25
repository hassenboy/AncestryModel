package View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PropertyPanel extends JPanel
{
    private int nof_rows = 0;

    public PropertyPanel()
    {
        setLayout(new GridBagLayout());
    }
    
    public void addEditors(List<? extends JComponent> comps, String title)
    {
        if(!comps.isEmpty())
        {
            Iterator<? extends JComponent> it = comps.iterator();
            addEditor(it.next(), title);
            it.forEachRemaining( (c) -> addEditor(c) );
        }
    }
    
    public void addEditor(JComponent editor)
    {
        addEditor(editor, "");
    }
    
    public void addEditor(JComponent editor, String title)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        if (nof_rows == 0)
        {
            gbc.anchor = GridBagConstraints.NORTH;
        }
        gbc.gridy = nof_rows;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;

        // Label
        gbc.gridx = 0;
        gbc.weightx = 0.2;
        JLabel label = new JLabel(title + (title.isEmpty() ? "": ":"), JLabel.NORTH_EAST);
        add(label, gbc);

        // Editor
        gbc.gridx = 1;
        gbc.weightx = 0.8;

        add(editor, gbc);

        nof_rows++;
    }

}
