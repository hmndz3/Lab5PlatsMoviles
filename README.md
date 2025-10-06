PokéDex App - Aplicación Android

Introducción
Esta es una aplicación Android que permite explorar el mundo Pokémon de una manera sencilla y visual. La aplicación muestra una lista de los primeros 100 Pokémon y 
permite ver los detalles de cada uno, incluyendo sus diferentes versiones visuales (sprites normales y shiny). El proyecto fue desarrollado como parte de un laboratorio 
para aprender a consumir APIs REST en Android usando tecnologías modernas.

¿Qué hace la aplicación?
La aplicación tiene dos pantallas principales. La primera muestra un grid con todos los Pokémon disponibles, mostrando su imagen, número y nombre. 
Al hacer clic en cualquier Pokémon, se abre una segunda pantalla que muestra cuatro imágenes diferentes del mismo: la versión frontal y trasera normal, y la versión frontal y trasera shiny.

Tecnologías utilizadas
Para este proyecto utilizamos Kotlin como lenguaje de programación y Jetpack Compose para crear la interfaz de usuario. 
Retrofit nos ayuda a consumir la PokéAPI, que es de donde obtenemos toda la información de los Pokémon. 
También usamos Coil para cargar las imágenes desde internet de forma eficiente, y Navigation Compose para movernos entre las diferentes pantallas de la app.

Estructura del proyecto
El proyecto está organizado en dos carpetas principales. La carpeta network contiene todo lo relacionado con la conexión a internet: el servicio de Retrofit que define cómo obtener los datos, 
los modelos que representan la información de los Pokémon, y el cliente de Retrofit que configura la conexión. 
La carpeta ui/theme contiene las pantallas de la aplicación: el ViewModel que maneja la lógica, la pantalla principal con la lista de Pokémon, y la pantalla de detalle que muestra las imágenes.

Cómo funciona
Cuando abres la aplicación, el ViewModel hace una petición a la PokéAPI para obtener la lista de los primeros 100 Pokémon. 
Mientras espera la respuesta, muestra una pantalla de carga. Una vez que recibe los datos, los muestra en un grid de dos columnas. 
Cada tarjeta de Pokémon incluye su imagen, número y nombre. Si hay algún error de conexión, la aplicación muestra un mensaje de error con un botón para intentar nuevamente.
Al seleccionar un Pokémon, la aplicación navega a la pantalla de detalle donde se muestran cuatro imágenes organizadas en un formato 2x2: 
las versiones normal y shiny del Pokémon visto de frente y de espalda. Las tarjetas de las versiones shiny tienen un fondo amarillo para distinguirlas fácilmente.

<img width="386" height="794" alt="image" src="https://github.com/user-attachments/assets/06830153-e941-4dc4-829e-a62247bdb05b" />   <img width="381" height="790" alt="image" src="https://github.com/user-attachments/assets/c55dc411-3c0c-4e1d-8fd4-550e74ab85e3" />

