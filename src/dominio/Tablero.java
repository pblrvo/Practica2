/**
 Copyright 2021 Pablo Rivero
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package dominio;
import java.io.*;
import java.util.*;
import java.lang.Math;

/*
*Clase Tablero, que genera las listas de 0's y 1's, siguiendo las reglas del juego de la vida
* @author Pablo Rivero
* @version 24/03/2021
 */
public class Tablero {
    private final static int DIMENSION = 30;
    private int[][] estadoActual = new int [DIMENSION + 2] [DIMENSION + 2];
    private int[][] estadoSiguiente = new int [DIMENSION + 2] [DIMENSION + 2];
    /*
    *Metodo que crea un fichero y agrega la serie de valores de el tablero
    *Tiene complejidad cuadratica (n^2)
     */
    public void leerEstadoActual(){
        try {
            File matriz = new File("matriz");
            Scanner sc = new Scanner(matriz);
            for (int i = 0; i < DIMENSION; i++) {
                String next = sc.nextLine();
                for (int j = 0; j < DIMENSION; j++) {
                    estadoActual[i+1][j+1] = Integer.parseInt(String.valueOf(next.charAt(j)));
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("No se puede acceder a la informacion");
        }
    }

    /*
    * Metodo que genera una serie de numeros aleatorios del 0 al 1 en el array estadoActual
    * Este metodo genera el estado inicial del programa
    * Si el numero generado es 0, la celula estara muerta. Si el numero generado es 1, la celula esta viva
    * Posee una complejidad cuadratica(n^2)
     */
    public void generarEstadoActualPorMontecarlo(){
        double numeroAleatorio;
        for(int i = 0; i < DIMENSION; i++){
            for(int j = 0; j < DIMENSION; j++) {
                numeroAleatorio = Math.random();
                if(numeroAleatorio<0.5){
                    estadoActual[i][j] = 0;
                } else{
                    estadoActual[i][j] = 1;
                }
            }
        }

    }

    /*
    *Metodo que toma el estado actual de la matriz de 1's y 0's y lo lleva al siguiente estado
    * Si la celula esta viva, y dos o tres de sus vecinos estan vivos, esta seguira viva
    * Si una celula esta muerta, y tres de sus vecinas estan vivas, esta pasara a estar viva
    * En el resto de los casos la celula estara muerta
    * Posee un complejidad cuadratica(n^2)
     */
    public void transitarAlEstadoSiguiente() {
        for (int i = 1; i < DIMENSION + 1; i++) {
            for (int j = 1; j < DIMENSION + 1; j++) {
                int contador = 0;

                if (estadoActual[i][j + 1] == 1) {
                    contador += 1;
                }
                if (estadoActual[i - 1][j + 1] == 1) {
                    contador += 1;
                }
                if (estadoActual[i][j - 1] == 1) {
                    contador += 1;
                }
                if (estadoActual[i - 1][j - 1] == 1) {
                    contador += 1;
                }
                if (estadoActual[i + 1][j - 1] == 1) {
                    contador += 1;
                }
                if (estadoActual[i - 1][j] == 1) {
                    contador += 1;
                }
                if (estadoActual[i + 1][j] == 1) {
                    contador += 1;
                }
                if (estadoActual[i + 1][j + 1] == 1) {
                    contador += 1;
                }

                if (contador == 3){
                    estadoSiguiente[i][j] = 1;
                } else if (estadoActual[i][j] == 1 && contador >= 2){
                    estadoSiguiente[i][j] = 1;
                } else {
                    estadoSiguiente[i][j] = 0;
                }
            }
        }
        estadoActual = estadoSiguiente;
    }

        /*
        *Metodo toString que modifica su contenido a el de la lista estadoActual
        * Posee un complejidad lineal o(n)
         */
        @Override
        public String toString() {
            StringBuilder line = new StringBuilder();
            for (int i = 1; i < DIMENSION; i++) {
                for (int j = 1; j < DIMENSION; j++) {
                    if (estadoActual[i][j] == 0) {
                        line.append(" ");
                    } else {
                        line.append("x");
                    }
                }
                line.append("\n");
            }
            return line.toString();
        }
}