public class FormatoNaoDecimal {

    public String formatar(int digPorClasse, String strNum) {
        String originalString = strNum;
        int groupSize = digPorClasse;
        char separator = '.';

        StringBuilder formattedString = new StringBuilder(strNum).reverse();
        for (int i = 0; i < originalString.length(); i++) {
            formattedString.append(originalString.charAt(i));
            if ((i + 1) % groupSize == 0 && (i + 1) != originalString.length()) {
                formattedString.append(separator);
            }
        }
        return(formattedString.reverse().toString()); // Output: ABC-DEF-GHIJ
    }
}