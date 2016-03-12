App VOiQ.

La app permite listar los primeros 20 Pokemones del servidor. Al seleccionar un item de la lista, mostrar� la informaci�n
relacionada al Pokem�n seleccionado.

En la primera parte, podemos ver una lista creada con un RecyclerView, utilizando las �ltimas t�cnicas para android L.

En la segunda parte, vemos una actividad que contiene un FrameLayout que colocar� la informaci�n del Pokem�n.

El proyecto contiene una Clase llamada <<MainThread>> que se encargar� de hacer toda la gesti�n de descarga de la informaci�n
del servidor. En ella, se crea alguna informaci�n a trav�s del constructor de la clase, y otra por par�metros enviados en el 
<<doInBackground>>.

Las dem�s clases, son Fragments dentro de una actividad que contienen un Adapter que se encargar� de parsear toda la informaci�n
descargada del servidor.

La clase MainThread, obtendr� una referencia pasada en el constructor de la actividad o del fragment, para colocar la informaci�n 
que se ha descargado del servidor.



LIBRERIAS USADAS

Picasso : Librer�a tercera que se encarga de descargar y colocar en cach� im�genes descargadas del servidor.

GSON : Librer�a que ayuda a parsear la informaci�n descargada del servicio web con clases java propias.

RecyclerView : Una de las nuevas clases de Android que reemplaza el View ListView, por uno m�s potente.

Design 23.2 : Librer�a de dise�o para las versiones anteriores con la misma capacidad visual para versiones anteriores de Android.

Http Legacy : Permite utilizar ciertas caracter�sticas para poder usar el protocolo HTTP.

JUnit: Permite hacer pruebas de automatizaci�n de la app.

Librer�a APP Compat : Permite la visualizaci�n para Android anterior a la versi�n 23, que puedan mostrar todas las caracter�sticas 
que contiene en Material Design.

Librer�a Support-v4: Permite utilizar ciertas clases para versiones anteriores de Android para que no se generen problemas en ejecuci�n.



Pendientes:
1. Primera vez que realizo una app con EndLessListView. Lo estaba haciendo, pero me generaba un error. Por ello, prefer� no 
seguir haci�ndolo de esta manera, dejando incompleta la aplicaci�n ya que queda sin un requerimiento. La idea es trabajar m�s a
profundo a ella, para dejar listo para usar de una forma m�s sencilla. 

2. Se deja pendiente la validaci�n cuando no se tiene internet. Se deja la clase preparada pero por cuestiones de tiempo, no se
realiza la validaci�n.

