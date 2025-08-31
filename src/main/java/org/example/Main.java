package org.example;

import Gestores.GestorPaciente;
import Gestores.GestorMedico;
import Gestores.GestorFarmaceuta;
import Gestores.GestorMedicamento;
import Gestores.GestorRecetas;

import entidades.Paciente;
import entidades.Medico;
import entidades.Farmaceuta;
import entidades.Medicamento;

import Prescripcion.PrescripcionReceta;
import Prescripcion.Indicaciones;

import Despacho.DespachoReceta;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // =======================
            // 1) PACIENTES
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
            System.out.println("Pacientes:");
            System.out.println(gestorPacientesCargado);

            // =======================
            // 2) MEDICOS
            // =======================
            GestorMedico gestorMedicos = new GestorMedico();
            Medico m1 = new Medico("Ana",  "1", "Cardiología");
            Medico m2 = new Medico("Juan", "2", "Pediatría");
            gestorMedicos.agregarMedico(m1);
            gestorMedicos.agregarMedico(m2);
            gestorMedicos.guardarXML();

            GestorMedico gestorMedicosCargado = new GestorMedico();
            gestorMedicosCargado.cargarXML();
            System.out.println("Médicos:");
            System.out.println(gestorMedicosCargado);

            // =======================
            // 3) FARMACEUTAS
            // =======================
            GestorFarmaceuta gestorFarmas = new GestorFarmaceuta();
            Farmaceuta f1 = new Farmaceuta("1", "Rafael"); // token=2 en constructor
            Farmaceuta f2 = new Farmaceuta("2", "Pedro");
            gestorFarmas.agregarFarmaceuta(f1);
            gestorFarmas.agregarFarmaceuta(f2);
            gestorFarmas.guardarXML();

            GestorFarmaceuta gestorFarmasCargado = new GestorFarmaceuta();
            gestorFarmasCargado.cargarXML();
            System.out.println("Farmaceutas:");
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

            // =======================
            // 5) PRESCRIPCIONRECETA
            // =======================
            PrescripcionReceta receta = new PrescripcionReceta();
            boolean asignadoPorId = receta.agregarPaciente(gestorPacientes, 1);
            System.out.println("Asignar paciente por ID -> " + asignadoPorId);

            Indicaciones ind1 = new Indicaciones();
            ind1.setMedicamento(meds.buscarMedicamentoCodigo(1001));
            ind1.setCantidad(20);
            ind1.setDescripcion("Tomar 1 tableta cada 8 horas");
            ind1.setDuracion(5);
            receta.agregarIndicacion(ind1);

            Indicaciones ind2 = new Indicaciones();
            ind2.setMedicamento(meds.buscarMedicamentoCodigo(2001));
            ind2.setCantidad(10);
            ind2.setDescripcion("Tomar 1 tableta cada 12 horas con comida");
            ind2.setDuracion(5);
            receta.agregarIndicacion(ind2);

            receta.estadoConfeccionado();
            receta.setFecha_retiro(LocalDate.now());
            receta.guardarXML();

            PrescripcionReceta recetaCargada = new PrescripcionReceta();
            recetaCargada.cargarXML();
            System.out.println("Prescripción cargada: estado=" + recetaCargada.getEstado()
                    + ", indicaciones=" + recetaCargada.cantidad());


            // =======================
            // 6) DESPACHO + HISTÓRICO
            // =======================
            GestorRecetas gestorRecetas = new GestorRecetas();
            DespachoReceta despacho = new DespachoReceta();

            boolean paso1 = despacho.iniciarProceso(gestorFarmasCargado, f1, receta, gestorRecetas);
            System.out.println("Despacho.iniciarProceso -> " + paso1 + " | estado=" + receta.getEstado());

            boolean paso2 = despacho.marcarLista(gestorFarmasCargado, f1, receta, gestorRecetas);
            System.out.println("Despacho.marcarLista -> " + paso2 + " | estado=" + receta.getEstado());

            boolean paso3 = despacho.entregar(gestorFarmasCargado, f1, receta, gestorRecetas);
            System.out.println("Despacho.entregar -> " + paso3 + " | estado=" + receta.getEstado());

            try {
                gestorRecetas.cargarXML();
                System.out.println("Histórico de recetas (recetas.xml):");
                System.out.println(gestorRecetas);
            } catch (Exception ignored) {}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
