package taller.app.model;

public class Cita {
    private int idCita;
    private String fecha;
    private String hora;
    private String matricula;
    private String descripcion;
    private int idCliente;

    public Cita(int idCita, String fecha, String hora, String matricula, String descripcion, int idCliente) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.matricula = matricula;
        this.descripcion = descripcion;
        this.idCliente = idCliente;
    }

    public int getIdCita() {
        return idCita;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    @Override
    public String toString() {
        return fecha + " a las " + hora + " - " + matricula;
    }
}
