package com.isep.javafxdemo;

import java.util.ArrayList;

public class Kanban {
    private static ArrayList<Tache> TachesAFaire;
    private static ArrayList<Tache> TachesEnCours;
    private static ArrayList<Tache> TachesTermine;

    public static ArrayList<Tache> getTachesAFaire() {
        return TachesAFaire;
    }

    public static ArrayList<Tache> getTachesEnCours() {
        return TachesEnCours;
    }

    public static ArrayList<Tache> getTachesTermine() {
        return TachesTermine;
    }

    public static ArrayList<Tache> getTaches(){
        ArrayList<Tache> taches = new ArrayList<Tache>();
        taches.addAll(TachesAFaire);
        taches.addAll(TachesEnCours);
        taches.addAll(TachesTermine);
        return taches;
    }

    public static void moveTache(Tache tache) {
        switch (tache.getCategory()) {
            case "a faire":
                for (Tache t : TachesAFaire) {
                    if (t != tache) {
                        TachesAFaire.add(tache);
                        if (TachesEnCours.contains(tache)) {
                            TachesEnCours.remove(tache);
                        }else if (TachesTermine.contains(tache)) {
                            TachesTermine.remove(tache);
                        }
                    }else{
                    System.out.println("Tache deja existante dans la liste des taches a faire");
                    }
                }
                
                break;
            case "en cours":
                for (Tache t : TachesEnCours) {
                    if (t != tache) {
                        TachesEnCours.add(tache);
                        if (TachesAFaire.contains(tache)) {
                            TachesAFaire.remove(tache);
                        }else if (TachesTermine.contains(tache)) {
                            TachesTermine.remove(tache);
                        }
                    }else{
                    System.out.println("Tache deja existante dans la liste des taches en cours");
                    }
                }
                break;
            case "termine":
                for (Tache t : TachesTermine) {
                    if (t != tache) {
                        TachesTermine.add(tache);
                        if (TachesAFaire.contains(tache)) {
                            TachesAFaire.remove(tache);
                        }else if (TachesEnCours.contains(tache)) {
                            TachesEnCours.remove(tache);
                        }
                    }else{
                    System.out.println("Tache deja existante dans la liste des taches termine");
                    }
                }
                break;
            default:
                break;
        }
    }

    public static void removeTache(Tache tache) {
        switch (tache.getCategory()) {
            case "a faire":
                TachesAFaire.remove(tache);
                break;
            case "en cours":
                TachesEnCours.remove(tache);
                break;
            case "termine":
                TachesTermine.remove(tache);
                break;
            default:
                break;
        }
    }
    
}
