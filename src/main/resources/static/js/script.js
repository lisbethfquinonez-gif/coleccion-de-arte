document.addEventListener("DOMContentLoaded", cargarObras);

function cargarObras() {
    fetch("http://localhost:8080/api/obras/publicas")
        .then(response => response.json())
        .then(obras => {
            const galeria = document.getElementById("galeria");
            galeria.innerHTML = "";

            if (obras.length === 0) {
                galeria.innerHTML = "<p>No hay obras disponibles.</p>";
                return;
            }

            obras.forEach(obra => {
                const imagen = obra.imagenUrl && obra.imagenUrl.trim() !== ""
                    ? "http://localhost:8080" + obra.imagenUrl
                    : "./img/galeriadearte.jpg";

                galeria.innerHTML += `
                    <article class="card-obra">
                        <img src="${imagen}" alt="obra">

                        <div class="info-obra">
                            <h3>${obra.titulo}</h3>
                            <p><strong>Autor:</strong> ${obra.autor}</p>
                            <p><strong>Año:</strong> ${obra.anio || ""}</p>
                            <p><strong>Técnica:</strong> ${obra.tecnica || ""}</p>
                            <p><strong>Dimensiones:</strong> ${obra.dimensiones || ""}</p>
                            <p><strong>Descripción:</strong> ${obra.descripcion || ""}</p>
                        </div>
                    </article>
                `;
            });
        })
        .catch(error => console.error("Error al cargar obras:", error));
}