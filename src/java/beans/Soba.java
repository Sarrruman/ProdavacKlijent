package beans;

import java.io.Serializable;

public class Soba implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private long redBr;

    private long brOsoba;

    private String opis;

    private Apartman apartman;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getRedBr() {
        return redBr;
    }

    public void setRedBr(long redBr) {
        this.redBr = redBr;
    }

    public long getBrOsoba() {
        return brOsoba;
    }

    public void setBrOsoba(long brOsoba) {
        this.brOsoba = brOsoba;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Apartman getApartman() {
        return apartman;
    }

    public void setApartman(Apartman apartman) {
        this.apartman = apartman;
    }

}
