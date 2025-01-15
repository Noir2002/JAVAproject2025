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

    public Tache(int id, String nom, String dateLimit, double budget, double realCost, int priority, String category, String descriptions, String commentaires) {
        Kanban.setKanbanList();
        int i = 0;
        for (Tache tache : Kanban.getTaches()) {
            if (id != tache.getId()) {
                i++;
            }
        }
        if (i == Kanban.getTaches().size()) {
            this.id = id;
            this.nom = nom;
            this.dateLimit = dateLimit;
            this.budget = budget;
            this.realCost = realCost;
            this.priority = priority;
            this.category = category;
            this.membresTache = new ArrayList<Employe>();
            this.descriptions = descriptions;
            this.commentaires = commentaires;
            // add tache to Kanban
            Kanban.moveTache(this);
            System.out.println("add tache to Kanban");
            i = 1;
        }else {
        System.out.println("Tache deja existante");
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
        int i = 0;
        for (Tache tache : Kanban.getTaches()) {
            if (id != tache.getId()) {
                i++;
            }
        }
        if (i == Kanban.getTaches().size()) {
            // update Kanban
            for (Tache tache : Kanban.getTaches()) {
                if (tache == this) {
                    tache.id = id;
                }
            }
            // update Projet
            for (Projet projet : Projet.getProjets()) {
                if (projet.getTaches().contains(this)) {
                    projet.deleteTache(this);
                    this.id = id;
                    projet.addTache(this);
                    break;
                }
            }
        }else {
        System.out.println("Id deja existant");
        }
    }

    public void setNom(String nom) {
        // update Kanban
        for (Tache tache : Kanban.getTaches()) {
            if (tache == this) {
                tache.nom = nom;
            }
        }
        // update Projet
        for (Projet projet : Projet.getProjets()) {
            if (projet.getTaches().contains(this)) {
                projet.deleteTache(this);
                this.nom = nom;
                projet.addTache(this);
                break;
            }
        }
    }

    public void setDateLimit(String dateLimit) {
        // update Kanban
        for (Tache tache : Kanban.getTaches()) {
            if (tache == this) {
                tache.dateLimit = dateLimit;
            }
        }
        // update Projet
        for (Projet projet : Projet.getProjets()) {
            if (projet.getTaches().contains(this)) {
                projet.deleteTache(this);
                this.dateLimit = dateLimit;
                projet.addTache(this);
                break;
            }
        }
    }

    public void setBudget(double budget) {
        // update Kanban
        for (Tache tache : Kanban.getTaches()) {
            if (tache == this) {
                tache.budget = budget;
            }
        }
        // update Projet
        for (Projet projet : Projet.getProjets()) {
            if (projet.getTaches().contains(this)) {
                projet.deleteTache(this);
                this.budget = budget;
                projet.addTache(this);
                break;
            }
        }
    }

    public void setRealCost(double realCost) {
        // update Kanban
        for (Tache tache : Kanban.getTaches()) {
            if (tache == this) {
                tache.realCost = realCost;
            }
        }
        // update Projet
        for (Projet projet : Projet.getProjets()) {
            if (projet.getTaches().contains(this)) {
                projet.deleteTache(this);
                this.realCost = realCost;
                projet.addTache(this);
                break;
            }
        }
    }

    public void setPriority(int priority) {
        // update Kanban
        for (Tache tache : Kanban.getTaches()) {
            if (tache == this) {
                tache.priority = priority;
            }
        }
        // update Projet
        for (Projet projet : Projet.getProjets()) {
            if (projet.getTaches().contains(this)) {
                projet.deleteTache(this);
                this.priority = priority;
                projet.addTache(this);
                break;
            }
        }
    }

    public void setCategory(String category) {
        // verifier si category est valide
        if (!category.equals("a faire") && !category.equals("en cours") && !category.equals("termine")) {
            System.out.println("Category invalide, veuillez choisir entre 'a faire', 'en cours' et 'termine'");
        }else{
            // update Kanban
            this.category = category;
            Kanban.moveTache(this);
            // update Projet
            for (Projet projet : Projet.getProjets()) {
                if (projet.getTaches().contains(this)) {
                    projet.deleteTache(this);
                    projet.addTache(this);
                    break;
                }
            }
        }
    }

    public void setDescriptions(String descriptions) {
        // update Kanban
        for (Tache tache : Kanban.getTaches()) {
            if (tache.getId() == this.getId()) {
                tache.descriptions = descriptions;
            }
        }
        // update Projet
        for (Projet projet : Projet.getProjets()) {
            if (projet.getTaches().contains(this)) {
                projet.deleteTache(this);
                this.descriptions = descriptions;
                projet.addTache(this);
                break;
            }
        }
    }

    public void setCommentaires(String commentaires) {
        // update Kanban
        for (Tache tache : Kanban.getTaches()) {
            if (tache.getId() == this.getId()) {
                tache.commentaires = commentaires;
            }
        }
        // update Projet
        for (Projet projet : Projet.getProjets()) {
            if (projet.getTaches().contains(this)) {
                projet.deleteTache(this);
                this.commentaires = commentaires;
                projet.addTache(this);
                break;
            }
        }
    }

    public void addMembre(Employe employe) {
        if (membresTache.isEmpty()) {
            membresTache.add(employe);
            // tache add member then projet add member too
            for (Projet projet : Projet.getProjets()) {
                for (Tache t : projet.getTaches()) {
                    if (t.getId() == this.getId()) {
                        projet.addMembre(employe);
                    }
                }
            }
        }
        for (Employe e : membresTache) {
            if (e.getId() != employe.getId())  {
                membresTache.add(employe);
                // tache add member then projet add member too
                for (Projet projet : Projet.getProjets()) {
                    for (Tache t : projet.getTaches()) {
                        if (t.getId() == this.getId()) {
                            projet.addMembre(employe);
                        }
                    }
                }
            }else{
                System.out.println("Employe deja membre de la tache");
            }
        }
    }

    public void deletMembre(Employe employe) {
        for (Employe e : membresTache) {
            if (e.getId() == employe.getId()) {
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
        for (Tache t : projet.getTaches()) {
            if (t != this) {
                projet.addTache(this);
                // add tache to Kanban
                Kanban.moveTache(this);
            }else{
                System.out.println("Tache deja ajoutee au projet");
            }
        }      
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
