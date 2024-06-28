window.addEventListener('load', function() {
  const tablaOdontologo = document.querySelector("#div_odontologo_table");
  const mostrarDatos = document.querySelector("#div_odontologo_updating");
  const formUpdate = document.querySelector("#update_odontologo_form")

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
                btnEliminar.forEach(btn => {
                    const {id} = btn
                    btn.addEventListener("click", function() {
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
                    btnActualizar.forEach(btn => {
                        const {id} = btn
                        btn.addEventListener("click", function() {
                           tablaOdontologo.innerHTML = ""
                           mostrarDatos.style.display = "block";
                           const odontologo = data.find(od => od.id == id)
                           renderizarDatosActualizar(odontologo);
                        })
                    });
                  }

        function renderizarDatosActualizar(odontologo) {
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

        formUpdate.addEventListener('submit', function(e) {
           e.preventDefault
           dataOdonActualizado();
        })

        function dataOdonActualizado() {

                  const idAct = document.querySelector("#odontologo_id");
                  const matriculaAct = document.querySelector("#matricula");
                  const nombreAct = document.querySelector("#nombre");
                  const apellidoAct = document.querySelector("#apellido");

                  const payload = {
                    id: idAct.value,
                    numeroMatricula: matriculaAct.value,
                    nombre: nombreAct.value,
                    apellido: apellidoAct.value
                  }

                  const settings = {
                    method: 'PUT',
                    headers: {
                      'Content-Type' : 'application/json'
                    },
                    body: JSON.stringify(payload)
                  }


                  fetch("/odontologos/actualizar", settings)
                  .then( response => {
                    if (!response.ok) {
                      return Promise.reject(response)
                    }
                    return response.text()
                  })
                  .then( text => {
                    console.log(text)
                  })
                  .catch(err => console.log(err))
                }
    })