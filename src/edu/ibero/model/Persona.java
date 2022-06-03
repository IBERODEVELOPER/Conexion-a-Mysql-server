package edu.ibero.model;
/**
 *
 * @author Ibero
 */
public class Persona {
    private int id;
    private String name;
    private String mail;
    private String telefono;

    public Persona() {
    }

    public Persona(String name, String mail, String telefono) {
        this.name = name;
        this.mail = mail;
        this.telefono = telefono;
    }

    public Persona(int id, String name, String mail, String telefono) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
    
}
