# Museoteca

## 👥 Miembros del Equipo
| Nombre y Apellidos | Correo URJC | Usuario GitHub |
|:--- |:--- |:--- |
| Adrián Villalba Cuello de Oro | a.villalba.2023@alumnos.urjc.es | AdrianVillalba26 |

---

## 🎭 **Preparación 1: Definición del Proyecto**

### **Descripción del Tema**
Aplicación sobre una web informativa de un museo. Los usuarios que acceden a la página pueden seleccionar una de las categorías disponibles en la web, de manera que se muestran todos los objetos que posee el museo de dicha categoría. Además de esto, el usuario puede elegir uno de los objetos mostrados de la categoría seleccionada para consultar información de interés acerca del objeto. La finalidad de esta web es que los usuarios puedan aprender y conocer en mayor profundidad la colección del museo de una manera interactiva, sencilla y accesible mediante una experiencia digital intuitiva y educativa.

### **Entidades**
Indicar las entidades principales que gestionará la aplicación y las relaciones entre ellas:

1. **Usuario**: Persona que entra a la aplicación web del museo.
2. **Objeto**: Pieza o elemento que forma parte de la colección del museo y que pertenece a una sección determinada.
3. **Nota**: Anotación que un usuario puede dejar en un objeto.

**Relaciones entre entidades:**
- Usuario - Objeto: Un usuario puede consultar varios objetos, y un objeto puede ser consultado por varios usuarios (N:M). 
- Usuario - Nota: Un usuario puede dejar varias notas, pero cada nota pertenece a un único usuario (1:N).
- Objeto - Nota: Un objeto puede contener varias notas, pero cada nota está asociada a un único objeto (1:N).

### **Permisos de los Usuarios**
Describir los permisos de cada tipo de usuario e indicar de qué entidades es dueño:

* **Usuario Anónimo**: 
  - Permisos: Puede acceder a una sección, entrar a la página informativa de un objeto y realizar búsquedas.
  - No es dueño de ninguna entidad.

* **Usuario Registrado**: 
  - Permisos: 
  
      -> Puede acceder a una sección, entrar a la página informativa de un objeto y realizar búsquedas usando filtros.  
      -> Puede marcar o desmarcar un objeto como visto.  
      -> Puede dejar una o varias notas en la página informativa de un objeto.  
      -> Puede editar su perfil de usuario.
      -> Puede acceder a la página de estadísticas.
  - Es dueño de: Su perfil de usuario, los objetos que marca como vistos y las notas que deja en un objeto.

* **Administrador**: 
  - Permisos: 
      -> Puede añadir o eliminar un objeto tras acceder a una sección.  
      -> Puede editar un objeto desde la página informativa de este (cambiar foto, nombre o descripción e información del objeto).
      -> Puede editar su perfil de usuario.
      -> Puede acceder al perfil de un usuario para editarlo o puede eliminar un usuario (desde la página de listado de usuarios).
  - Es dueño de: Los objetos que muestra cada sección, los datos e imágenes de cada objeto, y los usuarios que tiene la aplicación.

### **Imágenes**
Indicar qué entidades tendrán asociadas una o varias imágenes:

- **Usuario**: Una imagen en el perfil, que el usuario puede cambiar por otra.
- **Objeto**: Cada objeto tiene una imagen junto a su descripción en la página informativa de dicho objeto. La misma imagen también aparece en el objeto localizado en la lista de objetos que se muestra tras acceder a una sección. Si se edita el objeto (administrador), se puede cambiar o borrar dicha imagen.

### **Gráficos**
Indicar qué información se mostrará usando gráficos y de qué tipo serán:

- **Gráfico 1**: Gráfico que muestra cómo se distribuye el total de objetos entre las diferentes secciones (Gráfico de tarta / circular).

### **Algoritmo o Consulta Avanzada**
Indicar cuál será el algoritmo o consulta avanzada que se implementará:

- **Algoritmo/Consulta**: Cálculo del porcentaje de progreso del usuario por sección.
- **Descripción**: El sistema calcula el porcentaje de objetos que un usuario registrado ha marcado como "vistos" dentro de cada sección del museo.
- **Alternativa**: Determinar la sección más vista del usuario calculando el número de objetos marcados como vistos en cada sección. Si coinciden varias secciones como las más vistas del usuario, se muestran todas las que coincidan.

---

## 🛠 **Práctica 1: Web con HTML generado en servidor y AJAX**

### **Vídeo de Demostración**

📹 **[Enlace al vídeo en YouTube](https://youtu.be/zI_jivVCOrU)**
> Vídeo mostrando las principales funcionalidades de la aplicación web.


### **Navegación y Capturas de Pantalla**

#### **Diagrama de Navegación**

Diagrama que muestra cómo se navega entre las diferentes páginas de la aplicación:

![Diagrama de Navegación](screenshots/diagrama-navegacion.png)

> El usuario accede a la página inicial de la aplicación. A continuación, puede decidir si quiere entrar como usuario anónimo, iniciar sesión o registrarse tanto como usuario registrado como administrador.

> - El usuario anónimo, tras pasar la página inicial, llega a la página principal o home en la versión de usuario anónimo, donde, tras seleccionar una sección, le aparece la página de la sección elegida. En dicha página, si selecciona un elemento de los disponibles, pasa a estar en la página informativa del elemento seleccionado.

> - El usuario que ha iniciado sesión o se ha registrado (tanto como usuario normal como usuario administrador), puede hacer las mismas acciones que el usuario anónimo, más algunas adicionales dependiendo del rol.
Junto a esto, puede consultar su perfil de usuario desde la página principal o home (o desde cualquier página, ya que se puede acceder al perfil en cualquiera de ellas desde el navbar. Por simplicidad, en el diagrama se ha puesto que sea desde la página principal).


#### **Capturas de Pantalla Actualizadas**

#### **1. Página inicial**
![Página Principal](screenshots/pagina-inicial.png)

> Página de inicio de la aplicación. En la página, se te da la opción de iniciar sesión, registrarte, o acceder a la web como usuario anónimo. Si accedes como usuario anónimo, arriba se seguirá mostrando las opciones de inicio de sesión y de registro en todas las páginas por las que navegues.

#### **2. Página de registro**
![Página Principal](screenshots/registro.png)

> Página que se muestra tras haber pulsado la opción "Registrarse" en la barra superior. Se debe poner un nombre de usuario y una contraseña; opcionalmente, se puede poner también una imagen o foto de perfil. Tras esto, al pulsar en "Aceptar" quedas registrado en la aplicación.

#### **3. Página de inicio de sesión**
![Página Principal](screenshots/inicio-sesion.png)

> Página que se muestra tras haber pulsado la opción "Iniciar sesión" en la barra superior. Se debe poner un nombre de usuario y una contraseña para poder iniciar tu sesión en la aplicación. 

#### **4. Página principal / Home**

#### **- Página de usuario anónimo**
![Página Principal](screenshots/pagina-principal-anonimo.png)

> Página que se muestra tras haber pasado la página inicial. En ella, se pueden elegir diferentes secciones temáticas del museo, tanto seleccionando una de las ventanas con los logos representativos, como en las opciones de la barra superior. También se pueden consultar otras secciones que no están visibles en la página (opción "Ver más").

#### **- Página de usuario registrado y de administrador**
![Página Principal](screenshots/pagina-principal-usuario.png)

> Página que se muestra tras haber pasado la página inicial. En ella, se pueden elegir diferentes secciones temáticas del museo, tanto seleccionando una de las ventanas con los logos representativos, como en las opciones de la barra superior. También se pueden consultar otras secciones que no están visibles en la página (opción "Ver más").

#### **5. Página de una sección**

#### **- Página de usuario anónimo**
![Página Principal](screenshots/pagina-seccion-anonimo.png)

> Página que se muestra tras haber seleccionado una sección de las disponibles en la página principal. El usuario puede usar la barra de búsqueda, seleccionar uno de los elementos que se muestran en la página o consultar otros elementos que no están visibles en la página (opción "Ver más").

#### **- Página de usuario registrado**
![Página Principal](screenshots/pagina-seccion-usuario.png)

> Además de lo que puede hacer el usuario anónimo, se pueden buscar elementos por tipos pulsando en los botones disponibles (agua dulce, mar o abisales).

#### **- Página del administrador**
![Página Principal](screenshots/pagina-seccion-admin.png)

> Se puede además añadir un elemento, y editar o eliminar uno de los disponibles.

#### **6. Página informativa**
#### **- Página de usuario anónimo**
![Página Principal](screenshots/pagina-informativa-anonimo.png)

> Página que se muestra tras haber seleccionado un elemento de los disponibles en la página de la sección. El usuario puede consultar información de interés acerca del elemento que ha seleccionado previamente. 

#### **- Página de usuario registrado**
![Página Principal](screenshots/pagina-informativa-usuario.png)

> Además de lo que puede hacer el usuario anónimo, se puede marcar como visto el elemento o añadir una nota en la página informativa. 

#### **- Página del administrador (Página de edición de un objeto)**
![Página Principal](screenshots/editar-objeto-1.png)
![Página Principal](screenshots/editar-objeto-2.png)
![Página Principal](screenshots/editar-objeto-3.png)

> Accede directamente a la página informativa donde puede modificar lo que desee de ella.

#### **7. Página de nuevo objeto (solo admin)**
![Página Principal](screenshots/nuevo-objeto.png)

> Página que se muestra tras haber seleccionado la opción de añadir en la página de la sección en modo administrador. Se puede crear un objeto para guardarlo y añadirlo al resto de objetos que se muestran en la página de la sección.

#### **8. Página de nueva nota (solo usuario registrado)**
![Página Principal](screenshots/nueva-nota.png)

> Página que se muestra tras haber seleccionado la opción de añadir nota en la página informativa. Se puede crear una nota y guardarla en la página informativa de un objeto.

#### **9. Página de confirmación**
![Página Principal](screenshots/confirmacion.png)

> Página en la que se informa que una operación ha sido realizada con éxito. 

#### **10. Página de error**
![Página Principal](screenshots/error.png)

> Página en la que se informa que se ha producido algún fallo al intentar realizar una operación. 

#### **-> Para las siguientes páginas, se accede a ellas pulsando una opción de las que hay en el menú desplegable del perfil:**

#### **11. Página de perfil de usuario**
![Página Principal](screenshots/pagina-perfil.png)

> Página que muestra tu perfil de usuario actual, el cual puedes editar si lo deseas.

#### **12. Página estadística (solo usuario registrado, no admin)**
![Página Principal](screenshots/grafico-estadisticas.png)

> Página que muestra el porcentaje de objetos vistos por el usuario en cada sección y una gráfica circular de los objetos de cada sección disponibles en el museo.

#### **13. Lista de usuarios (solo admin)**
![Página Principal](screenshots/lista-usuarios.png)

> Página que muestra una lista con los usuarios que tiene la aplicación, en la que se puede acceder al perfil de uno de ellos o eliminarlo.

### **Instrucciones de Ejecución**

#### **Requisitos Previos**
- **Java**: versión 21 o superior
- **Maven**: versión 3.8 o superior
- **MySQL**: versión 8.0 o superior
- **Git**: para clonar el repositorio

#### **Pasos para ejecutar la aplicación**

**OPCIÓN 1**

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/CodeURJC-DAW-2025-26/practica-daw-2025-26-grupo-1.git
   cd practica-daw-2025-26-grupo-1
   ```


2. **Configuración de la base de datos**

> La aplicación utiliza MySQL y requiere que la base de datos esté creada previamente antes del arranque.
> 1. Crear la base de datos: Acceda a la aplicación MySQL Workbench y cree una base de datos llamada `museum`.
> 2. Introducir las credenciales necesarias tras la creación de la base de datos. Puede consultarlas poniendo en su terminal `cd practica-daw-2025-26-grupo-1/backend/src/main/resources` y después `cat application.properties`. El proyecto está configurado para conectar con el usuario `root` y la contraseña `Mysql2026!`. Si se desea usar otro usuario y contraseña, se pueden modificar los valores `spring.datasource.username` y `spring.datasource.password`en el archivo `application.properties`.

3. **Pasos para la ejecución**

> Una vez configurada la base de datos, tras haber clonado en la terminal el repositorio con `git clone https://github.com/CodeURJC-DAW-2025-26/practica-daw-2025-26-grupo-1.git`, escriba los siguientes comandos:

```bash
cd practica-daw-2025-26-grupo-1/backend
mvn spring-boot:run
```

> Tras esto, la aplicación debería cargar todo lo que necesita. Una vez termine la carga, si en las últimas líneas que aparecen encuentra una en la que pone `Tomcat started on port 8443 (https) with context path '/'`, la aplicación se puede ejecutar. Para ejecutarla, vaya a un navegador y escriba `https://localhost:8443` y tras pulsa la tecla Enter o Intro, se le mostrará la aplicación.


**OPCIÓN 2**

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/CodeURJC-DAW-2025-26/practica-daw-2025-26-grupo-1.git
   cd practica-daw-2025-26-grupo-1
   ```

2. **Ejecutar el siguiente comando para arrancar la base de datos de la aplicación**
   ```bash
   docker run --rm -e MYSQL_ROOT_PASSWORD=Mysql2026! -e MYSQL_DATABASE=museum -p 3306:3306 -d mysql:9.5.0
   ```

3. **Ejecutar la aplicación**
   ```bash
   cd backend
   mvn spring-boot:run
   ```


#### **Credenciales de prueba**
- **Usuario Admin**: usuario: `admin`, contraseña: `adminpass`
- **Usuario Registrado**: usuario: `user`, contraseña: `pass`

### **Diagrama de Entidades de Base de Datos**

Diagrama mostrando las entidades, sus campos y relaciones:

![Diagrama Entidad-Relación](screenshots/diagrama-base-de-datos.png)

> El diagrama muestra las entidades principales que han sido necesarias para el desarrollo de esta aplicación, además de las tablas con la que cuenta alguna de ellas para la gestión de sus datos.

### **Diagrama de Clases y Templates**

Diagrama de clases de la aplicación con diferenciación por colores o secciones:

![Diagrama de Clases](screenshots/diagrama-web.png)

> Este diagrama es una representación que muestra todas las clases y templates que se han usado, así como las relaciones entre ellas. Se puede ver que las entidades se relacionan entre ellas al mismo tiempo que cada una usa unos repositorios específicos. Estos repositorios son usados por los servicios, y dichos servicios serán posteriormente utilizados por los controladores con el fin de mostrar la vista de cada una de las templates de la aplicación.

---

## 🛠 **Práctica 2: Incorporación de una API REST a la aplicación web, despliegue con Docker y despliegue remoto**

### **Vídeo de Demostración**
📹 **[Enlace al vídeo en YouTube (primera parte)](https://youtu.be/L8le4tw4Kns)**
> Vídeo mostrando las principales funcionalidades de la aplicación web.

**[Enlace al vídeo en YouTube (segunda parte, continuación del anterior)](https://youtu.be/qmqpddm392o)**
> Vídeo mostrando las principales funcionalidades de la aplicación web.

### **Documentación de la API REST**

#### **Especificación OpenAPI**
📄 **[Especificación OpenAPI (YAML)](backend/api-docs/api-docs.yaml)**

#### **Documentación HTML**
📖 **[Documentación API REST (HTML)](https://raw.githack.com/CodeURJC-DAW-2025-26/practica-daw-2025-26-grupo-1/main/backend/api-docs/api-docs.html)**

> La documentación de la API REST se encuentra en la carpeta `/api-docs` del repositorio. Se ha generado automáticamente con SpringDoc a partir de las anotaciones en el código Java.

### **Diagrama de Clases y Templates Actualizado**

Diagrama actualizado incluyendo los @RestController y su relación con los @Service compartidos:

![Diagrama de Clases Actualizado](screenshots/diagrama-web.png)
![Diagrama de Clases Actualizado](screenshots/diagrama-rest.png)

> Este diagrama muestra cómo las entidades se relacionan entre ellas al mismo tiempo que cada una usa unos repositorios específicos. Estos repositorios son utilizados por los servicios, y dichos servicios son posteriormente consumidos por los controladores.
A diferencia del caso anterior, en el que se mostraban los servicios utilizados por los controladores web para renderizar vistas, en este caso se representan los servicios utilizados por los controladores REST, los cuales exponen la funcionalidad de la aplicación a través de una API.

### **Instrucciones de Ejecución con Docker**

#### **Requisitos previos:**
- Docker instalado (versión 20.10 o superior)
- Docker Compose instalado (versión 2.0 o superior)

#### **Pasos para ejecutar con docker-compose:**

1. **Clonar el repositorio** (si no lo has hecho ya):
   ```bash
   git clone https://github.com/CodeURJC-DAW-2025-26/practica-daw-2025-26-grupo-1.git
   cd practica-daw-2025-26-grupo-1
   ```

2. **Entrar en la carpeta docker del proyecto:**
   
   ```bash
   cd backend/docker
   ```

3. **Ejecutar el siguiente comando:**

   ```bash
   docker compose up
   ```

### **Construcción de la Imagen Docker**

#### **Requisitos:**
- Docker instalado en el sistema

#### **Pasos para construir y publicar la imagen:**

1. **Navegar al directorio de Docker:**:
   ```bash
   cd backend/docker
   ```

2. **Crear la imagen ejecutando lo siguiente:**

   ```bash
   ./create_image.sh museum
   ```
3. **Publicar la imagen ejecutando lo siguiente:**

   ```bash
   ./publish_image.sh <tu_nombre_de_usuario_dockerhub> museum

   #Publicar la imagen como OCI Artifact
   ./publish_docker-compose.sh <tu_nombre_de_usuario_dockerhub> museum
   ```


### **Despliegue en Máquina Virtual**

#### **Requisitos:**
- Acceso a la máquina virtual (SSH)
- Clave privada para autenticación
- Conexión a la red correspondiente o VPN configurada

#### **Pasos para desplegar:**

1. **Conectar a la máquina virtual**:
   ```bash
   ssh -i [ruta/a/clave.key] [usuario]@[IP-o-dominio-VM]
   ```
   
   Ejemplo:
   ```bash
   ssh -i ssh-keys/app.key vmuser@10.100.139.XXX
   ```

2. **Ejecutar uno de los modos de docker compose**:

   Si se quiere el modo create, ejecutar:
   ```bash
   DB_CONFIG=create docker compose -f oci://docker.io/<tu_nombre_usuario_dockerhub>/museum-compose:latest up
   ```

   Si se quiere el modo none, hay dos opciones.

   O ejecutar:
   ```bash
   DB_CONFIG=none docker compose -f oci://docker.io/<tu_nombre_usuario_dockerhub>/museum-compose:latest up
   ```
   O ejecutar:
   ```bash
   docker compose -f oci://docker.io/<tu_nombre_usuario_dockerhub>/museum-compose:latest up
   ```

### **URL de la Aplicación Desplegada**

🌐 **URL de acceso**: `https://appweb01.dawgis.etsii.urjc.es:8443`

#### **Credenciales de Usuarios de Ejemplo**

| Rol | Usuario | Contraseña |
|:---|:---|:---|
| Administrador | admin | adminpass |
| Usuario Registrado | user | pass |



---

## 🛠 **Práctica 3: Implementación de la web con arquitectura SPA**

### **Vídeo de Demostración**
📹 **[Enlace al vídeo en YouTube](URL_del_video)**
> Vídeo mostrando las principales funcionalidades de la aplicación web.

### **Preparación del Entorno de Desarrollo**

#### **Requisitos Previos**
- **Node.js**: versión 18.x o superior
- **npm**: versión 9.x o superior (se instala con Node.js)
- **Git**: para clonar el repositorio

#### **Pasos para configurar el entorno de desarrollo**

1. **Instalar Node.js y npm**
   
   Descarga e instala Node.js desde [https://nodejs.org/](https://nodejs.org/)
   
   Verifica la instalación:
   ```bash
   node --version
   npm --version
   ```

2. **Clonar el repositorio** (si no lo has hecho ya)
   ```bash
   git clone https://github.com/CodeURJC-DAW-2025-26/practica-daw-2025-26-grupo-1.git
   cd practica-daw-2025-26-grupo-1
   ```

3. **Navegar a la carpeta del proyecto React**
   ```bash
   cd frontend
   ```

4. **Instalar todas las dependencias necesarias antes de ejecutar el proyecto**
   ```bash
   npm install
   ```

6. **Ejecutar primero el proyecto desde el backend**
   ```bash
   cd ../backend
   mvn spring-boot:run
   ``` 

5. **Tras ejecutar el backend, volver a la carpeta del proyecto React y ejecutar dicho proyecto desde el frontend (React)**
   ```bash
   cd ../frontend
   npm run dev
   ```  

 

### **Diagrama de Clases y Templates de la SPA**

Diagrama mostrando los componentes React, hooks personalizados, servicios y sus relaciones:

![Diagrama de Componentes React](screenshots/diagrama-react.png)

## **Leyenda del diagrama**
- **Rutas** (cajas grises): endpoints de la aplicación.
- **Páginas de la aplicación / Componentes principales** (cajas moradas): componentes React que sirven como páginas de la aplicación.
- **Componentes auxiliares** (cajas amarillas): componentes React auxiliares.
- **Stores** (cajas rojas): estado global de la aplicación.
- **Services** (cajas azules): servicios que consume la aplicación (comunicación con la API).
- **Flechas sólidas moradas**: navegación entre componentes.
- **Flechas sólidas naranjas**: un componente principal o página usa un componente auxiliar.
- **Flechas sólidas rojas**: un componente usa store.
- **Flechas sólidas azules**: un componente o store usa service.