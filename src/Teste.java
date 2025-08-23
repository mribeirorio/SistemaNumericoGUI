import org.mribeiro.fatores.FatoresNumericos;

import java.util.*;

public class Teste {

    public static void main(String[] args) {
        long numero = 98765432198L;
        FatoresNumericos fat = new FatoresNumericos();
        ArrayList<Long> f = fat.getFatores(numero);

        System.out.println(f);
    }

}
