package com.isep.javafxdemo;


import java.util.ArrayList;

public class Employe {
    private int id;
    private String nom;
    private String role;
    private static ArrayList<Employe> employes = new ArrayList<>(0);

    public Employe(int id, String nom, String role) {
        setEmployes();
        if(employes.isEmpty()){
            this.id = id;
            this.nom = nom;
            this.role = role;
            employes.add(this);
        }else {
            int i = 0;
            for (Employe employe : employes) {
                if (id != employe.getId()) {
                    i++;
                }
            }
            if (i == employes.size()) {
                this.id = id;
                this.nom = nom;
                this.role = role;
                employes.add(this);
            } else {
                System.out.println("Employe deja existant");
            }
        }
    }

    public static void setEmployes(){
        if (employes == null) {
            employes = new ArrayList<Employe>();
        }
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        int i = 0;
        for (Employe employe : employes) {
            if (id != employe.getId()) {
                i++;
            }
        }
        if (i == employes.size()) {
            this.id = id;
        }else {
        System.out.println("Id deja existant");
        }
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static ArrayList<Employe> getEmployes() {
        setEmployes();
        return employes;
    }

    public static void deleteEmploye(Employe employe) {
        employes.remove(employe);
    }

    @Override
    public String toString() {
        return  "Employe [id=" + id + ", nom=" + nom + ", role=" + role + "]";
    }
}