import java.util.Random;

public class HashRehash {

    public static void main(String[] args) {
        final int TAMANHO = 100;
        final int QUANTIDADE = 100;
        final int LIMITE = 100000;
        final int OCUPACAO_MAXIMA = 90;
        final int VAZIO = -1;

        Random random = new Random(1090);
        int[] tabela = new int[TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            tabela[i] = VAZIO;
        }

        int ocupados = 0;
        int rejeitados = 0;
        int totalColisoes = 0;

        int valor1 = -1;
        int colisoes1 = -1;
        int valor2 = -1;
        int colisoes2 = -1;
        int valor3 = -1;
        int colisoes3 = -1;

        for (int i = 0; i < QUANTIDADE; i++) {
            int valor = random.nextInt(LIMITE + 1);

            if (ocupados * 100 >= OCUPACAO_MAXIMA * TAMANHO) {
                rejeitados++;
            } else {
                int posicao = valor % TAMANHO;
                int colisoes = 0;

                while (tabela[posicao] != VAZIO) {
                    colisoes++;
                    posicao = (posicao + 1) % TAMANHO;
                }

                tabela[posicao] = valor;
                ocupados++;
                totalColisoes += colisoes;

                if (colisoes > colisoes1) {
                    valor3 = valor2;
                    colisoes3 = colisoes2;
                    valor2 = valor1;
                    colisoes2 = colisoes1;
                    valor1 = valor;
                    colisoes1 = colisoes;
                } else if (colisoes > colisoes2) {
                    valor3 = valor2;
                    colisoes3 = colisoes2;
                    valor2 = valor;
                    colisoes2 = colisoes;
                } else if (colisoes > colisoes3) {
                    valor3 = valor;
                    colisoes3 = colisoes;
                }
            }
        }

        System.out.println("RESULTADOS");
        System.out.println();
        System.out.println("Elementos inseridos: " + ocupados);
        System.out.println("Elementos rejeitados (ocupação de " + OCUPACAO_MAXIMA + "% atingida): " + rejeitados);
        System.out.println("Total de colisões na inserção: " + totalColisoes);

        System.out.println();
        System.out.println("Três elementos que mais colidiram:");
        System.out.println("1º: " + valor1 + " (" + colisoes1 + " colisões)");
        System.out.println("2º: " + valor2 + " (" + colisoes2 + " colisões)");
        System.out.println("3º: " + valor3 + " (" + colisoes3 + " colisões)");

        System.out.println();
        int livres = 0;
        System.out.print("Posições livres: ");
        for (int i = 0; i < TAMANHO; i++) {
            if (tabela[i] == VAZIO) {
                if (livres > 0) {
                    System.out.print(", ");
                }
                System.out.print(i);
                livres++;
            }
        }
        System.out.println();
        System.out.println("Quantidade de posições livres: " + livres);

        System.out.println();
        System.out.println("===== TESTE DE BUSCA =====");
        buscar(tabela, valor1, VAZIO);
        buscar(tabela, valor2, VAZIO);
        buscar(tabela, valor3, VAZIO);
    }

    static void buscar(int[] tabela, int valor, int vazio) {
        int tamanho = tabela.length;
        int posicao = valor % tamanho;
        int comparacoes = 0;
        int encontrada = -1;

        while (comparacoes < tamanho && tabela[posicao] != vazio && encontrada == -1) {
            comparacoes++;
            if (tabela[posicao] == valor) {
                encontrada = posicao;
            } else {
                posicao = (posicao + 1) % tamanho;
            }
        }

        if (encontrada != -1) {
            System.out.println("Elemento " + valor + " encontrado na posição " + encontrada
                    + " (posição inicial de hash: " + (valor % tamanho)
                    + ", comparações: " + comparacoes + ")");
        } else {
            System.out.println("Elemento " + valor + " não encontrado (comparações: " + comparacoes + ")");
        }
    }
}
