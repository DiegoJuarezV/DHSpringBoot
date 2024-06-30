window.addEventListener('load', () => {
  const form = document.forms[0];
  const matriculaOdon = document.querySelector("#matricula");
  const nombreOdon = document.querySelector("#nombre");
  const apellidoOdon = document.querySelector("#apellido");
  const divRespuesta = document.querySelector("#response");
  const url = "/odontologos/registrar";

  form.addEventListener('submit', function(e) {
    e.preventDefault();

    const payload = {
      numeroMatricula: matriculaOdon.value,
      nombre: nombreOdon.value,
      apellido: apellidoOdon.value
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
          <strong>Odontólogo registrado exitosamente</strong>
      </div>
    `
  }

  function renderizarMsjError() {
    divRespuesta.style.display = "block";
    divRespuesta.innerHTML = `
      <div class="alert alert-danger alert-dismissible">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <strong>Error al realizar registro</strong>
      </div>
    `
  }
})