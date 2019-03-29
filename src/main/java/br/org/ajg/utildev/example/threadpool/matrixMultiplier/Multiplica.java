package br.org.ajg.utildev.example.threadpool.matrixMultiplier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Multiplica implements Runnable {

    public int i;
    public int j;
    
    int[][] A;
    int[][] B;
    int[][] C;

    public Multiplica() {
    }

    public Multiplica(int i, int j, int[][] A, int[][] B, int[][] C) {
        this.i = i;
        this.j = j;
    }

    private static void aguardarTerminoDaExecucao(ExecutorService poolDeThreads) throws InterruptedException {
        poolDeThreads.awaitTermination(60, TimeUnit.SECONDS);
    }

    private static void bloquearRecebimentoDeNovasTarefasEIniciarProcessoDeDesligamento(ExecutorService poolDeThreads) {
        poolDeThreads.shutdown();
    }

    @Override
    public void run() {
        calculaValorPosicao();
    }

    public void calculaValorPosicao() {

        for (int k = 0; k < B.length; k++) {
            C[i][j] += A[i][k] * B[k][j];
        }

        if (ExecutorTarefa.LOG_LIGADO) {
            System.out.println(Thread.currentThread().getName() + " => C[" + i + "," + j + "] = " + C[i][j]);
        }

    }

    private static void imprimirEstatisticas(long[] tempos) {

        EstatisticasUtil ce = new EstatisticasUtil(tempos);

        System.out.println("Media: " + ce.calcularMediaSemOutliers());

    }
}