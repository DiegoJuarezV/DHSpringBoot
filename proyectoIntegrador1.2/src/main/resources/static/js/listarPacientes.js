window.addEventListener('load', () => {
  const tablaPacientes = document.querySelector("#div_paciente_table");
  const mostrarDatos = document.querySelector("#div_paciente_updating");
  const formUpdate = document.querySelector("#update_paciente_form");
  const divRespuesta = document.querySelector("#response");
  const url = "/pacientes";

  obtenerPacientes();

  function obtenerPacientes() {
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
      renderizarPacientes(data);
      eliminarPaciente();
      actualizarPaciente(data);
    })
    .catch(err => console.log(err))
  }

  function renderizarPacientes(lista) {
    const filaPaciente = document.querySelector("#pacienteTableBody");
    filaPaciente.innerHTML = "";
    lista.forEach(paciente => {
      let updateBtn = `
        <button id="${paciente.id}"
          type="button"
          class="btn btn-info btn_id">
          Actualizar
        </button>
      `
      let deleteBtn = `
        <button
          id=${paciente.id}
          type="button"
          class="btn btn-danger btn_delete">
          Borrar
        </button>
      `
      filaPaciente.innerHTML += `
        <tr>
          <td class="td_id">${paciente.id}</td>
          <td class="td_nombre">${paciente.nombre}</td>
          <td class="td_apellido">${paciente.apellido}</td>
          <td class="td_cedula">${paciente.cedula}</td>
          <td class="td_fechaIngreso">${paciente.fechaIngreso}</td>
          <td class="td_email">${paciente.email}</td>
          <td>${updateBtn}</td>
          <td>${deleteBtn}</td>
        </tr>
      `
    });
  }

  function eliminarPaciente() {
    const btnEliminar = document.querySelectorAll(".btn_delete");
    btnEliminar.forEach(btn => {
      const {id} = btn;
      btn.addEventListener('click', () => {
        const urlEliminar = `${url}/${id}`;
        const settings = { method: 'DELETE' }
        fetch(urlEliminar, settings)
        .then(response => obtenerPacientes())
      })
    });
  }

  function actualizarPaciente(lista) {
    const btnActualizar = document.querySelectorAll(".btn_id");
    btnActualizar.forEach(btn => {
      const {id} = btn;
      btn.addEventListener('click', () => {
        tablaPacientes.innerHTML = "";
        mostrarDatos.style.display = 'block';
        const pacienteBuscadoId = lista.find(paciente => paciente.id == id);
        renderizarDatosActualizar(pacienteBuscadoId);
      })
    });
  }

  function renderizarDatosActualizar(paciente) {
    formUpdate.innerHTML = `
      <div class="form-group">
        <label >ID Paciente:</label>
        <input type="text" class="form-control" id="paciente_id" value="${paciente.id}" readonly>
      </div>
      <div class="form-group">
        <label >Nombre:</label>
        <input type="text" class="form-control" placeholder="nombre" id="nombre" value="${paciente.nombre}">
      </div>
      <div class="form-group">
        <label >Apellido:</label>
        <input type="text" class="form-control" placeholder="apellido" id="apellido" value="${paciente.apellido}">
      </div>
      <div class="form-group">
        <label >Cédula:</label>
        <input type="text" class="form-control" placeholder="cédula" id="cedula" value="${paciente.cedula}">
      </div>
      <div class="form-group">
        <label >Fecha de Ingreso:</label>
        <input type="date" class="form-control" placeholder="fecha de ingreso" id="fechaIngreso" value="${paciente.fechaIngreso}">
      </div>
      <div class="form-group">
        <label >Id Domicilio:</label>
        <input type="text" class="form-control" id="domicilio_id" value="${paciente.domicilio.id}" readonly>
      </div>
      <div class="form-group">
        <label >Calle:</label>
        <input type="text" class="form-control" placeholder="calle" id="calle" value="${paciente.domicilio.calle}">
      </div>
      <div class="form-group">
        <label >Número:</label>
        <input type="text" class="form-control" placeholder="número" id="numero" value="${paciente.domicilio.numero}">
      </div>
      <div class="form-group">
        <label >Localidad:</label>
        <input type="text" class="form-control" placeholder="localidad" id="localidad" value="${paciente.domicilio.localidad}">
      </div>
      <div class="form-group">
        <label >Provincia:</label>
        <input type="text" class="form-control" placeholder="provincia" id="provincia" value="${paciente.domicilio.provincia}">
      </div>
      <div class="form-group">
        <label >Email:</label>
        <input type="text" class="form-control" placeholder="email" id="email" value="${paciente.email}">
      </div>
      <button type="submit" class="btn btn-primary">Modificar</button>
    `
  }

  formUpdate.addEventListener('submit', function(e) {
    e.preventDefault();
    dataPacienteActualizado();
  })

  function dataPacienteActualizado() {
    const idAct = document.querySelector("#paciente_id");
    const nombreAct = document.querySelector("#nombre");
    const apellidoAct = document.querySelector("#apellido");
    const cedulaAct = document.querySelector("#cedula");
    const fechaIngresoAct = document.querySelector("#fechaIngreso");
    const domicilioId = document.querySelector("#domicilio_id");
    const calleAct = document.querySelector("#calle");
    const numeroAct = document.querySelector("#numero");
    const localidadAct = document.querySelector("#localidad");
    const provinciaAct = document.querySelector("#provincia");
    const emailAct = document.querySelector("#email");

    const payload = {
      id: idAct.value,
      nombre: nombreAct.value,
      apellido: apellidoAct.value,
      cedula: cedulaAct.value,
      fechaIngreso: fechaIngresoAct.value,
      domicilio: {
        id: domicilioId.value,
        calle: calleAct.value,
        numero: numeroAct.value,
        localidad: localidadAct.value,
        provincia: provinciaAct.value
      },
      email: emailAct.value
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