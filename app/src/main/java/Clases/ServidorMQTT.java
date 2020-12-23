package Clases;

public class ServidorMQTT {
    private String ip;
    private String usuario;
    private String pass;

    public ServidorMQTT(){
        ip = "tcp://134.209.79.119:1883";
        usuario = "usuariomqtt";
        pass = "ohinacio";
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String passwrd) {
        this.pass = passwrd;
    }

}
