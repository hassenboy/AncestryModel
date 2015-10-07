package view;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class PropertyEditor
{

    private TextInputControl textInputControl;
    private Supplier<String> getter;
    private Consumer<String> setter;
    private ChangeListener<String> changeListener;
    
    private PropertyEditor(TextInputControl textInputControl, Supplier<String> getter, Consumer<String> setter, ChangeListener<String> listener)
    {
        this.textInputControl = textInputControl;
        this.setter = setter;
        this.getter = getter;
        this.changeListener = listener;
        
        textInputControl.setText(getter.get());
        
        textInputControl.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                setter.accept(newValue);
            }
        });
        
        if(changeListener != null)
        {
            textInputControl.textProperty().addListener(changeListener);
        }
    }
    
    public TextInputControl getNode()
    {
        return textInputControl;
    }

    public static PropertyEditor createArea(Supplier<String> getter, Consumer<String> setter, ChangeListener<String> listener)
    {
        return new PropertyEditor(new TextArea(), getter, setter, listener);
    }

    public static PropertyEditor createField(Supplier<String> getter, Consumer<String> setter, ChangeListener<String> listener)
    {
        return new PropertyEditor(new TextField(), getter, setter, listener);
    }
    
}
