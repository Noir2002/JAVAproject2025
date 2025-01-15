package com.isep.javafxdemo;

import java.io.*;
import java.util.*;

public class DataHandler {

    private static final String EMPLOYES_FILE = "employes.csv";
    private static final String PROJETS_FILE = "projets.csv";
    private static final String TACHES_FILE = "taches.csv";
    private static final String MEMBRES_PROJET_FILE = "membresProjet.csv";
    private static final String MEMBRES_TACHE_FILE = "membresTache.csv";

    // 读取数据
    public static void loadData() throws IOException {
        // 确保文件存在
        ensureFileExists(EMPLOYES_FILE, "id,nom,role");
        ensureFileExists(PROJETS_FILE, "id,nom,dateLimit,budget,realCost");
        ensureFileExists(TACHES_FILE, "id,nom,dateLimit,budget,realCost,priority,category,descriptions,commentaires,idProjet");
        ensureFileExists(MEMBRES_PROJET_FILE, "idEmploye,idProjet");
        ensureFileExists(MEMBRES_TACHE_FILE, "idEmploye,idTache");

        // 读取Employe数据
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYES_FILE))) {
            String line = br.readLine(); // Skip header
            if (line == null) return; // File is empty
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    new Employe(Integer.parseInt(parts[0]), parts[1], parts[2]);
                }
            }
        }

        // 读取Projet数据
        try (BufferedReader br = new BufferedReader(new FileReader(PROJETS_FILE))) {
            String line = br.readLine(); // Skip header
            if (line == null) return; // File is empty
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Projet projet = new Projet(Integer.parseInt(parts[0]), parts[1], parts[2], Double.parseDouble(parts[3]), Double.parseDouble(parts[4]));
                }
            }
        }

        // 读取Tache数据
        try (BufferedReader br = new BufferedReader(new FileReader(TACHES_FILE))) {
            String line = br.readLine(); // Skip header
            if (line == null) return; // File is empty
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 10) {
                    Tache tache = new Tache(Integer.parseInt(parts[0]), parts[1], parts[2], Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]), Integer.parseInt(parts[5]), parts[6], parts[7], parts[8]);
                    Projet projet = Projet.getProjets().stream().filter(p -> p.getId() == Integer.parseInt(parts[9])).findFirst().orElse(null);
                    if (projet != null) {
                        projet.addTache(tache);
                    }
                }
            }
        }

        // 建立membresTache关系
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBRES_TACHE_FILE))) {
            String line = br.readLine(); // Skip header
            if (line == null) return; // File is empty
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    Employe employe = Employe.getEmployes().stream().filter(e -> e.getId() == Integer.parseInt(parts[0])).findFirst().orElse(null);
                    Tache tache = Kanban.getTaches().stream().filter(t -> t.getId() == Integer.parseInt(parts[1])).findFirst().orElse(null);
                    if (employe != null && tache != null) {
                        tache.addMembre(employe);
                    }
                }
            }
        }

        // 建立membresProjet关系
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBRES_PROJET_FILE))) {
            String line = br.readLine(); // Skip header
            if (line == null) return; // File is empty
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    Employe employe = Employe.getEmployes().stream().filter(e -> e.getId() == Integer.parseInt(parts[0])).findFirst().orElse(null);
                    Projet projet = Projet.getProjets().stream().filter(p -> p.getId() == Integer.parseInt(parts[1])).findFirst().orElse(null);
                    if (employe != null && projet != null) {
                        projet.addMembre(employe);
                    }
                }
            }
        }
    }

    // 储存数据
    public static void saveData() throws IOException {
        // 写入Employe数据
        recreateFile(EMPLOYES_FILE, "id,nom,role");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EMPLOYES_FILE, true))) {
            for (Employe e : Employe.getEmployes()) {
                bw.write(e.getId() + "," + e.getNom() + "," + e.getRole());
                bw.newLine();
            }
        }

        // 写入Projet数据
        recreateFile(PROJETS_FILE, "id,nom,dateLimit,budget,realCost");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PROJETS_FILE, true))) {
            for (Projet p : Projet.getProjets()) {
                bw.write(p.getId() + "," + p.getNom() + "," + p.getDateLimit() + "," + p.getBudget() + "," + p.getRealCost());
                bw.newLine();
            }
        }

        // 写入Tache数据
        recreateFile(TACHES_FILE, "id,nom,dateLimit,budget,realCost,priority,category,descriptions,commentaires,idProjet");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TACHES_FILE, true))) {
            for (Tache t : Kanban.getTaches()) {
                Projet projet = Projet.getProjets().stream().filter(p -> p.getTaches().contains(t)).findFirst().orElse(null);
                int projetId = (projet != null) ? projet.getId() : -1;
                bw.write(t.getId() + "," + t.getNom() + "," + t.getDateLimit() + "," + t.getBudget() + "," + t.getRealCost() + "," + t.getPriority() + "," + t.getCategory() + "," + t.getDescriptions() + "," + t.getCommentaires() + "," + projetId);
                bw.newLine();
            }
        }

        // 写入membresTache数据
        recreateFile(MEMBRES_TACHE_FILE, "idEmploye,idTache");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEMBRES_TACHE_FILE, true))) {
            for (Tache t : Kanban.getTaches()) {
                for (Employe e : t.getMembresTache()) {
                    bw.write(e.getId() + "," + t.getId());
                    bw.newLine();
                }
            }
        }

        // 写入membresProjet数据
        recreateFile(MEMBRES_PROJET_FILE, "idEmploye,idProjet");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEMBRES_PROJET_FILE, true))) {
            for (Projet p : Projet.getProjets()) {
                for (Employe e : p.getMembresProjet()) {
                    bw.write(e.getId() + "," + p.getId());
                    bw.newLine();
                }
            }
        }
    }

    // 确保文件存在
    private static void ensureFileExists(String fileName, String header) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(header);
                bw.newLine();
            }
        }
    }

    // 重建文件
    private static void recreateFile(String fileName, String header) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        ensureFileExists(fileName, header);
    }
}
