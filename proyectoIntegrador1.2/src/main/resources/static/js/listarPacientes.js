window.addEventListener('load', function() {
  const tablaOdontologo = document.querySelector("#div_odontologo_table");
  obtenerPacientes()

        function obtenerPacientes() {
            const url = "/pacientes";
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
                    renderizarPacientes(data);
                } catch (err) {
                    console.error(err)
                }
            })
        }

        function renderizarPacientes(lista) {
            const filaPaciente = document.querySelector("#pacienteTableBody")
            filaPaciente.innerHTML = ""
            lista.forEach(paciente => {
                let updateBtn = `
                <button id="${paciente.id}"
                    type="button"
                    class="btn btn-info btn_id">
                    ${paciente.id}
                </button>
                `
                let deleteBtn = `
                <button
                    id=${paciente.id}
                    type="button"
                    class="btn btn-danger btn_delete">
                    &times;
                </button>
                `

                filaPaciente.innerHTML += `
                    <tr>
                        <td>${updateBtn}</td>
                        <td class="td_nombre">${paciente.nombre}</td>
                        <td class="td_apellido">${paciente.apellido}</td>
                        <td class="td_cedula">${paciente.cedula}</td>
                        <td class="td_fechaIngreso">${paciente.fechaIngreso}</td>
                        <td class="td_email">${paciente.email}</td>
                        <td>${deleteBtn}</td>
                    </tr>
                `
            });
        }
    })