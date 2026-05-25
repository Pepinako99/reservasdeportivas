package models;

public class Reserva {

    private int idReserva;
    private int idUsuario;
    private int idInstalacion;
    private String nombreInstalacion;
    private String tipoInstalacion;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private double importe;
    private String estado;

    public Reserva() {}

    public int getIdReserva() {
        return idReserva;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdInstalacion() {
        return idInstalacion;
    }

    public String getNombreInstalacion() {
        return nombreInstalacion;
    }

    public String getTipoInstalacion() {
        return tipoInstalacion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public double getImporte() {
        return importe;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdInstalacion(int idInstalacion) {
        this.idInstalacion = idInstalacion;
    }

    public void setNombreInstalacion(String nombreInstalacion) {
        this.nombreInstalacion = nombreInstalacion;
    }

    public void setTipoInstalacion(String tipoInstalacion) {
        this.tipoInstalacion = tipoInstalacion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}