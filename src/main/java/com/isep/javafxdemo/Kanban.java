package com.isep.javafxdemo;

import java.util.ArrayList;

public class Kanban {
    private static ArrayList<Tache> TachesAFaire;
    private static ArrayList<Tache> TachesEnCours;
    private static ArrayList<Tache> TachesTermine;

    public static void setKanbanList() {
        if (TachesAFaire == null) {
            TachesAFaire = new ArrayList<Tache>();
        }
        if (TachesEnCours == null) {
            TachesEnCours = new ArrayList<Tache>();
        }
        if (TachesTermine == null) {
            TachesTermine = new ArrayList<Tache>();
        }
    }

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
                        for(Tache tEnCours : TachesEnCours){
                            if(tEnCours.getId() == tache.getId()){
                                TachesEnCours.remove(tache);
                            }
                        }
                        for(Tache tTermine : TachesTermine){
                            if(tTermine.getId() == tache.getId()){
                                TachesTermine.remove(tache);
                            }
                        }
                        System.out.println("Tache ajoutee a la liste des taches a faire");
                    }else{
                    System.out.println("Tache deja existante dans la liste des taches a faire");
                    }
                }
                
                break;
            case "en cours":
                for (Tache t : TachesEnCours) {
                    if (t != tache) {
                        TachesEnCours.add(tache);
                        for(Tache tAFaire : TachesAFaire){
                            if(tAFaire.getId() == tache.getId()){
                                TachesAFaire.remove(tache);
                            }
                        }
                        for(Tache tTermine : TachesTermine){
                            if(tTermine.getId() == tache.getId()){
                                TachesTermine.remove(tache);
                            }
                        }
                        System.out.println("Tache ajoutee a la liste des taches en cours");
                    }else{
                    System.out.println("Tache deja existante dans la liste des taches en cours");
                    }
                }
                break;
            case "termine":
                for (Tache t : TachesTermine) {
                    if (t != tache) {
                        TachesTermine.add(tache);
                        for(Tache tAFaire : TachesAFaire){
                            if(tAFaire.getId() == tache.getId()){
                                TachesAFaire.remove(tache);
                            }
                        }
                        for(Tache tEnCours : TachesEnCours){
                            if(tEnCours.getId() == tache.getId()){
                                TachesEnCours.remove(tache);
                            }
                        }
                        System.out.println("Tache ajoutee a la liste des taches termine");
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
        for (Tache t : TachesAFaire) {
            if (t.getId() == tache.getId()) {
                TachesAFaire.remove(tache);
                System.out.println("Tache retiree de la liste des taches a faire");
            }else{
                System.out.println("Tache non existante dans la liste des taches a faire");
            }
        }
        for (Tache t : TachesEnCours) {
            if (t.getId() == tache.getId()) {
                TachesEnCours.remove(tache);
                System.out.println("Tache retiree de la liste des taches en cours");
            }else{
                System.out.println("Tache non existante dans la liste des taches en cours");
            }
        }
        for (Tache t : TachesTermine) {
            if (t.getId() == tache.getId()) {
                TachesTermine.remove(tache);
                System.out.println("Tache retiree de la liste des taches termine");
            }else{
                System.out.println("Tache non existante dans la liste des taches termine");
            }
        }
        for (Projet projet : Projet.getProjets()) {
            for (Tache t : projet.getTaches()) {
                if (t.getId() == tache.getId()) {
                    projet.deleteTache(tache);
                    System.out.println("Tache retiree du projet");
                }else{
                    System.out.println("Tache non existante dans le projet");
                }
            }
        }
    }
    
}
