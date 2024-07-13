window.addEventListener('load', () => {
  const form = document.forms[0];
  const nombreRegistro = document.querySelector("#name");
  const nombreUsuarioReg = document.querySelector("#username");
  const emailRegistro = document.querySelector("#email");
  const passRegistro = document.querySelector("#password");
  const rolRegistro = document.querySelector("#role");
  const divRespuesta = document.querySelector("#response");
  const toggleMostrarPasswd = document.querySelector("#togglePassword");
  const url = "/usuarios/registrar"

  toggleMostrarPasswd.addEventListener('click', () => {
    const type = passRegistro.type === 'password' ? 'text' : 'password';
    passRegistro.type = type;
    toggleMostrarPasswd.innerHTML = type === 'password' ?
      '<path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle>' :
      '<path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><line x1="23" y1="1" x2="1" y2="23"></line>';
  })

  form.addEventListener('submit', function(e) {
    e.preventDefault();

    const payload = {
      nombre: nombreRegistro.value,
      userName: nombreUsuarioReg.value,
      email: emailRegistro.value,
      password: passRegistro.value,
      usuarioRole: rolRegistro.value
    }

    const settings = {
      method: 'POST',
      headers: {
        'Content-Type' : 'application/json'
      },
      body: JSON.stringify(payload)
    }

    realizarRegistro(settings);
    form.reset();
  })

  function realizarRegistro(settings) {
    fetch(url, settings)
    .then(response => {
      if(!response.ok) {
        return Promise.reject(response);
      }
      return response.json();
    })
    .then(data => renderizarMsjExito())
    .catch(err => renderizarMsjError())
  }

  function renderizarMsjExito() {
    divRespuesta.style.display = "block";
    divRespuesta.innerHTML = `
      <div class="alert alert-success alert-dismissible">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <strong>Registrado con éxito</strong>
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