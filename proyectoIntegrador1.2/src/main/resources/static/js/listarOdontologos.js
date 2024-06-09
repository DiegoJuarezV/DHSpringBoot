window.addEventListener('load', function() {
  const tablaOdontologo = document.querySelector("#odontologoTable");
  obtenerOdontologos()

        function obtenerOdontologos() {
            const url = "/odontologos";
            const settings = {
                method: 'GET'
            }

            fetch(url, settings)
            .then(async response =>{
                if (response.ok != true) {
                    return Promise.reject(response)
                }
                try {
                    const data = await response.json();
                    renderizarOdontologos(data);
                    eliminarOdontologo();
                    actualizarOdontologo(data);
                } catch (err) {
                    console.error(err)
                }
            })
        }

        function renderizarOdontologos(lista) {
            const filaOdontologo = document.querySelector("#OdontologoTableBody")
            filaOdontologo.innerHTML = ""
            lista.forEach(odontologo => {
                let updateBtn = `
                <button id="${odontologo.id}"
                    type="button"
                    class="btn btn-info btn_id">
                    ${odontologo.id}
                </button>
                `
                let deleteBtn = `
                <button
                    id=${odontologo.id}
                    type="button"
                    class="btn btn-danger btn_delete">
                    &times;
                </button>
                `

                filaOdontologo.innerHTML += `
                    <tr>
                        <td>${updateBtn}</td>
                        <td class="td_matricula">${odontologo.numeroMatricula}</td>
                        <td class="td_nombre">${odontologo.nombre}</td>
                        <td class="td_apellido">${odontologo.apellido}</td>
                        <td>${deleteBtn}</td>
                    </tr>
                `
            });
        }

        function eliminarOdontologo() {
            const btnEliminar = document.querySelectorAll(".btn_delete");
                console.log(btnEliminar)
                btnEliminar.forEach(btn => {
                    const {id} = btn
                    btn.addEventListener("click", function() {
                        console.log("eliminado")
                        const urlEliminar = `/odontologos/${id}`
                        const settings = {
                            method: "DELETE"
                        }
                        fetch(urlEliminar, settings)
                        .then(response => obtenerOdontologos())
                    })
                });
        }

        function actualizarOdontologo(data) {
                    const btnActualizar = document.querySelectorAll(".btn_id");
                    console.log(btnActualizar)
                    const mostrarDatos = document.querySelector("#div_odontologo_updating");
                    btnActualizar.forEach(btn => {
                        const {id} = btn
                        btn.addEventListener("click", function() {
                           mostrarDatos.style.display = "block";
                           const odontologo = data.find(od => od.id == id)
                           renderizarDatosActualizar(odontologo);
                        })
                    });
                  }

        function renderizarDatosActualizar(odontologo) {
                    const formUpdate = document.querySelector("#update_odontologo_form")
                    formUpdate.innerHTML = `
                        <div class="form-group">
                            <label >Id:</label>
                            <input type="text" class="form-control" id="odontologo_id" value="${odontologo.id}" readonly>
                        </div>
                        <div class="form-group">
                            <label >Matricula:</label>
                            <input type="text" class="form-control" placeholder="matricula" id="matricula" value="${odontologo.numeroMatricula}">
                        </div>
                        <div class="form-group">
                            <label >Nombre:</label>
                            <input type="text" class="form-control" placeholder="nombre" id="nombre" value="${odontologo.nombre}">
                        </div>
                        <div class="form-group">
                            <label >Apellido:</label>
                            <input type="text" class="form-control" placeholder="apellido" id="apellido" value="${odontologo.apellido}">
                        </div>
                        <button type="submit" class="btn btn-primary">Modificar</button>
                        `
                  }
    })