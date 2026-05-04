# Luigi Motors 🏎️🔧

**Luigi Motors** es una aplicación de escritorio desarrollada en **Java** diseñada para la gestión integral de un taller mecánico. Este proyecto ha sido creado como trabajo final para la asignatura de Programación del 1º año del ciclo de Desarrollo de Aplicaciones Web (DAW).

El sistema cuenta con una interfaz gráfica de usuario (GUI) amigable e intuitiva construida con Java Swing, y utiliza una base de datos relacional (SQL) para garantizar la persistencia de la información.

## 🚀 Características Principales

La aplicación está diseñada con un sistema de roles para ofrecer diferentes funcionalidades según el tipo de usuario que acceda:

### 👤 Perfil Cliente
- **Registro e Inicio de Sesión:** Los usuarios pueden crear una cuenta nueva de forma segura y acceder a la plataforma.
- **Mi Perfil:** Visualización y actualización de los datos personales del cliente.
- **Mis Citas:** Panel dinámico donde los clientes pueden consultar su historial de citas pasadas y visualizar sus próximas citas programadas.

### 🛠️ Perfil Administrador (Mecánico/Gerente)
- **Gestión de Clientes:** Capacidad para visualizar y administrar la información de todos los clientes registrados.
- **Gestión de Reparaciones:** Permite añadir nuevas reparaciones al sistema y actualizar su estado dinámicamente (cambiando entre "Pendiente" y "Terminado").
- **Cálculo de Ingresos:** Herramienta administrativa para consultar y calcular los ingresos generados por los servicios del taller.
- **Gestión de la Base de Datos:** Opciones avanzadas de administración de datos operadas directamente desde la interfaz.

## ⚙️ Tecnologías Utilizadas

- **Lenguaje:** Java 
- **Interfaz Gráfica:** Java Swing / AWT (con diseño moderno y adaptación de fondos/imágenes).
- **Base de Datos:** SQL (Conexión mediante JDBC utilizando `PreparedStatement` para mayor seguridad).
- **Gestor de Dependencias:** Maven
- **Testing:** JUnit 5 y Mockito para la ejecución de pruebas unitarias.

## 🏗️ Arquitectura y Estructura

El proyecto sigue una estructura organizada por paquetes (MVC-like) para separar la interfaz gráfica, la lógica de negocio y el acceso a datos:
- **Vistas (`taller.app.view`):** Componentes visuales como `VentanaPrincipal`, `VentanaLogin` y paneles de navegación.
- **Modelos:** Representación de entidades del dominio (usuarios, citas, reparaciones).
- **Controladores / Persistencia:** Clases clave como `ConexionBBDD` que centralizan todas las operaciones CRUD.
- **Utilidades (`taller.app.utils`):** Funciones de apoyo para validación de datos y manipulación de elementos UI.

## 🏁 Cómo Ejecutar el Proyecto

1. **Preparar la Base de Datos:** Antes de iniciar la aplicación, es necesario ejecutar el script `database.sql` en tu gestor de base de datos SQL. Este script generará la estructura de tablas y volcará los datos iniciales necesarios para probar el programa (incluyendo usuarios de prueba).
2. **Dependencias:** Asegúrate de que las dependencias de Maven estén descargadas e instaladas correctamente.
3. **Ejecución:** Ejecuta la clase principal del proyecto (`VentanaInicial` o la clase `Main` si está definida).
4. **Uso:** Al arrancar, se mostrará una ventana de bienvenida. Dependiendo de las credenciales introducidas en el Login (rol Cliente o Administrador), el sistema te redirigirá a un panel u otro con sus menús específicos.

---
*Desarrollado como proyecto para el módulo de Programación - 1º DAW (Trimestre 3).*