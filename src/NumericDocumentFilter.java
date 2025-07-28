import org.mribeiro.constantes.Constantes;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumericDocumentFilter extends DocumentFilter {

    String filtro = "";

    String regex = ""; //""^[a-zA-Z0-9,]+$";

    public NumericDocumentFilter(int base) {
        String algarismos = Constantes.ALGARISMOS_POSSIVEIS.toString();
        if(base<=10) {
            this.filtro = "-?0-" + algarismos.charAt(base-1);
            this.regex  = "^[" + filtro + ",]+$";
        } else {
            this.filtro = "-?a-" + algarismos.charAt(base-1);
            this.regex  = "^["+filtro+filtro.toUpperCase()+"0-9,]+$";
        }
        //System.out.println(">>>>>>> filtro= "+filtro+" regex= "+regex);
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string == null) {
            return;
        }
        if (isValid(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text == null) {
            super.replace(fb, offset, length, text, attrs); // Allow deletion
            return;
        }
        if (isValid(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }

    private boolean isValid(String text) {
        // Define your filtering criteria here
        // Example: Allow only digits
        //return text.matches("\\d*");
        return text.matches(this.regex);
    }
}