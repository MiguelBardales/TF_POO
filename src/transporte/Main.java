package transporte;

import java.util.Scanner;

public class Main {
	static Registro registro = new Registro();
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		int dni, numero;
		String fecha;
		int opcion = 0;
		int subopcion = 0;
		while(opcion != 7) {
			boolean esperar = false;
			opcion = Menu.mostrarMenu();
			switch (opcion) {
				case 1:
					System.out.println();
					subopcion = Menu.mostrarMenuRegistro();
					switch (subopcion) {
						case 1:
							System.out.print("Ingrese el número del nuevo bus: ");
							numero = Menu.leerEntero("Ingrese un número de bus válido: ");
							if (registro.buscarBus(numero) == null) {
								//Si el bus no está registrado
								System.out.print("Ingrese la fecha de salida del bus: ");
								fecha = scanner.nextLine();
								System.out.print("El bus sale a las horas: ");
								int horas = Menu.leerEntero("Ingrese un número entero: ");
								System.out.print("¿Con cuántos minutos? ");
								int minutos = Menu.leerEntero("Ingrese un número entero: ");
								try {
									registro.registrarBus(numero, fecha, horas, minutos);
									System.out.println("El bus numero " + numero + " ha sido registrado correctamente.");
									esperar = true;
								} catch (Exception e) {
									System.out.println("El bus no se pudo registrar. Asegúrese de colocar los datos correctamente.");
								} finally {
									esperar = true;
								}
							}
							else {
								System.out.println("El bus de numero " + numero + " ya está registrado.");
								esperar = true;
							}
							break;
						case 2:
							System.out.print("Ingrese el DNI del vecino: ");
							dni = Menu.leerDNI();
							if (registro.buscarPasajero(dni) == null) {
								//Si el vecino no está registrado
								System.out.print("Ingrese el número de teléfono del vecino: ");
								int telefono = Menu.leerEntero("Ingrese un número entero: ");
								System.out.print("Ingrese el estado civil del vecino: ");
								String estadoCivil = scanner.nextLine();
								System.out.print("Ingrese la edad del vecino: ");
								int edad = Menu.leerEntero("Ingrese un número entero: ");
								System.out.print("Ingrese el correo electrónico del vecino: ");
								String correoElectronico = scanner.nextLine();
								System.out.println("¿El vecino es adulto mayor o miembro del Club de Ecología?");
								System.out.println("1. Adulto mayor");
								System.out.println("2. Personal del Club\n");
								int opcionVecino = Menu.leerOpciones(2);
								String tipo = (opcionVecino == 1) ? "Vecino" : "Personal";
								try {
									registro.registrarPasajero(tipo, dni, telefono, estadoCivil, edad, correoElectronico);
									System.out.println("El vecino con DNI N° " + dni + " ha sido registrado correctamente.");
									esperar = true;
								} catch (Exception e) {
									System.out.println("El vecino no se pudo registrar. Asegúrese de colocar los datos correctamente.");
								} finally {
									esperar = true;
								}
							}
							else {
								System.out.println("El vecino con DNI N° " + dni + " ya está registrado.");
								esperar = true;
							}
							break;
						case 3:
							System.out.println();
							break;
					}
					subopcion = 0;
					break;
				case 2:
					System.out.print("Ingrese el número de DNI del pasajero: ");
					dni = Menu.leerDNI();
					System.out.print("Ingrese la fecha que requiere reservar: ");
					fecha = scanner.nextLine();
					try {
						String boleto = registro.reservarAsiento(dni, fecha);
						System.out.println("La reserva se hizo satisfactoriamente.\n");
						System.out.println(boleto + "\n");
					}
					catch (PasajeroNoRegistradoException e) {
						System.out.println("No hay vecino registrado con DNI N° " + dni + ".");
					}
					catch (BusNoDisponibleException e) {
						System.out.println("No hay bus disponible para la fecha indicada.");
					}
					catch (Exception e) {
						System.out.println("No se puede hacer la reserva.");
					}
					finally {
						esperar = true;
					}
					break;
				case 3:
					System.out.print("Ingrese el número de DNI del pasajero: ");
					dni = Menu.leerDNI();
					Menu.imprimirDatosPasajero(registro, dni);
					esperar = true;
					break;
				case 4:
					if (registro.numeroBuses() == 0) {
						System.out.println("No hay buses registrados.");
						esperar = true;
					}
					else {
						System.out.print("Ingrese el número de bus: ");
						numero = Menu.leerNumeroBus(registro);
						Bus bus = registro.buscarBus(numero); //Nunca será null
						System.out.println();
						while(subopcion != 4) {
							subopcion = Menu.mostrarMenuReporte(numero);
							switch (subopcion) {
								case 1:
									int asiento = 1;
									System.out.println("Reporte de asientos: (X: reservado, O: disponible)");
									for (int i = 0; i < Bus.CAPACIDAD/10; i++) {
										for (int j = 0; j < 10; j++) {
											try {
												System.out.printf("%2d) %s\t", asiento, bus.getPasajero(asiento) == null ? "O" : "X");
											} catch (Exception e) {
												System.out.println(e.getMessage());
											}
											asiento++;
										}
										System.out.println();
									}
									System.out.println("Presione Enter para continuar.\n");
									try {
										System.in.read();
									} catch (Exception e) { }
									break;
								case 2:
									if (bus.numeroAsientosOcupados() == 0) {
										System.out.println("\nNingún pasajero ha reservado este bus.");
									}
									else {
										System.out.println("====Reporte de pasajeros====");
										for (Pasajero pasajero : bus.getRelacionPasajeros()) {
											if (pasajero != null) {
											String obsequio = pasajero.obtenerPromocion();	
											System.out.println(pasajero.datos() + "\n" + (obsequio.equals("") ? "" : "Obsequio: " + obsequio));
											System.out.println("============================");
											}
										}
									}
									System.out.println("\nPresione Enter para continuar.\n");
									try {
										System.in.read();
									} catch (Exception e) { }
									break;
								case 3:
									System.out.println("\nEl bus tiene " + bus.numeroAsientosDisponibles() + " asientos disponibles.");
									System.out.println("\nPresione Enter para continuar.\n");
									try {
										System.in.read();
									} catch (Exception e) { }
									break;
								case 4:
									System.out.println();
									break;
							}
						}
						subopcion = 0;
					}
					break;
				case 5:
					try {
						System.out.println("El promedio de las edades de los adultos mayores registrados es igual a " + Menu.promedioAdultosMayores(registro) + ".");
					} catch(Exception e) {
						System.out.println("No hay adultos mayores registrados.");
					} finally {
						esperar = true;
						break;
					}
				case 6:
					try {
						System.out.println("El promedio de las edades de los vecinos miembros del Club de Ecología que no recibieron obsequio es igual a " + Menu.promedioPersonal(registro) + ".");
					} catch(Exception e) {
						System.out.println("No hay miembros del Club de Ecología sin obsequio registrados.");
					} finally {
						esperar = true;
						break;
					}
				case 7:
					System.out.println("\nSaliendo. Presione Enter para terminar de salir.");
					try {
						System.in.read();
					} catch (Exception e) { }
					break;
			}
			if (esperar) {
				System.out.println("Presione Enter para volver al menú principal.\n");
				try {
					System.in.read();
				} catch (Exception e) { }
			}
		}
	}
	
	static class Menu {
		static int mostrarMenu() {
			System.out.println("============");
			System.out.println("    Menu    ");
			System.out.println("============");
			System.out.println("1. Registrar");
			System.out.println("2. Reservar");
			System.out.println("3. Buscar vecino");
			System.out.println("4. Reporte de buses");
			System.out.println("5. Promedio de edades de los adultos mayores");
			System.out.println("6. Promedio de edades del personal sin obsequio");
			System.out.println("7. Salir");
			System.out.print("\nIngrese una opción: ");
			return leerOpciones(7);
		}
		
		static int mostrarMenuRegistro() {
			System.out.println("=========");
			System.out.println("Registrar");
			System.out.println("=========");
			System.out.println("1. Bus");
			System.out.println("2. Vecino");
			System.out.println("3. Atrás");
			System.out.print("\nIngrese una opción: ");
			return leerOpciones(3);
		}
		
		static int mostrarMenuReporte(int numero) {
			System.out.println("==============");
			System.out.println("Bus N° " + numero);
			System.out.println("==============");
			System.out.println("1. Reporte de asientos");
			System.out.println("2. Reporte de pasajeros");
			System.out.println("3. Asientos disponibles");
			System.out.println("4. Atrás");
			System.out.print("\nIngrese una opción: ");
			return leerOpciones(4);
		}
		
		static int leerEntero(String mensaje) {
			try {
				return Integer.parseInt(scanner.nextLine());
			}
			catch (NumberFormatException e) {
				System.out.print(mensaje);
				return leerEntero(mensaje);
			}
		}
		
		static int leerOpciones(int numeroOpciones) {
			int opcion = leerEntero("Ingrese una opción válida: ");
			if (opcion >= 1 && opcion <= numeroOpciones) {
				return opcion;
			}
			else {
				System.out.print("Ingrese una opción válida: ");
				return leerOpciones(numeroOpciones);
			}
		}
		
		static int leerDNI() {
			int dni = leerEntero("Ingrese un número de DNI válido: ");
			if (dni >= 10000000 && dni <= 99999999) {
				return dni;
			}
			else {
				System.out.print("Ingrese un número de DNI válido: ");
				return leerDNI();
			}
		}
		
		static int leerNumeroBus(Registro registro) {
			int numero = leerEntero("Ingrese un número de bus válido: ");
			Bus bus = registro.buscarBus(numero);
			if (bus != null) {
				return numero;
			}
			else {
				System.out.print("Ingrese un número de bus registrado: ");
				return leerNumeroBus(registro);
			}
		}
		
		static void imprimirDatosPasajero(Registro registro, int dni) {
			Pasajero pasajero = registro.buscarPasajero(dni);
			if (pasajero == null) {
				System.out.println("No hay vecino registrado con DNI N° " + dni + ".");
			}
			else {
				System.out.println("\n====Datos====\n" + pasajero.datos() + "\n");
			}
		}
		
		static double promedioAdultosMayores(Registro registro) throws Exception {
			int contador = 0;
			double promedio = 0;
			for (Pasajero pasajero : registro.getRegistroPasajeros()) {
				if (pasajero instanceof Vecino) {
					promedio += pasajero.getEdad();
					contador++;
				}
			}
			if (contador == 0) {
				throw new Exception("No hay vecinos que cumplan con la condición.");
			}
			return promedio/contador;
		}
		
		static double promedioPersonal(Registro registro) throws Exception {
			int contador = 0;
			double promedio = 0;
			for (Pasajero pasajero : registro.getRegistroPasajeros()) {
				if (pasajero instanceof Personal && pasajero.obtenerPromocion().equals("")) {
					promedio += pasajero.getEdad();
					contador++;
				}
			}
			if (contador == 0) {
				throw new Exception("No hay vecinos que cumplan con la condición.");
			}
			return promedio/contador;
		}
	}
}
