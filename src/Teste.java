import org.mribeiro.fatores.FatoresNumericos;

import java.util.*;

public class Teste {

    public static void main(String[] args) {
        long numero = 987654321987654321L;
        FatoresNumericos fat = new FatoresNumericos();
        ArrayList<Long> f = fat.getFatores(numero);

        System.out.println(f);
    }

}
