window.addEventListener('load', () => {
    const tablaTurnos = document.querySelector("#div_turno_table");
    const mostrarDatos = document.querySelector("#div_turno_updating");
    const formUpdate = document.querySelector("#update_turno_form");
    const divRespuesta = document.querySelector("#response");
    const url = "/turnos";

    obtenerTurnos();

    function obtenerTurnos() {
      const settings = {
        method: 'GET'
      }

      fetch(url, settings)
      .then( response => {
        if(!response.ok) {
          return Promise.reject(response);
        }
        return response.json();
      })
      .then(data => {
        renderizarTurnos(data);
        eliminarTurno();
        actualizarTurno(data);
      })
      .catch(err => console.log(err))
    }

    function renderizarTurnos(lista) {
      const filaTurno = document.querySelector("#turnoTableBody");
      filaTurno.innerHTML = "";
      lista.forEach(turno => {
        let updateBtn = `
          <button id="${turno.id}"
            type="button"
            class="btn btn-info btn_id">
            Actualizar
          </button>
        `
        let deleteBtn = `
          <button
            id=${turno.id}
            type="button"
            class="btn btn-danger btn_delete">
            Borrar
          </button>
        `
        filaTurno.innerHTML += `
          <tr>
            <td class="td_id">${turno.id}</td>
            <td class="td_fechaTurno">${turno.fecha}</td>
            <td class="td_id_paciente">${turno.paciente.nombre}</td>
            <td class="td_id_odontologo">${turno.odontologo.nombre}</td>
            <td>${updateBtn}</td>
            <td>${deleteBtn}</td>
          </tr>
        `
      });
    }

    function eliminarTurno() {
      const btnEliminar = document.querySelectorAll(".btn_delete");
      btnEliminar.forEach(btn => {
        const {id} = btn;
        btn.addEventListener('click', () => {
          const urlEliminar = `${url}/${id}`;
          const settings = { method: 'DELETE' }
          fetch(urlEliminar, settings)
          .then(response => obtenerTurnos())
        });
      })
    }

    function actualizarTurno(lista) {
      const btnActualizar = document.querySelectorAll(".btn_id");
      btnActualizar.forEach(btn => {
        const {id} = btn;
        btn.addEventListener('click', () => {
          tablaTurnos.innerHTML = "";
          mostrarDatos.style.display = 'block';
          const turnoBuscadoId = lista.find(turno => turno.id == id);
          renderizarDatosActualizar(turnoBuscadoId);
        })
      });
    }

    function renderizarDatosActualizar(turno) {
      formUpdate.innerHTML = `
        <div class="form-group">
            <label >Id:</label>
            <input type="text" class="form-control" id="turno_id" value="${turno.id}" readonly>
        </div>
        <div class="form-group">
            <label >Fecha de turno:</label>
            <input type="date" class="form-control" placeholder="Fecha de turno" id="fechaTurno" value="${turno.fecha}">
        </div>
        <div class="form-group">
            <label >ID Paciente:</label>
            <input type="text" class="form-control" placeholder="ID de Odontólogo" id="id_paciente" value="${turno.paciente.id}">
        </div>
        <div class="form-group">
            <label >ID Odontólogo:</label>
            <input type="text" class="form-control" placeholder="ID de Paciente" id="id_odontologo" value="${turno.odontologo.id}">
        </div>
        <button type="submit" class="btn btn-primary">Modificar</button>
      `
    }

    formUpdate.addEventListener('submit', function(e) {
      e.preventDefault();
      dataTurnoActualizado();
    })

    function dataTurnoActualizado() {
      const idAct = document.querySelector("#turno_id");
      const fechaTurnoAct = document.querySelector("#fechaTurno");
      const pacienteIdAct = document.querySelector("#id_paciente");
      const odontologoIdAct = document.querySelector("#id_odontologo");

      const payload = {
        id: idAct.value,
        fecha: fechaTurnoAct.value,
        paciente: {
          id: pacienteIdAct.value,
        },
        odontologo: {
          id: odontologoIdAct.value
        }
      }

      const settings = {
        method: 'PUT',
        headers: {
          'Content-Type' : 'application/json'
        },
        body: JSON.stringify(payload)
      }

      fetch(`${url}/actualizar`, settings)
      .then(response => {
        if(!response.ok) {
          return Promise.reject(response);
        }
        return response.text();
      })
      .then(text => renderizarMsjExito(text))
      .catch(err => renderizarMsjError(err))
    }

    function renderizarMsjExito(msje) {
      divRespuesta.style.display = "block";
      divRespuesta.innerHTML = `
        <div class="alert alert-success alert-dismissible">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <strong>${msje}</strong>
        </div>
      `
    }

    function renderizarMsjError(msje) {
      divRespuesta.style.display = "block";
      divRespuesta.innerHTML = `
        <div class="alert alert-danger alert-dismissible">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <strong>${msje}</strong>
        </div>
      `
    }
  })