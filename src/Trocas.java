import java.io.*;
import java.util.*;

public class Trocas {

    Produtos produtos = new Produtos();
    List<String> pedidos = new ArrayList<String>();
    Scanner read = new Scanner(System.in);

    public Trocas() throws IOException {
    }

    public void lerPedidos() throws IOException {
        InputStream is = new FileInputStream("Pedidos.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s = br.readLine();

        while (s != null) {
            pedidos.add(s);
            s = br.readLine();
        }

        br.close();
    }

    List<String> idsNasPrateleiras = new ArrayList<String>(10);
    List<Integer> qtdesNasPrateleiras = new ArrayList<Integer>(10);

    public void trocaPorFIFO() throws IOException {
        int i = 0, contadorTrocas = 0, contadorReposição = 0;
        produtos.lerProdutos();
        lerPedidos();
        int[] qtdes = produtos.setQtdeNaPrateleira();

        // enche as prateleiras com os 10 produtos, já conferindo se existem e atualizando a quantidade
        while (pedidos.size() > 0) {
            while (idsNasPrateleiras.size() < 10) {
                if (idsNasPrateleiras.contains(pedidos.get(0))) {   // verifica se existe
                    int index = procuraItem(idsNasPrateleiras, pedidos.get(0));
                    int qtde = qtdesNasPrateleiras.get(index);
                    if (qtde - 1 == 0) {
                        // se esse for o ultimo produto na prateleira, a quantidade é redefinida
                        // como o dono tivesse ido buscar mais produtos no armazém
                        qtdesNasPrateleiras.set(index, qtdes[Integer.parseInt(pedidos.get(0)) - 1]);
                        contadorReposição++;
                    } else {
                        // se tiver mais de um item, somente retira um da quantidade
                        qtdesNasPrateleiras.set(index, qtde - 1);
                    }
                    pedidos.remove(0);
                } else {
                    // adiciona o novo pedido na prateleira
                    idsNasPrateleiras.add(pedidos.get(0));
                    qtdesNasPrateleiras.add(qtdes[Integer.parseInt(pedidos.get(0)) - 1] - 1);
                    pedidos.remove(0);
                }
            }

            // somente chega aqui quando as molduras já foram totalmente preenchidas
            // se o produto já está na prateleira
            if (idsNasPrateleiras.contains(pedidos.get(0))) {
                int index = procuraItem(idsNasPrateleiras, pedidos.get(0));
                int qtde = qtdesNasPrateleiras.get(index);
                if (qtde - 1 == 0) {
                    // se esse for o ultimo produto na prateleira, a quantidade é redefinida
                    // como o dono tivesse ido buscar mais produtos no armazém
                    qtdesNasPrateleiras.set(index, qtdes[Integer.parseInt(pedidos.get(0)) - 1]);
                    contadorReposição++;
                } else {
                    // se tiver mais de um item, somente retira um da quantidade
                    qtdesNasPrateleiras.set(index, qtde - 1);
                }
                pedidos.remove(0);
            } else {
                // substitui o primeiro que foi adicionado pelo novo pedido
                idsNasPrateleiras.remove(0);
                qtdesNasPrateleiras.remove(0);
                idsNasPrateleiras.add(pedidos.get(0));
                qtdesNasPrateleiras.add(qtdes[Integer.parseInt(pedidos.get(0)) - 1] -1 );
                pedidos.remove(0);

                contadorTrocas++;
            }
        }

        System.out.println("\n\nForam feitas " +contadorReposição+ " reposições e " +contadorTrocas+ " trocas");
        System.out.println("Total de idas ao armazém: " +(contadorReposição + contadorTrocas));
        System.out.println("\nPressione ENTER para continuar");
        String x = read.nextLine();
    }


    public int procuraItem(List<String> idsNasPrateleiras, String n) {
        int cont = 0;
        while (cont < idsNasPrateleiras.size()) {
            if (idsNasPrateleiras.get(cont).equals(n)) {
                return cont;
            }
            else { cont++; }
        }

        return -1;
    }

    public void trocaPorMenorQuantidade() throws IOException {
        idsNasPrateleiras.clear();
        qtdesNasPrateleiras.clear();

        lerPedidos();

        int i = 0, contadorTrocas = 0, contadorReposição = 0;
        int[] qtdesMQ = produtos.setQtdeNaPrateleira();

        // enche as prateleiras com os 10 produtos, já conferindo se existem e atualizando a quantidade
        while (pedidos.size() > 0) {
            while (idsNasPrateleiras.size() < 10) {
                if (idsNasPrateleiras.contains(pedidos.get(0))) {   // verifica se existe
                    int index = procuraItem(idsNasPrateleiras, pedidos.get(0));
                    int qtde = qtdesNasPrateleiras.get(index);
                    if (qtde - 1 == 0) {
                        // se esse for o ultimo produto na prateleira, a quantidade é redefinida
                        // como o dono tivesse ido buscar mais produtos no armazém
                        qtdesNasPrateleiras.set(index, qtdesMQ[Integer.parseInt(pedidos.get(0)) - 1]);
                        contadorReposição++;
                    } else {
                        // se tiver mais de um item, somente retira um da quantidade
                        qtdesNasPrateleiras.set(index, qtde - 1);
                    }
                    pedidos.remove(0);
                } else {
                    // adiciona o novo pedido na prateleira
                    idsNasPrateleiras.add(pedidos.get(0));
                    qtdesNasPrateleiras.add(qtdesMQ[Integer.parseInt(pedidos.get(0)) - 1] - 1);
                    pedidos.remove(0);
                }
            }

            // somente chega aqui quando as molduras já foram totalmente preenchidas
            // se o produto já está na prateleira
            if (idsNasPrateleiras.contains(pedidos.get(0))) {
                int index = procuraItem(idsNasPrateleiras, pedidos.get(0));
                int qtde = qtdesNasPrateleiras.get(index);
                if (qtde - 1 == 0) {
                    // se esse for o ultimo produto na prateleira, a quantidade é redefinida
                    // como o dono tivesse ido buscar mais produtos no armazém
                    qtdesNasPrateleiras.set(index, qtdesMQ[Integer.parseInt(pedidos.get(0)) - 1]);
                    contadorReposição++;
                } else {
                    // se tiver mais de um item, somente retira um da quantidade
                    qtdesNasPrateleiras.set(index, qtde - 1);
                }
                pedidos.remove(0);
            } else {
                // substitui o item com menor quantidade na prateleira pelo novo pedido
                int index = EscolheMenor();
                idsNasPrateleiras.remove(index);
                qtdesNasPrateleiras.remove(index);
                idsNasPrateleiras.add(pedidos.get(0));
                qtdesNasPrateleiras.add(qtdesMQ[Integer.parseInt(pedidos.get(0)) - 1] -1 );
                pedidos.remove(0);

                contadorTrocas++;
            }
        }

        System.out.println("\n\nForam feitas " +contadorReposição+ " reposições e " +contadorTrocas+ " trocas");
        System.out.println("Total de idas ao armazém: " +(contadorReposição + contadorTrocas));
        System.out.println("\nPressione ENTER para continuar");
        String x = read.nextLine();
    }

    public int EscolheMenor() {
        int aux = qtdesNasPrateleiras.get(0);
        boolean achou = false;
        for (int i = 0; i < qtdesNasPrateleiras.size(); i++) {
            if (aux > qtdesNasPrateleiras.get(i)) {
                aux = qtdesNasPrateleiras.get(i);
                i++;
            }
            else { i++; }
        }

        return aux;
    }

    List<Boolean> bitsRnasPrateleiras = new ArrayList<Boolean>(10);
    public void trocaPorRelogio() throws IOException {
        int epoca = 0;
        int ponteiro = 0;

        idsNasPrateleiras.clear();
        qtdesNasPrateleiras.clear();
        bitsRnasPrateleiras = IniciaListaBits();

        lerPedidos();

        int i = 0, contadorTrocas = 0, contadorReposição = 0;
        int[] qtdesMQ = produtos.setQtdeNaPrateleira();

        // enche as prateleiras com os 10 produtos, já conferindo se existem e atualizando a quantidade
        while (pedidos.size() > 0) {
            while (idsNasPrateleiras.size() < 10) {
                if (epoca == 2){ bitsRnasPrateleiras = LimpaListaBits(); epoca = 0; }
                if (idsNasPrateleiras.contains(pedidos.get(0))) {   // verifica se existe
                    int index = procuraItem(idsNasPrateleiras, pedidos.get(0));
                    int qtde = qtdesNasPrateleiras.get(index);
                    if (qtde - 1 == 0) {
                        // se esse for o ultimo produto na prateleira, a quantidade é redefinida
                        // como o dono tivesse ido buscar mais produtos no armazém
                        qtdesNasPrateleiras.set(index, qtdesMQ[Integer.parseInt(pedidos.get(0)) - 1]);
                        contadorReposição++;
                        bitsRnasPrateleiras.set(index, true);
                    } else {
                        // se tiver mais de um item, somente retira um da quantidade
                        qtdesNasPrateleiras.set(index, qtde - 1);
                        bitsRnasPrateleiras.set(index, true);
                    }
                    pedidos.remove(0);
                } else {
                    // adiciona o novo pedido na prateleira
                    idsNasPrateleiras.add(pedidos.get(0));
                    qtdesNasPrateleiras.add(qtdesMQ[Integer.parseInt(pedidos.get(0)) - 1] - 1);
                    bitsRnasPrateleiras.set(procuraItem(idsNasPrateleiras, pedidos.get(0)), true);
                    pedidos.remove(0);
                }
                epoca++;
            }

            // somente chega aqui quando as molduras já foram totalmente preenchidas
            // se o produto já está na prateleira
            if (epoca == 2){ bitsRnasPrateleiras = LimpaListaBits(); epoca = 0; }
            if (idsNasPrateleiras.contains(pedidos.get(0))) {
                int index = procuraItem(idsNasPrateleiras, pedidos.get(0));
                int qtde = qtdesNasPrateleiras.get(index);
                if (qtde - 1 == 0) {
                    // se esse for o ultimo produto na prateleira, a quantidade é redefinida
                    // como o dono tivesse ido buscar mais produtos no armazém
                    qtdesNasPrateleiras.set(index, qtdesMQ[Integer.parseInt(pedidos.get(0)) - 1]);
                    contadorReposição++;
                    bitsRnasPrateleiras.set(index, true);
                } else {
                    // se tiver mais de um item, somente retira um da quantidade
                    qtdesNasPrateleiras.set(index, qtde - 1);
                    bitsRnasPrateleiras.set(index, true);
                }
                pedidos.remove(0);
            } else {
                // substitui o item que está no ponteiro pelo novo pedido ou o próximo, caso ele tenha a segunda chance
                int index = PegaPonteiro(ponteiro);
                idsNasPrateleiras.remove(index);
                qtdesNasPrateleiras.remove(index);
                idsNasPrateleiras.add(pedidos.get(0));
                qtdesNasPrateleiras.add(qtdesMQ[Integer.parseInt(pedidos.get(0)) - 1] -1 );
                bitsRnasPrateleiras.set(9, true);
                pedidos.remove(0);

                contadorTrocas++;
            }

            epoca++;
        }

        System.out.println("\n\nForam feitas " +contadorReposição+ " reposições e " +contadorTrocas+ " trocas");
        System.out.println("Total de idas ao armazém: " +(contadorReposição + contadorTrocas));
        System.out.println("\nPressione ENTER para continuar");
        String x = read.nextLine();
    }

    public int PegaPonteiro(int ponteiro) {
        boolean achou = false;
        while (!achou) {
            if (ponteiro == 10) { ponteiro = 0; }
            if (bitsRnasPrateleiras.get(ponteiro) == false) {
                achou = true;
                return ponteiro;
            } else {
                bitsRnasPrateleiras.set(ponteiro, false);
                ponteiro++;
            }
        }
        return ponteiro;
    }

    public List<Boolean> IniciaListaBits() {
        for (int i = 0; i < 10; i++) {
            bitsRnasPrateleiras.add(false);
        }
        return bitsRnasPrateleiras;
    }

    public List<Boolean> LimpaListaBits() {
        for (int i = 0; i < 10; i++) {
            bitsRnasPrateleiras.set(i, false);
        }
        return bitsRnasPrateleiras;
    }
}
