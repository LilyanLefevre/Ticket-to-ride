package Enum;

import java.util.InputMismatchException;
import java.util.Scanner;

public enum Destination {
    PARIS,
    ANGORA,
    BUDAPEST,
    SOFIA,
    FRANKFURT,
    KOBENHAVN,
    ROSTOV,
    ERZURUM,
    SMYRNA,
    BERLIN,
    ATHINA,
    KYIV,
    ZURICH,
    SMOLENSK,
    BREST,
    WIEN,
    PETROGRAD,
    BRINDISI,
    ZAGREB,
    MARSEILLE,
    LONDON,
    EDINBURGH,
    AMSTERDAM,
    PALERMO,
    CONSTANTINOPLE,
    MADRID,
    BARCELONA,
    BRUXELLES,
    VENZIA,
    ESSEN,
    BUCURESTI,
    DANAIC,
    WILNO,
    STOCKHOLM,
    MOSKVA,
    WARSZAWA,
    ZAGRAB,
    PAMPLONA,
    ROMA,
    SARAJEVO,
    SEVASTOPOL,
    DIEPPE,
    MUNCHEN,
    VENEZIA,
    SOCHI,
    DANZIC,
    KHARKOV,
    RIGA,
    VENIZIA,
    LISBOA,
    CADIZ,
    DANZIG,
    SEVASTAPOL,
    KØBENHAVN,
    MÜNCHEN,
    ZÜRICH;


    /**
     * saisie d'une destination existante
     *
     * @return String une destination existante
     */
    public static Destination saisieDestination(){
        String choix = "";
        Scanner entree =   new Scanner(System.in);

        try{
            choix = entree.next();
        }catch (InputMismatchException e){
            entree.next();
        }
        //verif de la saisie
        int exist = 0;
        for(Destination c : Destination.values()) {
            try {
                if (c.toString().equals(choix)) {
                    exist = 1;
                }
            }catch(IllegalArgumentException e){};
        }
        while(exist != 1) {
            System.out.println("Erreur : cette destination n'existe pas. Veuillez réessayer : ");
            try {
                choix = entree.next();
            } catch (InputMismatchException e) {
                entree.next();
            }
            for(Destination c : Destination.values()) {
                try {
                    if (c.toString().equals(choix)) {
                        exist = 1;
                    }
                }catch(IllegalArgumentException e){};
            }
        }
        return Destination.valueOf(choix);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
