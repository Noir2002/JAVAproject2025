package com.isep.javafxdemo;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        Kanban.setKanbanList();
        Projet projet = new Projet(1, "projet1", "2025-01-01 00:00:00", 10000);
        System.out.println("projet created: " + projet);
        Tache tache = new Tache(1, "tache1","2025-01-01 00:00:00", 1000, 0 , 1, "a faire", "description1", "commentaire1");
        System.out.println("tache created: " + tache);
        projet.addTache(tache);
        for (Tache tache1 : projet.getTaches()) {
            System.out.println("retirer tache: " + tache1);
        }

        // Test DataHandler
        DataHandler.saveData();

        //DataHandler.loadData();

    }
}