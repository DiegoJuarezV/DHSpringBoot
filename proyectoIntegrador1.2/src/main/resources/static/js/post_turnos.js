window.addEventListener('load', () => {
  const form = document.forms[0];
  const fechaTurnoDto = document.querySelector("#fechaTurno");
  const pacienteID = document.querySelector("#pacienteId");
  const odontologoID = document.querySelector("#odontologoId");
  const divRespuesta = document.querySelector("#response");
  const url = "turnos/registrar";

  form.addEventListener('submit', function(e) {
    e.preventDefault();

    const payload = {
      fecha: fechaTurnoDto.value,
      paciente: {
        id: pacienteID.value
      },
      odontologo: {
        id: odontologoID.value
      }
    }

    const settings = {
      method: 'POST',
      headers: {'Content-Type' : 'application/json'},
      body: JSON.stringify(payload)
    }

    realizarRegistro(settings);
    form.reset();
  })

  async function realizarRegistro(settings) {
    try {
      const response = await fetch(url, settings);
      if(!response.ok) {
        throw response;
      }
      const data = await response.json();
      renderizarMsjExito();
    } catch (error) { renderizarMsjError(); }
  }

  function renderizarMsjExito() {
    divRespuesta.style.display = "block";
    divRespuesta.innerHTML = `
      <div class="alert alert-success alert-dismissible">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <strong>Turno registrado exitosamente</strong>
      </div>
    `
  }

  function renderizarMsjError() {
    divRespuesta.style.display = "block";
    divRespuesta.innerHTML = `
      <div class="alert alert-danger alert-dismissible">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <strong>ID´s incorrectos o error al cargar los datos.</strong>
      </div>
    `
  }
})