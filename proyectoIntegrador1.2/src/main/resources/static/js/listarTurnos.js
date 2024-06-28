window.addEventListener('load', function() {
  const tablaTurno = document.querySelector("#div_turno_table");
  obtenerTurnos()

        function obtenerTurnos() {
            const url = "/turnos";
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
                    renderizarTurnos(data);
                } catch (err) {
                    console.error(err)
                }
            })
        }

        function renderizarTurnos(lista) {
        console.log(lista)
            const filaTurno = document.querySelector("#turnoTableBody")
            filaTurno.innerHTML = ""
            lista.forEach(turno => {
                let updateBtn = `
                <button id="${turno.id}"
                    type="button"
                    class="btn btn-info btn_id">
                    ${turno.id}
                </button>
                `
                let deleteBtn = `
                <button
                    id=${turno.id}
                    type="button"
                    class="btn btn-danger btn_delete">
                    &times;
                </button>
                `

                filaTurno.innerHTML += `
                    <tr>
                        <td>${updateBtn}</td>
                        <td class="td_fechaTurno">${turno.fecha}</td>
                        <td class="td_nombrePaciente">${turno.pacienteId}</td>
                        <td class="td_nombreOdontologo">${turno.odontologoId}</td>
                        <td>${deleteBtn}</td>
                    </tr>
                `
            });
        }
    })