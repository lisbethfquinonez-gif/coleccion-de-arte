document.addEventListener("DOMContentLoaded", () => {

    const formulario = document.getElementById("formRegistro");

    formulario.addEventListener("submit", async (event) => {

        event.preventDefault();

        const nombre = document.getElementById("nuevo-usuario").value;
        const password = document.getElementById("nuevo-password").value;

        const usuario = {
            nombre: nombre,
            password: password
        };

        try {

            const respuesta = await fetch(
                "http://localhost:8080/usuarios",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(usuario)
                }
            );

            if (respuesta.ok) {

                document.getElementById("mensajeRegistro")
                    .textContent = "Administrador registrado correctamente";

                formulario.reset();

            } else {

                document.getElementById("mensajeRegistro")
                    .textContent = "Error al registrar administrador";

            }

        } catch (error) {

            console.error(error);

            document.getElementById("mensajeRegistro")
                .textContent = "No se pudo conectar con el servidor";
        }

    });

});