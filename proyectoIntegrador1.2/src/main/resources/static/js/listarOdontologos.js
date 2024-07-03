window.addEventListener('load', () => {
  const tablaOdontologos = document.querySelector("#div_odontologo_table");
  const mostrarDatos = document.querySelector("#div_odontologo_updating");
  const formUpdate = document.querySelector("#update_odontologo_form");
  const divRespuesta = document.querySelector("#response");
  const url = "/odontologos";

  obtenerOdontologos();

  function obtenerOdontologos() {
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
      renderizarOdontologos(data);
      eliminarOdontologo();
      actualizarOdontologo(data);
    })
    .catch(err => console.log(err))
  }

  function renderizarOdontologos(lista) {
    const filaOdontologo = document.querySelector("#odontologoTableBody");
    filaOdontologo.innerHTML = "";
    lista.forEach(odontologo => {
      let updateBtn = `
        <button id="${odontologo.id}"
          type="button"
          class="btn btn-info btn_id">
          Actualizar
        </button>
      `
      let deleteBtn = `
        <button
          id=${odontologo.id}
          type="button"
          class="btn btn-danger btn_delete">
          Borrar
        </button>
      `
      filaOdontologo.innerHTML += `
        <tr>
          <td class="td_id">${odontologo.id}</td>
          <td class="td_matricula">${odontologo.numeroMatricula}</td>
          <td class="td_nombre">${odontologo.nombre}</td>
          <td class="td_apellido">${odontologo.apellido}</td>
          <td>${updateBtn}</td>
          <td>${deleteBtn}</td>
        </tr>
      `
    });
  }

  function eliminarOdontologo() {
    const btnEliminar = document.querySelectorAll(".btn_delete");
    btnEliminar.forEach(btn => {
      const {id} = btn;
      btn.addEventListener('click', () => {
        const urlEliminar = `${url}/${id}`;
        const settings = { method: 'DELETE' }
        fetch(urlEliminar, settings)
        .then(response => obtenerOdontologos())
      })
    });
  }

  function actualizarOdontologo(lista) {
    const btnActualizar = document.querySelectorAll(".btn_id");
    btnActualizar.forEach(btn => {
      const {id} = btn;
      btn.addEventListener('click', () => {
        tablaOdontologos.innerHTML = "";
        mostrarDatos.style.display = 'block';
        const odontologoBuscadoId = lista.find(odontologo => odontologo.id == id);
        renderizarDatosActualizar(odontologoBuscadoId);
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
    e.preventDefault();
    dataOdontologoActualizado();
  })

  function dataOdontologoActualizado() {
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

    fetch(`${url}/actualizar`, settings)
    .then(response => {
      if(!response.ok) {
        return Promise.reject(response);
      }
      return response.text();
    })
    .then(text => renderizarMsjExito(text))
    .catch(err => {
      if(err.status == 400) {
        renderizarMsjError("La matricula debe ser valor numérico")
      }
    })
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