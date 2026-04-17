package taller.app.model;

public class Reparacion {

    private int id_reparacion;
    private String matricula;
    private String descripcion;
    private double coste;
    private String fecha_ingreso;
    private String estado;
    private int id_cliente;

    public Reparacion(int id_reparacion, String matricula, String descripcion, double coste, String fecha_ingreso,
            String estado, int id_cliente) {
        this.id_reparacion = id_reparacion;
        this.matricula = matricula;
        this.descripcion = descripcion;
        this.coste = coste;
        this.fecha_ingreso = fecha_ingreso;
        this.estado = estado;
        this.id_cliente = id_cliente;
    }

    public int getId_reparacion() {
        return id_reparacion;
    }

    public void setId_reparacion(int id_reparacion) {
        this.id_reparacion = id_reparacion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public String toString() {
        return "Reparacion{" + "id_reparacion=" + id_reparacion + ", matricula=" + matricula + ", descripcion="
                + descripcion + ", coste=" + coste + ", fecha_ingreso=" + fecha_ingreso + ", estado=" + estado
                + ", id_cliente=" + id_cliente + "}";
    }
}
