package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import model.wrappers.NPerson;

class AutoCompleteBox<E> extends JComboBox<String>
{
    public int        caretPos   = 0;
    public JTextField inputField = null;

    private List<E>             elements;
    private Function<E, String> toStrFunction;
    private Consumer<E>         onSelect;

    public AutoCompleteBox(List<E> elements, Function<E, String> toStrFunction, Consumer<E> onSelect)
    {
        super(new Vector<String>(transform(elements, toStrFunction)));
        this.elements = elements;
        this.toStrFunction = toStrFunction;
        this.onSelect = onSelect;

        setEditor(new BasicComboBoxEditor());
        setEditable(true);

        addItemListener(new ItemListener()
        {

            @Override
            public void itemStateChanged(ItemEvent e)
            {
                onSelect.accept(getSelectedItem());
            }
        });
    }

    public void setToStrFunc(Function<E, String> f)
    {
        this.toStrFunction = f;
    }

    @Override
    public void setSelectedIndex(int index)
    {
        super.setSelectedIndex(index);

        System.out.println("setSelectedIndex(): " + index);

        inputField.setText(getItemAt(index));
        inputField.setSelectionEnd(caretPos + 0);
        inputField.moveCaretPosition(caretPos);
    }

    public E getSelectedItem()
    {
        System.out.println("getSelectedItem()");
        // TBD
        if (getSelectedIndex() >= elements.size() || getSelectedIndex() < 0)
        {
            System.err.println("index out of bounds: " + getSelectedIndex());
            return null;
        }
        return elements.get(getSelectedIndex());
    }

    @Override
    public void setEditor(ComboBoxEditor editor)
    {
        System.out.println("setSelectedElement(ComboBoxEditor editor)");
        super.setEditor(editor);
        if (editor.getEditorComponent() instanceof JTextField)
        {
            inputField = (JTextField) editor.getEditorComponent();

            inputField.addKeyListener(new KeyAdapter()
            {
                public void keyReleased(KeyEvent ev)
                {
                    char key = ev.getKeyChar();
                    if (!(Character.isLetterOrDigit(key) || Character.isSpaceChar(key)))
                        return;

                    caretPos = inputField.getCaretPosition();
                    String text = "";
                    try
                    {
                        text = inputField.getText(0, caretPos);
                    } catch (javax.swing.text.BadLocationException e)
                    {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < getItemCount(); i++)
                    {
                        String element = (String) getItemAt(i);
                        if (element.startsWith(text))
                        {
                            setSelectedIndex(i);
                            return;
                        }
                    }
                }
            });
        }
    }

    public void setSelectedElement(E element)
    {
        System.out.println("setSelectedElement(E element)");
        setSelectedIndex(elements.indexOf(element));
    }

    private static <T1, T2> List<T2> transform(List<T1> listIn, Function<T1, T2> f)
    {
        List<T2> listOut = new Vector<T2>();
        for (T1 element : listIn)
        {
            listOut.add(f.apply(element));
        }
        return listOut;
    }
}