package com.isep.javafxdemo;

import java.util.ArrayList;

public class Projet {
    private int id;
    private String nom;
    private String dateLimit; // format : "yyyy-MM-dd HH:mm:ss"
    private double budget;
    private double realCost;
    private ArrayList<Employe> membresProjet;
    private ArrayList<Tache> taches;

    private static ArrayList<Projet> projets;

    public Projet(int id, String nom, String dateLimit, double budget) {
        setProjets();
        int i = 0;
        for (Projet projet : projets) {
            if (id != projet.getId()) {
                i++;
            }
        }
        if (i == projets.size()) {
            this.id = id;
            this.nom = nom;
            this.dateLimit = dateLimit;
            this.budget = budget;
            this.realCost = 0;
            this.membresProjet = new ArrayList<Employe>();
            this.taches = new ArrayList<Tache>();
            projets.add(this);
            i = 1;
        }else {
        System.out.println("Projet deja existant");
        }
    }

    public static void setProjets(){
        if (projets == null) {
            projets = new ArrayList<Projet>();
        }
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDateLimit() {
        return dateLimit;
    }

    public double getBudget() {
        return budget;
    }

    public double getRealCost() {
        return realCost;
    }

    public ArrayList<Employe> getMembresProjet() {
        return membresProjet;
    }

    public ArrayList<Tache> getTaches() {
        return taches;
    }

    public void setId(int id) {
        int i = 0;
        for (Projet projet : projets) {
            if (id != projet.getId()) {
                i++;
            }
        }
        if (i == projets.size()) {
            this.id = id;
        }else {
        System.out.println("Id deja existant");
        }
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateLimit(String dateLimit) {
        this.dateLimit = dateLimit;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setRealCost(double realCost) {
        this.realCost = realCost;
    }

    public void addMembre(Employe employe) {
        for (Employe e : membresProjet) {
            if (e != employe) {
                membresProjet.add(employe);
            }else{
                System.out.println("Employe deja membre du projet");
            }
        }
    }

    public void deleteMembre(Employe employe) {
        for (Employe e : membresProjet) {
            if (e == employe) {
                membresProjet.remove(employe);
            }else{
                System.out.println("Employe n'est pas un membre du projet");
            }
        }
        
    }

    public void addTache(Tache tache) {
        /*
        for (Tache t : taches) {
            if (t.getId() != tache.getId()) {
                taches.add(tache);
                System.out.println("Tache ajoutee au projet");
            }else{
                System.out.println("Tache deja ajoutee au projet");
            }
        } */
        taches.add(tache);
    }

    public void deleteTache(Tache tache) {
        for (Tache t : taches) {
            if (t == tache) {
                taches.remove(tache);
            }else{
                System.out.println("Tache n'est pas une tache du projet");
            }
        }
    }

    public static void deleteProjet(Projet projet) {
        projets.remove(projet);
    }

    public static ArrayList<Projet> getProjets() {
        return projets;
    }

    @Override
    public String toString() {
        String tachesString = "";
        for (Tache t : taches) {
            tachesString += t.toString() + "\n";
        }

        String membresString = "";
        for (Employe e : membresProjet) {
            membresString += "Employe [id=" + e.getId() + ", nom=" + e.getNom() + "]" + "\n";
        }
        return "Projet [id=" + id + ", nom=" + nom + ", dateLimit=" + dateLimit + ", budget=" + budget + ", realCost=" + realCost + "]"
                + "\nMembres du projet : " + membresString
                + "\nTaches du projet : \n" + tachesString + "\n";
    }

}
