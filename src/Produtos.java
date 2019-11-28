import java.io.*;
import java.util.*;

public class Produtos {
    public String id;
    public float peso;

    List<Produtos> produtos = new ArrayList<Produtos>(80);

    public Produtos() { }

    public Produtos(String id, float peso) {
        this.id = id;
        this.peso = peso;
    }

    public void lerProdutos() throws IOException {
        produtos.clear();

        InputStream is = new FileInputStream("Produtos.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = br.readLine();

        while (s != null) {
            String[] splitStr = s.split(";");
            if (splitStr.length == 3) {
                Produtos produto = new Produtos (splitStr[0], Float.parseFloat(splitStr[2].replace(",",".")));
                produtos.add(produto);
            }
            s = br.readLine();
        }
        br.close();
    }

    public int[] setQtdeNaPrateleira() throws IOException {
        int[] qtdes = new int[80];
        int i = 0;
        lerProdutos();

        for (Produtos p: produtos) {
            float peso = 0;
            int qtde = 1;

            while (peso < 10) {
                peso = p.peso * (qtde + 2);
                qtde++;
            }

            qtdes[i] = qtde;
            i++;
        }

        return qtdes;
    }
}
