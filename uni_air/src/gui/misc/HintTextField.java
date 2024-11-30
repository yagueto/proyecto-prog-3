package gui.misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author playscode - Norman Carcamo.
 */
public class HintTextField extends JTextField implements FocusListener {

    /**
     *
     */
    private static final long serialVersionUID = 1598140334981076482L;

    private final Font fontLost = new Font("Monaco", Font.ITALIC, 10);
    private final Font fontGained = new Font("Monaco", Font.PLAIN, 12);
    private final Color colorLost = Color.LIGHT_GRAY;
    private final Color colorGained = Color.BLACK;
    private String hint;

    public HintTextField(int columns, String hint) {
        super(columns);
        setHint(hint);
        addFocusListener(this);
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        setForeground(colorLost);
        setFont(fontLost);
        setText(hint);
        this.hint = hint;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (getText().equals(getHint())) {
            setText("");
            setFont(fontGained);
            setForeground(colorGained);
        } else {
            setForeground(colorGained);
            setFont(fontGained);
            setText(getText());
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            setHint(getHint());
        } else {
            setForeground(colorGained);
            setFont(fontGained);
            setText(getText());
        }
    }
}