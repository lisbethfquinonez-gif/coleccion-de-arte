fetch("http://localhost:8080/usuarios", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        nombre: "admin",
        password: "1234"
    })
})