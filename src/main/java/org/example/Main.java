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
            // 1. GESTOR PACIENTES
            // =======================
            GestorPaciente gestorPacientes = new GestorPaciente();

            Paciente p1 = new Paciente(1, "Josué", 88887777, LocalDate.of(1995, 5, 20));
            Paciente p2 = new Paciente(2, "Kenny", 89996666, LocalDate.of(1988, 10, 12));
            Paciente p3 = new Paciente(3, "María", 87775555, LocalDate.of(2004, 12, 12));
            gestorPacientes.agregarPaciente(p1);
            gestorPacientes.agregarPaciente(p2);
            gestorPacientes.agregarPaciente(p3);

            gestorPacientes.guardarXML();

            GestorPaciente gestorPacientesCargado = new GestorPaciente();
            gestorPacientesCargado.cargarXML();

            System.out.println("Pacientes cargados desde XML:");
            System.out.println(gestorPacientesCargado);

            // =======================
            // 2. GESTOR MEDICOS
            // =======================
            GestorMedico gestorMedicos = new GestorMedico();

            Medico m1 = new Medico("Ana", "1", "Cardiología");
            Medico m2 = new Medico("Juan", "2", "Pediatría");
            gestorMedicos.agregarMedico(m1);
            gestorMedicos.agregarMedico(m2);

            gestorMedicos.guardarXML();

            GestorMedico gestorMedicosCargado = new GestorMedico();
            gestorMedicosCargado.cargarXML();

            System.out.println("Médicos cargados desde XML:");
            System.out.println(gestorMedicosCargado);

            // =======================
            // 3. GESTOR FARMACEUTAS
            // =======================
            GestorFarmaceuta gestorFarmas = new GestorFarmaceuta();

            Farmaceuta f1 = new Farmaceuta("1", "Rafael");
            Farmaceuta f2 = new Farmaceuta("2", "Pedro");
            gestorFarmas.agregarFarmaceuta(f1);
            gestorFarmas.agregarFarmaceuta(f2);

            gestorFarmas.guardarXML();

            GestorFarmaceuta gestorFarmasCargado = new GestorFarmaceuta();
            gestorFarmasCargado.cargarXML();

            System.out.println("Farmaceutas cargados desde XML:");
            System.out.println(gestorFarmasCargado);

            // =======================
            // 4) MEDICAMENTOS
            // =======================
            GestorMedicamento meds = new GestorMedicamento();

            meds.agregarMedicamento(new Medicamento(1001, "Acetaminofén", "Tableta 500 mg", "Analgésico y antipirético"));
            meds.agregarMedicamento(new Medicamento(1002, "Acetaminofén", "Jarabe 100 mg/5 ml", "Uso pediátrico"));
            meds.agregarMedicamento(new Medicamento(2001, "Ibuprofeno", "Tableta 400 mg", "AINE antiinflamatorio"));

            meds.guardarXML();
            meds.cargarXML();

            System.out.println("Medicamentos:");
            System.out.println(meds);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
