package org.example;

import Gestores.GestorPaciente;
import Gestores.GestorMedico;
import Gestores.GestorFarmaceuta;
import Gestores.GestorMedicamento;

import entidades.Paciente;
import entidades.Medico;
import entidades.Farmaceuta;
import entidades.Medicamento;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // =======================
            // 1) PACIENTES
            // =======================
            GestorPaciente pacientes = new GestorPaciente();

            pacientes.agregarPaciente(new Paciente(1, "Josué", 88887777, LocalDate.of(1995, 5, 20)));
            pacientes.agregarPaciente(new Paciente(2, "Kenny", 89996666, LocalDate.of(1988, 10, 12)));

            pacientes.guardarXML();
            pacientes.cargarXML();

            System.out.println("Pacientes:");
            System.out.println(pacientes);

            // =======================
            // 2) MÉDICOS
            // =======================
            GestorMedico medicos = new GestorMedico();

            medicos.agregarMedico(new Medico("Carlos Ramírez", 1, "Cardiología"));
            medicos.agregarMedico(new Medico("María López", 2, "Pediatría"));

            medicos.guardarXML();
            medicos.cargarXML();

            System.out.println("Médicos:");
            System.out.println(medicos);

            // =======================
            // 3) FARMACEUTAS
            // =======================
            GestorFarmaceuta farmas = new GestorFarmaceuta();

            farmas.agregarFarmaceuta(new Farmaceuta(1, "Laura Sánchez"));
            farmas.agregarFarmaceuta(new Farmaceuta(2, "Pedro Martínez"));

            farmas.guardarXML();
            farmas.cargarXML();

            System.out.println("Farmaceutas:");
            System.out.println(farmas);

            // =======================
            // 4) MEDICAMENTOS
            // =======================
            GestorMedicamento meds = new GestorMedicamento();

            meds.agregarMedicamento(new Medicamento(1001, "Acetaminofén", "Tableta 500 mg", "Analgésico y antipirético"));
            meds.agregarMedicamento(new Medicamento(1002, "Acetaminofén", "Jarabe 100 mg/5 ml", "Uso pediátrico"));
            meds.agregarMedicamento(new Medicamento(2001, "Ibuprofeno", "Tableta 400 mg", "Antiinflamatorio"));

            meds.guardarXML();
            meds.cargarXML();

            System.out.println("Medicamentos:");
            System.out.println(meds);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
