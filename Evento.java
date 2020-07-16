package testeeventos;

public abstract class Evento implements Comparable<Evento> {
    private int tempoEvento, idCabine, idVeiculo;

    public Evento(int tempoEvento, int idCabine, int idVeiculo) {
        this.tempoEvento = tempoEvento;
        this.idCabine = idCabine;
        this.idVeiculo = idVeiculo;
    }

    public int getTempoEvento() {
        return tempoEvento;
    }
    
    @Override
    public int compareTo(Evento e) {
        
    }
}
