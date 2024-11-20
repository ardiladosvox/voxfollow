// Función para realizar una petición Fetch genérica
function realizarPeticion(url, metodo = 'GET', body = null) {
    const opciones = {
        method: metodo,
        headers: { 'Content-Type': 'application/json' },
    };
    if (body) opciones.body = JSON.stringify(body);

    return fetch(url, opciones)
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.json();
        });
}

// Función para iniciar sesión
function iniciarSesion(event) {
    event.preventDefault();

    const correo = document.getElementById('correo').value.trim();
    const contraseña = document.getElementById('contraseña').value;

    if (!correo || !contraseña) {
        alert("Correo y contraseña son obligatorios.");
        return;
    }

    realizarPeticion(`/usuarios/login?correo=${correo}&contraseña=${contraseña}`, 'POST')
        .then(data => {
            localStorage.setItem('userId', data.userID);
            window.location.href = '/principal';
        })
        .catch(error => alert(error.message));
}

// Función para registrar usuario
function registrarUsuario(event) {
    event.preventDefault();

    const usuario = {
        nombre: document.getElementById('nombre').value.trim(),
        correo: document.getElementById('correo').value.trim(),
        contraseña: document.getElementById('contraseña').value.trim(),
        edad: parseInt(document.getElementById('edad').value.trim()) || 0,
        carrera: document.getElementById('carrera').value.trim(),
        semestre: document.getElementById('semestre').value.trim(),
    };

    if (Object.values(usuario).some(valor => !valor)) {
        alert("Todos los campos son obligatorios.");
        return;
    }

    realizarPeticion('/usuarios/registro', 'POST', usuario)
        .then(() => {
            alert('Usuario registrado con éxito');
            window.location.href = '/login';
        })
        .catch(error => alert('Error de conexión: ' + error.message));
}

// Función para cargar publicaciones
function cargarPublicaciones() {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        alert("Usuario no registrado. Redirigiendo al login...");
        window.location.href = '/login';
        return;
    }

    realizarPeticion(`/publicaciones/usuario/${userId}`)
        .then(publicaciones => {
            const publicacionesDiv = document.getElementById('publicaciones');
            publicacionesDiv.innerHTML = '';
            publicaciones.forEach(pub => {
                const pubDiv = document.createElement('div');
                pubDiv.innerHTML = `
                    <h3>${pub.descripcion}</h3>
                    <p>${pub.fecha}</p>
                    <button type="button" onClick="darLike(${pub.pubID}, false)">Dar Like Público</button>
                    <button type="button" onClick="darLike(${pub.pubID}, true)">Dar Like Anónimo</button>
                `;
                publicacionesDiv.appendChild(pubDiv);
            });
        })
        .catch(error => alert('Error al cargar publicaciones: ' + error.message));
}

function darLike(publicacionId, esAnonimo) {
    const userId = localStorage.getItem('userId');
    if (!userId && !esAnonimo) {
        alert("Error: Usuario no identificado.");
        return;
    }

    const url = esAnonimo
        ? `/likes/anonimo/${publicacionId}`
        : `/likes/publico/${publicacionId}?usuarioId=${userId}`; // Agregamos usuarioId como parámetro de consulta

    realizarPeticion(url, 'POST')
        .then(() => {
            alert('Like Registrado');
            cargarPublicaciones();
        })
        .catch(error => {
            console.error("Detalles del error:", error.message || error);
            alert('Error al dar like: ' + (error.message || JSON.stringify(error)));
        });
}


// Función para crear una nueva publicación
function crearPublicacion(event) {
    event.preventDefault();

    const userId = localStorage.getItem('userId');
    const descripcion = document.getElementById('descripcion').value.trim();
    const anonimo = document.getElementById('anonimo').checked;

    if (!userId) {
        alert('Por favor, inicie sesión primero');
        return;
    }

    if (!descripcion) {
        alert('La descripción de la publicación es obligatoria.');
        return;
    }

    realizarPeticion(`/publicaciones/crear?userId=${userId}`, 'POST', { descripcion, anonimo })
        .then(() => {
            alert('Publicación creada con éxito');
          //  window.location.href = '/principal';
            cargarPublicaciones();
        })
        .catch(error => alert('Error al crear publicación: ' + error.message));
}

// Función para cargar perfil
function cargarPerfil() {
    const userId = localStorage.getItem('userId');
    if (!userId) return;

    realizarPeticion(`/usuarios/${userId}`)
        .then(usuario => {
            const { nombre, correo, edad, carrera, semestre, biografia } = usuario;
            document.getElementById('nombre').innerText = nombre || 'Sin nombre';
            document.getElementById('correo').innerText = correo || 'Sin correo';
            document.getElementById('edad').innerText = edad || 'Sin edad';
            document.getElementById('carrera').innerText = carrera || 'Sin carrera';
            document.getElementById('semestre').innerText = semestre || 'Sin semestre';
            document.getElementById('biografia').innerText = biografia || 'Sin biografía';
        })
        .catch(error => {console.error('Error al cargar el perfil:', error)});
}

function actualizarPerfil(event) {
    event.preventDefault(); // Prevenir el envío normal del formulario

    const userId = localStorage.getItem('userId');
    if (!userId) {
        alert("No se encontró un usuario válido. Redirigiendo al registro...");
        window.location.href = "/registro.html";
        return;
    }

    // Obtener los datos del formulario
    const datosActualizados = {
        nombre: document.getElementById('nombre').value.trim(),
        edad: parseInt(document.getElementById('edad').value.trim()) || 0,
        carrera: document.getElementById('carrera').value.trim(),
        semestre: document.getElementById('semestre').value.trim(),
        biografia: document.getElementById('biografia').value.trim(),
    };

    // Comprobar que los campos no estén vacíos
    if (Object.values(datosActualizados).some(value => value === '')) {
        alert('Todos los campos son obligatorios.');
        return;
    }

    // Realizar la petición para actualizar los datos del perfil
    realizarPeticion(`/usuarios/${userId}`, 'PATCH', datosActualizados)
        .then(() => {
            // Mostrar mensaje de éxito
            const mensajeExito = document.getElementById('mensajeExito');
            mensajeExito.style.display = 'block';

            // Redirigir después de 2 segundos
            setTimeout(() => {
                window.location.href = '/perfil'; // O la URL que deseas redirigir
            }, 2000);
        })
        .catch(error => {
            alert('Error al guardar los cambios: ' + error.message);
        });
}

// Función que se ejecuta cuando el usuario hace clic en el botón de buscar
function buscarUsuario() {
    const nombreUsuario = document.getElementById('nombre-usuario').value.trim();
    
    // Validar que el nombre no esté vacío
    if (nombreUsuario) {
        // Llama al backend para buscar al usuario por nombre
        window.location.href = `/perfil/${nombreUsuario}`;  // Redirige al perfil del usuario
    } else {
        alert('Por favor, ingrese un nombre de usuario válido.');
    }
}

// Función que valida la búsqueda cuando el usuario presiona "Enter"
function validarBusqueda(event) {
    if (event.key === "Enter") {
        buscarUsuario();  // Llama la función de búsqueda si el usuario presiona Enter
    }
}

//uscar usuario por correo
function buscarUsuarioPorCorreo() {
    const correo = document.getElementById('searchInput').value.trim();
    if (!correo) {
        alert("Por favor, ingresa un correo válido.");
        return;
    }

    realizarPeticion(`/usuarios?correo=${correo}`, 'GET')
        .then(data => {
            usuarioActual = data; // Almacenar el usuario encontrado
            mostrarPerfilUsuario(data);
            document.getElementById('accionesUsuario').style.display = 'block'; // Mostrar el botón de añadir
        })
        .catch(error => {
            alert("Error al buscar el usuario: " + error.message);
            console.error("Error:", error);
            document.getElementById('accionesUsuario').style.display = 'none'; // Ocultar el botón si falla
        });
}

// Mostrar perfil del usuario
function mostrarPerfilUsuario(usuario) {
    const perfilDiv = document.getElementById('perfilUsuario');
    perfilDiv.innerHTML = `
        <h3>Perfil del Usuario</h3>
        <p><strong>Nombre:</strong> ${usuario.nombre}</p>
        <p><strong>Correo:</strong> ${usuario.correo}</p>
    `;
}

// Añadir usuario
/*
function añadirUsuario() {
    if (!usuarioActual) {
        alert("No hay usuario seleccionado para añadir.");
        return;
    }

    realizarPeticion('/usuarios/añadir', 'POST', usuarioActual)
        .then(data => {
            alert("usuario añadido correctamente");
            document.getElementById('accionesUsuario').style.display = 'none'; // Ocultar botón tras añadir
        })
        .catch(error => {
            alert("Error al añadir el usuario: " + error.message);
            console.error("Error:", error);
        });
}
        */

function añadirUsuario() {
    if (!usuarioActual) {
        alert("No hay usuario seleccionado para añadir.");
        return;
    }

    // Realizar la petición para añadir el usuario
    realizarPeticion('/usuarios/añadir', 'POST', usuarioActual)
        .then(data => {
            alert("Usuario añadido correctamente");

            // Una vez añadido el usuario, realizar el seguimiento
            realizarPeticion(`/api/seguimiento/follow/${data.id}?seguidorId=${usuarioActual.id}`, 'POST')
                .then(response => {
                    if (response.success) {
                        alert("Usuario seguido exitosamente.");
                    } else {
                        alert("Ya estás siguiendo a este usuario.");
                    }
                })
                .catch(error => {
                    alert("Error al seguir al usuario: " + error.message);
                    console.error("Error:", error);
                });

            // Ocultar el botón de acción tras añadir
            document.getElementById('accionesUsuario').style.display = 'none';
        })
        .catch(error => {
            alert("Error al añadir el usuario: " + error.message);
            console.error("Error:", error);
        });
}


/*
function seguirUsuario() {
    if (!usuarioActual) {
        alert("No hay usuario seleccionado para seguimiento.");
        return;
    }

    // Obtén el ID del usuario logueado (esto podría venir de una sesión o token)
    const usuarioLogueadoId = obtenerUsuarioLogueadoId(); // Implementar esta función para obtener el ID del usuario logueado.

    if (!usuarioLogueadoId) {
        alert("No se ha identificado al usuario logueado.");
        return;
    }

    // Realizar la petición para seguir al usuario
    realizarPeticion(`/api/seguimiento/follow/${usuarioActual.id}?seguidorId=${usuarioLogueadoId}`, 'POST')
        .then(data => {
            if (data.success) {
                alert(data.message); // Mensaje de éxito
            } else {
                alert("Error: " + data.message); // Mensaje de error del backend
            }
        })
        .catch(error => {
            alert("Error al seguir al usuario: " + error.message);
            console.error("Error:", error);
        });
}
        */


// Asignación de eventos
document.getElementById('loginForm')?.addEventListener('submit', iniciarSesion);
document.getElementById('registroForm')?.addEventListener('submit', registrarUsuario);
document.getElementById('publicarForm')?.addEventListener('submit', crearPublicacion);
document.getElementById('perfilForm')?.addEventListener('submit', actualizarPerfil);
document.getElementById('editarPerfilForm')?.addEventListener('submit', actualizarPerfil);

// Cargar perfil automáticamente al cargar la página
document.addEventListener('DOMContentLoaded', cargarPerfil);
