public class NumberFormatNaoDecimal {

    public String formatar(char separador, int digPorClasse, String strNum) {
        String[] arr = strNum.split(",");

        if(arr[0].length() <= digPorClasse) {
            return strNum;
        }

        boolean possuiDecimal = (arr.length > 1);

        StringBuilder resultado = new StringBuilder(arr[0]).reverse();
        int offset = digPorClasse;
        int quantos = (resultado.length() / digPorClasse) - (resultado.length() % digPorClasse == 0 ? 1 : 0);
        int contador = 0;

        do {
            resultado.insert(offset, separador);
            offset += digPorClasse + 1;
            contador++;
        } while(contador < quantos);

        if(possuiDecimal) {
            return resultado.reverse().toString() + "," + arr[1];
        } else {
            return resultado.reverse().toString();
        }
    }
}