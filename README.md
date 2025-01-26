# CrudRealTimeLogin260125

Este es un proyecto Android de una aplicación CRUD (Crear, Leer, Actualizar y Eliminar) desarrollado en Kotlin que utiliza Firebase Realtime Database como base de datos y soporte para la autenticación de usuarios. La aplicación permite gestionar productos, organizándolos en un nodo de Firebase llamado `agenda`. Además, incluye autenticación con correo electrónico y un menú lateral para manejar la sesión.

## Características

- **Autenticación de usuarios**:
  - Inicio de sesión con correo electrónico y contraseña.
  - Registro de nuevos usuarios.
  - Función para cerrar sesión desde un menú lateral.

- **Gestión de productos**:
  - Los productos se almacenan en el nodo `agenda` de Firebase Realtime Database.
  - Cada producto tiene los siguientes atributos:
    - `nombre` (String): Nombre del producto.
    - `supermercado` (String): Nombre del supermercado.
    - `precio` (Float): Precio del producto.
  - El nombre del producto es único.

- **Funcionalidades CRUD**:
  - **Crear**: Añadir nuevos productos.
  - **Leer**: Mostrar los productos en un RecyclerView, ordenados por nombre.
  - **Actualizar**: Editar los detalles de un producto existente.
  - **Eliminar**: Borrar productos individuales o todos los productos con confirmación.

- **Interfaz de usuario**:
  - Diseñado con Material Design.
  - Menú lateral con opciones para cerrar sesión y eliminar todos los productos.
  - Activity para añadir y editar productos.

## Tecnologías utilizadas

- **Lenguaje**: Kotlin.
- **Firebase**:
  - Realtime Database: Para almacenar los productos.
  - Authentication: Para gestionar el inicio de sesión de los usuarios.
- **Arquitectura**:
  - Modelo-Vista-Controlador (MVC).
- **UI**:
  - Material Design Components.
  - RecyclerView para la lista de productos.

## Estructura del proyecto

### Principales archivos y carpetas

1. **`models`**:
   - Contiene las clases de datos utilizadas para representar los productos.
   - `Producto.kt`: Modelo de datos para los productos con atributos como nombre, supermercado y precio.

2. **`providers`**:
   - Gestiona las operaciones relacionadas con Firebase.
   - `ProductoProvider.kt`: Incluye las funciones para leer, añadir, actualizar y eliminar productos en la base de datos.

3. **Activities**:
   - `MainActivity.kt`: Muestra la lista de productos en un RecyclerView. Incluye opciones para editar y borrar productos.
   - `AddActivity.kt`: Permite añadir o editar productos. Valida los datos ingresados antes de enviarlos a Firebase.
   - `LoginActivity.kt`: Gestiona el inicio de sesión de los usuarios.
   - `RegisterActivity.kt`: Permite registrar nuevos usuarios.

4. **Firebase Realtime Database Rules**:
   - Configuración para permitir acceso solo a usuarios autenticados:
     ```json
     {
       "rules": {
         "agenda": {
           ".read": "auth != null",
           ".write": "auth != null"
         }
       }
     }
     ```

5. **Layouts**:
   - Diseños XML para las pantallas principales (`activity_main.xml`, `activity_add.xml`) y el menú lateral (`nav_header_main.xml`).

## Funcionamiento

### 1. Inicio de sesión
- La aplicación comienza en la pantalla de inicio de sesión. Los usuarios deben autenticarse con su correo y contraseña.
- Si no tienen una cuenta, pueden registrarse desde la pantalla de registro.

### 2. Gestión de productos
- Una vez autenticado, el usuario accede a la pantalla principal donde se muestra la lista de productos.
- Cada producto se muestra con su nombre, supermercado y precio en un RecyclerView.
- Desde aquí, los usuarios pueden:
  - **Editar**: Al hacer clic en un producto, se abre una pantalla para modificar sus datos.
  - **Eliminar**: Pueden borrar un producto de forma individual.
- Un botón flotante permite añadir un nuevo producto.

### 3. Menú lateral
- Desde el menú lateral, los usuarios pueden:
  - Cerrar sesión.
  - Eliminar todos los productos (con confirmación).

### 4. Validaciones
- Al añadir o editar un producto, se validan los siguientes puntos:
  - El nombre debe ser único.
  - El precio debe estar entre 100 y 10,000.
  - Los campos no pueden estar vacíos.

