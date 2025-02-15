package com.campmaster;

public class MainCampMaster {
    public static void main(String[] args) {
        AppCampMaster.main(args);
    }
}

//TODO: freemarker, entity mapper, separar las querys de los metodos, y para cada entidad crear un DAO que gestione su CRUD particular
//TODO: crear en la vista principal un boton que solo se active si el usuario es de tipo administrador o monitor, luego
//otra ventana aparte, en la que, se active un boton solo si el usuario es administrador
//en esa ventana, que aparezca el inventario (mirad la interfaz del tipo monitorpickcontroller, que es igual, una lista)
//Y el boton de modificar el inventario abre otra ventana con posibilidad de modificar los objetos