package com.example.practica13_bitacora_ficheros;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText et_bitacora;//creamos nuestro objeto EditText que tenemos en nuestra parte grafica

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_bitacora = (EditText)findViewById(R.id.txt_bitacora);//buscamos nuestro objeto EditText que tenemos en nuestra parte grafica
        //cuando el usuario ha escrito algo en la bitacora y lo ha guardado lo muestra al abrir la aplicacion y en cambio cuando no ha escrito nada la aplicacion no muestra nada solo queda a la espera de escribir un texto y guardarlo
        //cuando trabajamos con ficheros( tambien conocidos como documentos de texto) le tenemos que decir a nuestra aplicacion que lo primero que debe de hacer es ir a buscarlo ya sea que tengamos uno o mas ficheros
        //para ello debemos usar un nuevo METODO que nos va a ayudar a que la aplicacion cada vez que se abra nos muestre el texto que se guardo previamente en dado caso que si se haya guardado de lo contrario no hara nada solo quedara a la espera de la introduccion de texto
        //El metodo FileList devuelve un array con los ficheros almacenados por nuestra aplicacion
        //Este metodo ademas de recuperar los ficheros a traves de un array, tambien debemos crearles un espacio en memoria donde van a estar de manera temporal.
        String archivos[] = fileList();//Esta linea de codigo busca todos los ficheros que se hayan creado, los va a recupear y los vas a colocar en el vector creado llamado archivos
        //una vez que recuperamos los ficheros es importante crear una estructura condicional para indicarle al programa que tiene que hacer en caso de localizarlos o no encontrar nada
        //la condicion que va a regir esta secuencia de pasos sera un metodo booleano, es necesario colocar el nombre de ese metodo booleano a la estructura condicional, de inicio marcara error ya que no esta creado pero mas abajo en el codigo se puede ver la creacion de este metodo
        if(ArchivoExiste(archivos, "bitacora.txt") ){//el metodo pasara dos parametros, el primero sera el vector que creamos previamente llamado archivos, ya que contiene los archivos que nuestro metodo filelist acaba de encontrar, el segundo parametro entre comillas es el nombre del fichero ncon el que estamos trabajando, este nombre debe ser el mismo en todos los metodos que vamos a utilzar y ademas siempre debe tener la extension .txt ya que estamos creando documentos de texto si no tiene esta extension no va a funcionar
            try {//es necesario encerrar nuestros metodos entre la sentecia TRY/CATCH
                InputStreamReader archivo_ISR = new InputStreamReader(openFileInput("bitacora.txt")); //Creamos un objeto de tipo InputStreamReader, esta clase nos permite abrir un archivo para leerlo, con la ayuda del metodo openFileInput que nos permite poder indicar cual es el archivo que queremos abrir
                //Una vez que ya esta abierto nuestro archivo debemos leerlo, para esto nos ayuda el metodo BuffereReader
                // ya que nos permite leer un archivo que abrimos previamente con la clase InputStreamReader
                BufferedReader objeto_br = new BufferedReader(archivo_ISR); //Creamos el objeto BuffereReader y posteriormente debemos colocarle el archivo que queremos que lea en este caso el archivo InputStreamReader(archivo_ISR)
                //Ahora que ya estamos leyendo nuestro archivo debemos indicar en donde vamos a guardar el texto linea por linea que contiene nuestro archivo
                //Creamos una variable de tipo String ya que es texto lo que estamos manejando, dentro de esta variable se va a alojar lo que nuestro objeto_br va a ir leyendo, el metodo readLine nos permite leer toda la linea de texto
                //Para saber que el archivo no esta vacio usaremos la siguiente linea de textog
                String linea_RL_String = objeto_br.readLine();//El programa leera SOLO LA PRIMER LINEA de texto al momento de abrir archivo_ISR, es decir una vez encontrado el archivo "bitacora.txt" se abrira con objeto archivo_ISR, despues el objeto_br lo leera con ayuda del metodo readLine y esa linea la guardara en el objeto linea_RL_String
                String BitacoraCompleta_String = "";//Esta variable guardara cada texto que se vaya encontrando dentro de nuestro archivo "bitacora.txt" Si la primer linea de texto esta vacia no tiene caso guardar nada dentro de esta variable
                //Asi como ya verificamos si la primer linea de texto no esta vacia entonces podemos interpetar que las demas lineas de texto tambien contienen algo por eso la debemos guardar en la variable BitacoraCompleta_String
                while(linea_RL_String != null) {//Con ayuda de un while, le indicaremos que mientras que la variable linea_RL_String (esta es nuestra primer linea del texto) sea diferente a NULL(vacio) quiere decir que el archivo contiene texto
                    //Creamos una variable de acumulacion con ayuda de la variable previamente creada antes del while
                    BitacoraCompleta_String = BitacoraCompleta_String + linea_RL_String + "\n";//En esta variable vamos a concatenar nuestra primer linea del texto que se encontro, de esta forma aprovecaremos para guardar el texto y agregamos un salto de linea al terminar de guardar nuestro texto encontrado
                    //Aprovecgando que ya estamos en una estructura repetitiva vamos a indicar que el programa debe leer la siguiente linea de texto
                    linea_RL_String = objeto_br.readLine();//Con esto cada vez que termine de agregar una linea va a seguir agregando mas y mas lineas hasta que se encuentre con que la ultima linea ya es diferente de vacia entpoces no agregara nada mas.
                    //De esta forma ya podremos obtener completo el texto escrito por el usuario osea el archivo "bitacora.txt" guardado dentro de la variable "BitacoraCompleta_String"
                }
                //Al terminar de almacenar el texto debemos indicarle al objeto que debe dejar de leer, esto lo hacemos con la ayuda del metodo Close y tambien debemos hacer que el archivo se cierre
                objeto_br.close();
                archivo_ISR.close();
                et_bitacora.setText(BitacoraCompleta_String);//Ahora le indicamos al programa que el texto almacenado en "BitacoraCompleta_String" se coloque dentro de nuestro EditText
            }catch (IOException e) {
            }
        }
    }

    //Creacion del metodo booleano Archivo Existe
    private boolean ArchivoExiste(String archivos [], String NombreArchivo){
    //Cuando trabajamos con metodos booleanos, no es necesario usar llaves de apertura ni de cierre en las estrucuturas que coloquemos en estos metodos
        // pero a este vector no podemos recorrerlo de manera automatica ya que necesitamos indices que podemos manipular mediate estrucutras repetitivas
        //Este for lo vamos a utilizar para recorrer el vector, ya que en la linea "String archivos [] = fileList();" se recuperan los archivos mediante un arreglo/array y despues los guardamos en un nuevo vector
        for(int i = 0; i < archivos.length; i++)//indicamos el rcorrido del vector "archivos" de uno en uno
            if(NombreArchivo.equals(archivos[i])) //hasta que encontremos el nombre del archivo que coinicida con el que determinamos en nuestro metodo ArchivoExiste ("bitacora.txt")
                return true;//una vez que lo encuentre regresara un valor verdadero
            return false;//si no lo encuentra regresara un valor falso
    }

    //Metodo Boton GUARDAR
    public void Guardar(View view){
        try {
            //Ahora debenmos guardar el texto que el usuario puso dentro del editText lo que tenemos que hacer utilizar el metodo OutputStreamWriter
            //Este metodo indica que vamos a mandar texto a un nuevo archivo que vamos a escribir
            OutputStreamWriter archivo_OSW = new OutputStreamWriter(openFileOutput("bitacora.txt", Activity.MODE_PRIVATE));//Este metodo necesita tener un objeto y ayudandonos del metodo openFileOutput vamos a indicar el nombre del archivo con el que estamos trabajando
            //Metodo que nos permite escribir detro de nuestro archivo "bitacora.txt" el texto que nuestro usuario a escrito desde el EditText
            archivo_OSW.write(et_bitacora.getText().toString());//colocamos el objeto que creamos de tipo OutputStreamWriter seguido del metodo "write()" y dentro del parentesis colocamos el objeto con el que estamos obteniendo el texto segudo de las intrucciones obtener texto y convertirlo a tipo String
            //una vez que ya escribimos ese texto debemos indicarle que limpie el canal que nosotros utilizamos para transportar el texto(tambien conocido como Buffere)
            archivo_OSW.flush();//Limpia el canal
            archivo_OSW.close();//Cierra el fichero
        }catch (IOException e) {
        }
        Toast.makeText(this, "Bitacora guardada correctamente", Toast.LENGTH_SHORT).show();//Manda mensaje para validar que se guardo el mensaje
        finish();//termina la aplicacion cuando se presiona el boton Guardar
        }
    }

