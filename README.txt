App VOiQ.

La app permite listar los primeros 20 Pokemones del servidor. Al seleccionar un item de la lista, mostrará la información
relacionada al Pokemón seleccionado.

En la primera parte, podemos ver una lista creada con un RecyclerView, utilizando las últimas técnicas para android L.

En la segunda parte, vemos una actividad que contiene un FrameLayout que colocará la información del Pokemón.

El proyecto contiene una Clase llamada <<MainThread>> que se encargará de hacer toda la gestión de descarga de la información
del servidor. En ella, se crea alguna información a través del constructor de la clase, y otra por parámetros enviados en el 
<<doInBackground>>.

Las demás clases, son Fragments dentro de una actividad que contienen un Adapter que se encargará de parsear toda la información
descargada del servidor.

La clase MainThread, obtendrá una referencia pasada en el constructor de la actividad o del fragment, para colocar la información 
que se ha descargado del servidor.



LIBRERIAS USADAS

Picasso : Librería tercera que se encarga de descargar y colocar en caché imágenes descargadas del servidor.

GSON : Librería que ayuda a parsear la información descargada del servicio web con clases java propias.

RecyclerView : Una de las nuevas clases de Android que reemplaza el View ListView, por uno más potente.

Design 23.2 : Librería de diseño para las versiones anteriores con la misma capacidad visual para versiones anteriores de Android.

Http Legacy : Permite utilizar ciertas características para poder usar el protocolo HTTP.

JUnit: Permite hacer pruebas de automatización de la app.

Librería APP Compat : Permite la visualización para Android anterior a la versión 23, que puedan mostrar todas las características 
que contiene en Material Design.

Librería Support-v4: Permite utilizar ciertas clases para versiones anteriores de Android para que no se generen problemas en ejecución.



Pendientes:
1. Primera vez que realizo una app con EndLessListView. Lo estaba haciendo, pero me generaba un error. Por ello, preferí no 
seguir haciéndolo de esta manera, dejando incompleta la aplicación ya que queda sin un requerimiento. La idea es trabajar más a
profundo a ella, para dejar listo para usar de una forma más sencilla. 

2. Se deja pendiente la validación cuando no se tiene internet. Se deja la clase preparada pero por cuestiones de tiempo, no se
realiza la validación.

