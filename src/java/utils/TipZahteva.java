package utils;

public enum TipZahteva {
    LOGIN_KUPAC, LOGIN_PRODAVAC, LOGOUT, IZMENI_PODATKE_KUPAC, IZMENI_PODATKE_PRODAVAC,
    UNOS_APARTMANA, IZMENA_APARTMANA, BRISANJE_APARTMANA, DOHVATANJE_APARTMANA_ZA_PRODAVCA;

    public static TipZahteva fromInteger(int x) {
        switch (x) {
            case 0:
                return LOGIN_KUPAC;
            case 1:
                return LOGIN_PRODAVAC;
            case 2:
                return LOGOUT;
            case 3:
                return IZMENI_PODATKE_KUPAC;
            case 4:
                return IZMENI_PODATKE_PRODAVAC;
            case 5:
                return UNOS_APARTMANA;
            case 6:
                return IZMENA_APARTMANA;
            case 7:
                return BRISANJE_APARTMANA;
            case 8:
                return DOHVATANJE_APARTMANA_ZA_PRODAVCA;

        }
        return null;
    }
}
