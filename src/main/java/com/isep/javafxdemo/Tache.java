package com.isep.javafxdemo;

import java.util.ArrayList;

public class Tache {
    
    private int id;
    private String nom;
    private String dateLimit; // format : "yyyy-MM-dd HH:mm
    private double budget;
    private double realCost;
    private int priority;
    private String category; // "a faire", "en cours", "termine"
    private ArrayList<Employe> membresTache;
    private String descriptions;
    private String commentaires;

    public Tache(int id, String nom, String dateLimit, double budget, int priority, String descriptions) {
        for (Tache tache : Kanban.getTaches()) {
            if (id != tache.getId()) {
                this.id = id;
                this.nom = nom;
                this.dateLimit = dateLimit;
                this.budget = budget;
                this.realCost = 0;
                this.priority = priority;
                this.category = "a faire";
                this.membresTache = new ArrayList<Employe>();
                this.descriptions = descriptions;
                this.commentaires = "";
                // add tache to Kanban
                Kanban.moveTache(this);
            } else {
                System.out.println("Tache deja existante");
            }
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

    public int getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<Employe> getMembresTache() {
        return membresTache;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCategory(String category) {
        // verifier si category est valide
        if (!category.equals("a faire") && !category.equals("en cours") && !category.equals("termine")) {
            System.out.println("Category invalide, veuillez choisir entre 'a faire', 'en cours' et 'termine'");
        }else{
            // update Kanban
            this.category = category;
            Kanban.moveTache(this);
        }
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public void addMembre(Employe employe) {
        for (Employe e : membresTache) {
            if (e != employe) {
                membresTache.add(employe);
            }else{
                System.out.println("Employe deja membre de la tache");
            }
        }
    }

    public void deletMembre(Employe employe) {
        for (Employe e : membresTache) {
            if (e == employe) {
                membresTache.remove(employe);
            }else{
                System.out.println("Employe n'est pas un membre de la tache");
            }
        }
    }

    public void deleteCommentaire() {
        this.commentaires = "";
    }

    public void deleteDescription() {
        this.descriptions = "";
    }

    public void addTache(Projet projet) {
        // add tache to projet
        projet.addTache(this);

        // add tache to Kanban
        Kanban.moveTache(this);
    }

    public void deleteTache(Tache tache) {
        // delete tache from projet
        for (Projet projet : Projet.getProjets()) {
            for (Tache t : projet.getTaches()) {
                if (t == tache) {
                    projet.deleteTache(tache);
                }
            }
        }

        // delete tache from Kanban
        Kanban.removeTache(tache);
    }

    @Override
    public String toString() {
        return "Tache [id=" + id + ", nom=" + nom + ", dateLimit=" + dateLimit + ", budget=" + budget + ", realCost=" + realCost + ", priority=" + priority + ", category=" + category + ", \ndescriptions=" + descriptions + ", \ncommentaires=" + commentaires + "]";
    }
}
