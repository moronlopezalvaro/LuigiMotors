package taller.app.model;

public class Cliente {

    private int id_cliente;
    private String dni;
    private String nombre;
    private String telefono;
    private String contrasenya;
    private String rol;

    public Cliente(int id_cliente, String dni, String nombre, String telefono, String contrasenya, String rol) {
        this.id_cliente = id_cliente;
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.contrasenya = contrasenya;
        this.rol = rol;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id_cliente=" + id_cliente + ", dni=" + dni + ", nombre=" + nombre + ", telefono="
                + telefono + ", contrasenya=" + contrasenya + ", rol=" + rol + "}";
    }
}
