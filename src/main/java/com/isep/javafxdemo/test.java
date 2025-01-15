package com.isep.javafxdemo;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        Kanban.setKanbanList();
        /* 
        Projet projet = new Projet(1, "projet1", "2025-01-01 00:00:00", 10000);
        System.out.println("projet created: " + projet);
        Tache tache = new Tache(1, "tache1","2025-01-01 00:00:00", 1000, 0 , 1, "a faire", "description1", "commentaire1");
        System.out.println("tache created: " + tache);
        projet.addTache(tache);
        for (Tache tache1 : projet.getTaches()) {
            System.out.println("retirer tache: " + tache1);
        }
        Employe employe = new Employe(1, "employe1", "manager");
        tache.addMembre(employe);
        projet.addMembre(employe); // Test Projet addMembre, normallement il faut ajouter un employe dans le projet quand on ajoute un employe dans sa tache
        for (Employe em : projet.getMembresProjet()) {
            System.out.println("retirer employe du Projet: " + em);
        }
        for (Employe em : tache.getMembresTache()) {
            System.out.println("retirer employe de la Tache: " + em);
        }
        for (Employe em : Employe.getEmployes()) {
            System.out.println("Employe: " + em.getNom());
        }

        // Test DataHandler saveData
        DataHandler.saveData();*/

        /* 
        // Test DataHandler loadData
        DataHandler.loadData();
        System.out.println("Employes: " + Employe.getEmployes());
        System.out.println("Projets: " + Projet.getProjets());
        System.out.println("Taches: " + Kanban.getTaches());
        for (Projet projet : Projet.getProjets()) {
            System.out.println("Projet: " + projet.getNom());
            for ( Employe em: projet.getMembresProjet()) {
                System.out.println("Employe: " + em.getNom());
            }
        }
        for (Tache tache : Kanban.getTaches()) {
            System.out.println("Tache: " + tache.getNom());
            for ( Employe em: tache.getMembresTache()) {
                System.out.println("Employe: " + em.getNom());
            }
        }*/

        /*
        DataHandler.loadData();
        for(Employe employe : Employe.getEmployes()){
            employe.setNom("sadkhgask");
            employe.setRole("manager");
            employe.setId(114514);
            System.out.println("Employe: " + employe.getNom());
        }
        DataHandler.saveData(); */

    }
}