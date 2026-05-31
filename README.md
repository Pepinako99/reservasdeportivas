# 🏟️ PistaYa - Reservas Deportivas

Aplicación Android para la gestión y reserva de instalaciones deportivas. Permite a los usuarios registrarse, iniciar sesión, consultar instalaciones disponibles, hacer reservas y cancelarlas.

---

## 📱 Pantallas de la aplicación

- **Splash** → Pantalla de inicio con animación de carga
- **Login** → Inicio de sesión con correo/nombre y contraseña
- **Registro** → Creación de nueva cuenta de usuario
- **Main** → Lista de instalaciones deportivas disponibles
- **Horarios** → Selección de fecha y hora para hacer una reserva
- **Mis Reservas** → Listado de reservas del usuario con opción a cancelar

---

## ⚽ Instalaciones disponibles

| Deporte | Precio/hora |
|---|---|
| Pádel | 15 € |
| Futsal | 20 € |
| Fútbol 11 | 25 € |
| Fútbol 7 | 25 € |
| Baloncesto | 13 € |

---

## 🗂️ Estructura del proyecto

```
app/src/main/java/
├── com.example.reservasdeportivas/
│   ├── SplashActivity.java
│   ├── LoginActivity.java
│   ├── RegistroActivity.java
│   ├── MainActivity.java
│   ├── HorariosActivity.java
│   ├── MisReservasActivity.java
│   ├── InstalacionAdapter.java
│   └── ReservaAdapter.java
├── models/
│   ├── Usuario.java
│   ├── Instalacion.java
│   └── Reserva.java
├── dao/
│   ├── UsuarioDAO.java
│   ├── InstalacionDAO.java
│   └── ReservaDAO.java
└── conexionBBDD/
    └── ConexionBBDD.java
```

---

## 🛠️ Tecnologías utilizadas

- **Java** → Lenguaje principal
- **Android Studio** → Entorno de desarrollo
- **PostgreSQL** → Base de datos
- **JDBC (Driver PostgreSQL)** → Conexión directa a la base de datos

---
 
## ⚙️ Configuración de la base de datos
 
La conexión se configura en `ConexionBBDD.java`:
 
```java
private static final String URL      = "jdbc:postgresql://10.0.2.2:5432/pistaya";
private static final String USUARIO  = "postgres";
private static final String PASSWORD = "1234";
```
 
> ⚠️ La IP `10.0.2.2` es la dirección del emulador para conectar con `localhost` del PC. Si usas un dispositivo físico, cámbiala por la IP local de tu ordenador.
 
---
 
## 🚀 Cómo ejecutar el proyecto
 
1. Clona el repositorio:
```bash
git clone https://github.com/Pepinako99/reservasdeportivas.git
```
2. Abre el proyecto en **Android Studio**
3. Crea una database en pgAdmin con el nombre pistaya
4. Dentro de esa database, ejecuta el Script que hay en este repositosio
5. Configura la IP en `ConexionBBDD.java` si usas dispositivo físico
6. Ejecuta la app en el emulador o dispositivo
---
 
## 📋 Permisos requeridos
 
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
 
---
 
## 👤 Autor
 
**Pedro** - [@Pepinako99](https://github.com/Pepinako99)
