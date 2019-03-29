package br.org.ajg.utildev.example.threadpool.matrixMultiplier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import br.org.ajg.utildev.example.threadpool.Chronometer;

public class ExecutorTarefa {

    public static final boolean LOG_LIGADO = false;

    public static int[][] A = null;
    public static int[][] B = null;
    public static int[][] C = null;

    static int numeroTemposMedia = 20;
    static long[] tempos = new long[numeroTemposMedia];
        
    static int qtdeThreads = 2;
    
    public int i;
    public int j;

    public ExecutorTarefa() {
    }

    public static void main(String[] args) {

        int dimensaoMatriz = MultiplicacaoUtil.lerDimensaoDaMatriz();
        int qtdeMaximaThreads = 20;
                
        
        for (qtdeThreads = 1; qtdeThreads <= qtdeMaximaThreads; qtdeThreads++) {

            tempos = new long[numeroTemposMedia];
            
            for (int i = 0; i < numeroTemposMedia; i++) {

                try {
                    A = MultiplicacaoUtil.criarMatrizQuadradaComDimensaoEscolhida(A, dimensaoMatriz);
                    B = MultiplicacaoUtil.criarMatrizQuadradaComDimensaoEscolhida(B, dimensaoMatriz);
                    A = MultiplicacaoUtil.preencherMatrizQuadradaComValor(A, 2);
                    B = MultiplicacaoUtil.preencherMatrizQuadradaComValor(B, 1);
                    C = new int[dimensaoMatriz][dimensaoMatriz];

                    Chronometer cron = new Chronometer();
                    
                    cron.start();
                    C = multiplicaMatrizesParalelo_COM_Pool(A, B, C, 9);
                    cron.end();

                    // MultiplicacaoUtil.imprimeMatriz(C);
                    tempos[i] = cron.getTempoGasto();

                } catch (Exception e) {
                    // TODO: handle exception
                }
                
            }
            
            EstatisticasUtil ce = new EstatisticasUtil(tempos);
            System.out.println(qtdeThreads + "; " + ce.calcularMediaSemOutliers());
        }
    }

    private static int[][] multiplicaMatrizesSequencial(int[][] A, int[][] B, int[][] C) throws InterruptedException {

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                for (int k = 0; k < B.length; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }

    private static int[][] multiplicaMatrizesParalelo_COM_Pool(int[][] A, int[][] B, int[][] C, int qtdeThreads) throws InterruptedException {

    	MultiplicacaoUtil.criarMatrizQuadradaComDimensaoEscolhida(C, A.length);

        ExecutorService poolDeThreads = Executors.newFixedThreadPool(qtdeThreads);

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                Runnable calculoDaPosicao = new Multiplica(i, j, A, B, C);
                poolDeThreads.submit(calculoDaPosicao);
            }
        }

        bloquearRecebimentoDeNovasTarefasEIniciarProcessoDeDesligamento(poolDeThreads);
        aguardarTerminoDaExecucao(poolDeThreads);

        return C;

    }

    private static void aguardarTerminoDaExecucao(ExecutorService poolDeThreads) throws InterruptedException {
        poolDeThreads.awaitTermination(60, TimeUnit.SECONDS);
    }

    private static void bloquearRecebimentoDeNovasTarefasEIniciarProcessoDeDesligamento(ExecutorService poolDeThreads) {
        poolDeThreads.shutdown();
    }

    private static void imprimirEstatisticas(long[] tempos) {

        EstatisticasUtil ce = new EstatisticasUtil(tempos);

        System.out.println("Media: " + ce.calcularMediaSemOutliers());

    }
}