package br.org.ajg.utildev.example.threadpool.matrixMultiplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EstatisticasUtil {
    private long[] dados;
    private int qtdeAmostras;   

    public EstatisticasUtil(long[] dados)  {
        this.dados = dados;
        qtdeAmostras = dados.length;
    }   
    
    public static ArrayList<Long> convertArray(long[] array) {
          ArrayList<Long> result = new ArrayList<Long>(array.length);
          for (long item : array)
            result.add(item);
          return result;
        }
    
    
    public long max() {
        return Arrays.stream(dados).max().getAsLong();
    }
    
    public long min() {
        return Arrays.stream(dados).min().getAsLong();
    }

    public double calcularMedia()  {
        double sum = 0.0;
        for(double a : dados)
            sum += a;
        return sum/qtdeAmostras;
    }
    
    public double calcularMediaSemOutliers()  {
        double sum = 0.0;
        
        double media = this.calcularMedia();
        double desvioPadrao = this.calcularDesvioPadrao();
        
        for(double a : dados){
            if(a <= (media + desvioPadrao) && a >= (media - desvioPadrao)){
                sum += a;                            
            }
        }
        return sum/qtdeAmostras;
    }

    public double calcularVariancia()  {
        double media = calcularMedia();
        double temp = 0;
        for(double a :dados)
            temp += (a-media)*(a-media);
        return temp/qtdeAmostras;
    }

    public double calcularDesvioPadrao() {
        return Math.sqrt(calcularVariancia());
    }

    public double mediana() {
       Arrays.sort(dados);
       if (dados.length % 2 == 0)  {
          return (dados[(dados.length / 2) - 1] + dados[dados.length / 2]) / 2.0;
       } 
       return dados[dados.length / 2];
    }
}