package org.example;

import Gestores.GestorPaciente;
import Gestores.GestorMedico;
import Gestores.GestorFarmaceuta;
import entidades.Paciente;
import entidades.Medico;
import entidades.Farmaceuta;
import Vista.LoginVista;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // =======================
            // 1. GESTOR PACIENTES
            // =======================
            GestorPaciente gestorPacientes = new GestorPaciente();

            // Agregar pacientes de prueba
            Paciente p1 = new Paciente(1, "Josué", 88887777, LocalDate.of(1995, 5, 20));
            Paciente p2 = new Paciente(2, "Kenny", 89996666, LocalDate.of(1988, 10, 12));
            Paciente p3 = new Paciente(3, "María", 87775555, LocalDate.of(2004, 12, 12));
            gestorPacientes.agregarPaciente(p1);
            gestorPacientes.agregarPaciente(p2);
            gestorPacientes.agregarPaciente(p3);

            // Guardar en datos/pacientes.xml
            gestorPacientes.guardarXML();

            // Crear otro gestor vacío y cargar desde XML
            GestorPaciente gestorPacientesCargado = new GestorPaciente();
            gestorPacientesCargado.cargarXML();

            System.out.println("Pacientes cargados desde XML:");
            System.out.println(gestorPacientesCargado);

            // =======================
            // 2. GESTOR MEDICOS
            // =======================
            GestorMedico gestorMedicos = new GestorMedico();

            // Agregar médicos de prueba
            Medico m1 = new Medico("Ana", "1", "Cardiología");
            Medico m2 = new Medico("Juan", "2", "Pediatría");
            gestorMedicos.agregarMedico(m1);
            gestorMedicos.agregarMedico(m2);

            // Guardar en datos/medicos.xml
            gestorMedicos.guardarXML();

            // Crear otro gestor vacío y cargar desde XML
            GestorMedico gestorMedicosCargado = new GestorMedico();
            gestorMedicosCargado.cargarXML();

            System.out.println("Médicos cargados desde XML:");
            System.out.println(gestorMedicosCargado);

            // =======================
            // 3. GESTOR FARMACEUTAS
            // =======================
            GestorFarmaceuta gestorFarmas = new GestorFarmaceuta();

            // Agregar farmaceutas de prueba
            Farmaceuta f1 = new Farmaceuta("4", "Rafael");
            Farmaceuta f2 = new Farmaceuta("2", "Pedro");
            gestorFarmas.agregarFarmaceuta(f1);
            gestorFarmas.agregarFarmaceuta(f2);

            // Guardar en datos/farmaceutas.xml
            gestorFarmas.guardarXML();

            // Crear otro gestor vacío y cargar desde XML
            GestorFarmaceuta gestorFarmasCargado = new GestorFarmaceuta();
            gestorFarmasCargado.cargarXML();

            System.out.println("Farmaceutas cargados desde XML:");
            System.out.println(gestorFarmasCargado);

        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginVista vista = new LoginVista();
        LoginControlador controlador = new LoginControlador(vista);
        vista.setVisible(true);

    }
}
