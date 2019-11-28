import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws IOException {

        Trocas trocas = new Trocas();
        Scanner read = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("┌────────────────────────────────────────┐");
            System.out.println("│           ESCOLHA UMA OPÇÃO            │");
            System.out.println("├────────────────────────────────────────┤");
            System.out.println("│  1 - FIFO                              │");
            System.out.println("│  2 - Menor Quantidade Sobrando         │");
            System.out.println("│  3 - Relogio                           │");
            System.out.println("├────────────────────────────────────────┤");
            System.out.println("│  4 - Sair                              │");
            System.out.println("└────────────────────────────────────────┘");

            System.out.print("\n│ OPÇÃO: ");
            opcao = read.nextInt();

            switch (opcao) {
                case 1:
                    trocas.trocaPorFIFO();
                    break;
                case 2:
                    trocas.trocaPorMenorQuantidade();
                    break;
                case 3:
                    trocas.trocaPorRelogio();
                    break;
            }
        }
    }
}
