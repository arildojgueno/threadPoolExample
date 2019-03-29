package br.org.ajg.utildev.example.threadpool;

public class Chronometer {
	
    private long tempoInicial = 0;
    private long tempoFinal = 0;
    
    public Chronometer() {
		super();
	}

	public void start(){
        clear();
        this.tempoInicial = System.currentTimeMillis();
    }
    
    public void end(){
    	this.tempoFinal = System.currentTimeMillis();
    }
    
    public void clear(){
    	this.tempoInicial = 0;
    	this.tempoFinal = 0;
    }
    
    public void display(){
        double tempoTotal = calculaTempoGasto();
        
        System.out.println("Tempo: " + tempoTotal + " ms");
    }

    private long calculaTempoGasto() {
        return tempoFinal - tempoInicial;
    }
    
    public long getTempoGasto(){
        return calculaTempoGasto();
    }
}
