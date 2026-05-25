package models;

public class Instalacion {

    private int idInstalacion;
    private String nombre;
    private String tipo;
    private String descripcion;
    private double precioPorHora;

    public Instalacion() {}

    public Instalacion(int idInstalacion, String nombre, String tipo, String descripcion, double precioPorHora) {
        this.idInstalacion = idInstalacion;
        this.nombre        = nombre;
        this.tipo          = tipo;
        this.descripcion   = descripcion;
        this.precioPorHora = precioPorHora;
    }

    public int getIdInstalacion() {
        return idInstalacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioPorHora() {
        return precioPorHora;
    }

    public void setIdInstalacion(int idInstalacion) {
        this.idInstalacion = idInstalacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioPorHora(double precioPorHora) {
        this.precioPorHora = precioPorHora;
    }

    public static double getPrecioPorTipo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "padel":
                return 15.0;
            case "futsal":
                return 20.0;
            case "futbol11":
                return 25.0;
            case "futbol7":
                return 25.0;
            case "baloncesto":
                return 13.0;
            default:
                return 15.0;
        }
    }
}