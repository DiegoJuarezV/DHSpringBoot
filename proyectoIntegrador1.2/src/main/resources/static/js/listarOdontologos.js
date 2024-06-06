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
                <button id="btn_id_${odontologo.id}" type="button" onclick="findBy('${odontologo.id}')" class="btn btn-info btn_id">
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
    })