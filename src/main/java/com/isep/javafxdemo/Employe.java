package com.isep.javafxdemo;


import java.util.ArrayList;

public class Employe {
    private int id;
    private String nom;
    private String role;
    private static ArrayList<Employe> employes = new ArrayList<>();

    public Employe(int id, String nom, String role) {
        this.id = id;
        this.nom = nom;
        this.role = role;
        employes.add(this);
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
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static ArrayList<Employe> getEmployes() {
        return employes;
    }

    public static void deleteEmploye(Employe employe) {
        employes.remove(employe);
    }

    @Override
    public String toString() {
        return "Employe [id=" + id + ", nom=" + nom + ", role=" + role + "]";
    }
}