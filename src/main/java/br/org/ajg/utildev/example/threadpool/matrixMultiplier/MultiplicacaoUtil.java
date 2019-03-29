package br.org.ajg.utildev.example.threadpool.matrixMultiplier;

import java.util.Scanner;

public class MultiplicacaoUtil {

    public static int lerDimensaoDaMatriz() {
        Scanner ler = null;
        try {
            ler = new Scanner(System.in);
            System.out.print("Insira a dimensao da matriz quadrada: ");
            int t = ler.nextInt();
            return t;
        } finally {
            ler.close();
        }
    }
    
    public static int lerQuantidadeThreads() {
        Scanner ler = null;
        try {
            ler = new Scanner(System.in);
            System.out.print("Insira a quantidade maxima de threads: ");
            int t = ler.nextInt();
            return t;
        } finally {
            ler.close();
        }
    }

//    public static void instanciarAs3Matrizes(int d, int[][] ... matrizes) {
//        MultiplicacaoSequencialMatrizes ms = new MultiplicacaoSequencialMatrizes();
//        
//        criarMatrizQuadradaComDimensaoEscolhida(matrizes[0], d);
//        criarMatrizQuadradaComDimensaoEscolhida(matrizes[1], d);
//        criarMatrizQuadradaComDimensaoEscolhida(matrizes[2], d);
//        
//    }

    public static int[][] preencherMatrizQuadradaComValor(int[][] M, int valor) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M.length; j++) {
                M[i][j] = valor;
            }
        }
        return M;
    }
    
    public static int[][] preencherDiagonalPrincipalMatrizQuadradaComValor(int[][] M, int valor) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M.length; j++) {
                if(i == j){
                    M[i][j] = valor;                    
                } else {
                    M[i][j] = 0;
                }
            }
        }
        return M;
    }

    public static int[][] criarMatrizQuadradaComDimensaoEscolhida(int[][] M, int dimensao) {
        M = new int[dimensao][dimensao];
        return M;
    }
    
    public static void imprimeMatriz(int[][] matriz) {

        System.out.println();
        
        System.out.print("Resultado: C = ");
        for (int i = 0; i < matriz.length; i++) {            
            System.out.print("| ");
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.print(" |");
            System.out.println();
            System.out.print("               ");
        }
        
    }

}
